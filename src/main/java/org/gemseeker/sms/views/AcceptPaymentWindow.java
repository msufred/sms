package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.gemseeker.sms.Utils;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.*;
import org.gemseeker.sms.views.icons.CheckCircleIcon;
import org.gemseeker.sms.views.icons.PaperClipIcon;
import org.gemseeker.sms.views.icons.XCircleIcon;

import java.io.File;
import java.time.LocalDate;

public class AcceptPaymentWindow extends AbstractWindow {

    @FXML private TextField tfPaymentNo;
    @FXML private Label lblErrPaymentNo; // if OR is not available
    @FXML private Label lblOkPaymentNo; // if OR is available
    @FXML private ComboBox<String> cbModes;
    @FXML private HBox refGroup;
    @FXML private Label lblRef;
    @FXML private TextField tfRef;
    @FXML private DatePicker dpPaymentDate;
    @FXML private Label lblErrPaymentDate;

    @FXML private TextField tfFee;
    @FXML private TextField tfPrevBalance;
    @FXML private TextField tfDiscount;
    @FXML private TextField tfPenalty;
    @FXML private TextField tfVat;

    @FXML private Label lblTotalDue;
    @FXML private Label lblChange;
    @FXML private Label lblBalance; // balance after payment
    @FXML private TextField tfAmount;
    @FXML private Label lblErrAmount;
    @FXML private TextField tfCashier;

    @FXML private Button btnUpload;
    @FXML private Label lblAttachment;

    @FXML private ProgressBar progressBar;
    @FXML private Button btnConfirm;    // save and print
    @FXML private Button btnExport;     // save and export
    @FXML private Button btnCancel;

    // billing number validation icons
    private final XCircleIcon xCircleIcon = new XCircleIcon(14);
    private final CheckCircleIcon checkCircleIcon = new CheckCircleIcon(14);

    private final BillingController billingController;
    private final AccountController accountController;
    private final BillingStatementController billingStatementController;
    private final BalanceController balanceController;
    private final PaymentController paymentController;
    private final RevenueController revenueController;
    private final CompositeDisposable disposables;

    private final User user;
    private final PrintWindow printWindow;
    private final SaveImageWindow saveImageWindow;

    private FileChooser fileChooser;
    private File imageFile;

    private String mBillingNo;
    private Billing mBilling;
    private Account mAccount;
    private BillingStatement mBillingStatement;
    private ObservableList<Balance> mBalances;

    private double amountToPay = 0; // monthly fee
    private double prevBalance = 0;
    private double discount = 0;
    private double penalty = 0; // surcharges
    private double vat = 0;
    private double totalAmount = 0;
    private double amountPaid = 0;
    private double balance = 0;

    public AcceptPaymentWindow(User user, Database database, PrintWindow printWindow, SaveImageWindow saveImageWindow, Stage owner) {
        super("Accept Payment", AcceptPaymentWindow.class.getResource("accept_payment.fxml"), null, owner);
        this.user = user;

        this.billingController = new BillingController(database);
        this.accountController = new AccountController(database);
        this.billingStatementController = new BillingStatementController(database);
        this.balanceController = new BalanceController(database);
        this.paymentController = new PaymentController(database);
        this.revenueController = new RevenueController(database);
        this.disposables = new CompositeDisposable();

        this.printWindow = printWindow;
        this.saveImageWindow = saveImageWindow;
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();

        ViewUtils.setAsNumericalTextField(tfFee, tfPrevBalance, tfDiscount, tfPenalty, tfVat, tfAmount);
        btnUpload.setGraphic(new PaperClipIcon(14));

        cbModes.setItems(Revenue.modes);
        cbModes.valueProperty().addListener((o, oldVal, newVal) -> {
            refGroup.setDisable(newVal.equals(Revenue.MODE_CASH));
            if (newVal.equals(Revenue.MODE_BANK_CASH)) {
                String ref = String.format("%s (%s)", mAccount.getBankAccountName(), mAccount.getBankAccountNo());
                lblRef.setText("Account No.");
                tfRef.setText(ref);
            } else {
                lblRef.setText("Reference No.");
                tfRef.clear();
            }
        });
        cbModes.setValue(Revenue.MODE_CASH);

        btnUpload.setOnAction(evt -> {
            uploadFile();
        });

        tfDiscount.textProperty().addListener((o, oldVal, newVal) -> calculate());
        tfPenalty.textProperty().addListener((o, oldVal, newVal) -> calculate());
        tfVat.textProperty().addListener((o, oldVal, newVal) -> calculate());
        tfAmount.textProperty().addListener((o, oldVal, newVal) -> calculate());

        btnConfirm.setOnAction(evt -> validateAndSave(this::saveAndPrint));
        btnExport.setOnAction(evt -> validateAndSave(this::saveAndExport));
        btnCancel.setOnAction(evt -> close());

        tfCashier.setText(user.getFullname());
    }

    private void setupIcons() {
        lblErrPaymentNo.setGraphic(new XCircleIcon(14));
        lblOkPaymentNo.setGraphic(new CheckCircleIcon(14));
        lblErrPaymentDate.setGraphic(new XCircleIcon(14));
        lblErrAmount.setGraphic(new XCircleIcon(14));
    }

    public void showAndWait(String billingNo) {
        if (billingNo == null) {
            showWarningDialog("Invalid", "No selected Billing entry. Try again.");
            return;
        }
        mBillingNo = billingNo;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> billingController.getByBillingNo(mBillingNo))
                .flatMap(billing -> {
                    mBilling = billing;
                    return Single.fromCallable(() -> accountController.getByAccountNo(billing.getAccountNo()));
                }).flatMap(account -> {
                    mAccount = account;
                    return Single.fromCallable(() -> balanceController.getUnpaidBalance(account.getAccountNo()));
                }).flatMap(balances -> {
                    mBalances = balances;
                    return Single.fromCallable(() -> billingStatementController.getByBillingNo(mBillingNo));
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(billingStatement -> {
                    progressBar.setVisible(false);
                    mBillingStatement = billingStatement;
                    fillUpFields();
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while retrieving Billing & BillingStatement entry.\n" + err);
                }));
    }

    private void calculate() {
        // reset values
        discount = 0;
        penalty = 0;
        vat = 0;
        amountPaid = 0;

        // parse text fields
        discount = tfDiscount.getText().isBlank() ? 0 : Double.parseDouble(tfDiscount.getText().trim());
        penalty = tfPaymentNo.getText().isBlank() ? 0 : Double.parseDouble(tfPenalty.getText().trim());
        vat = tfVat.getText().isBlank() ? 0 : Double.parseDouble(tfVat.getText().trim());
        amountPaid = tfAmount.getText().isBlank() ? 0 : Double.parseDouble(tfAmount.getText());

        // calculate total amount due and balance
        totalAmount = amountToPay + prevBalance + penalty + vat - discount;
        balance = totalAmount - amountPaid;

        // display values
        lblTotalDue.setText(String.format("%.2f", totalAmount));
        lblBalance.setText(String.format("%.2f", balance));
    }

    private void validateAndSave(Runnable onValidated) {
        // reset error/ok labels
        lblErrPaymentNo.setVisible(false);
        lblOkPaymentNo.setVisible(false);
        lblErrPaymentDate.setVisible(false);
        lblErrAmount.setVisible(false);

        // first, check payment date and amount entered
        boolean hasDate = dpPaymentDate.getValue() != null;
        boolean hasAmount = !tfAmount.getText().isBlank() && !tfAmount.getText().equals("0.00");
        lblErrPaymentDate.setVisible(!hasDate);
        lblErrAmount.setVisible(!hasAmount);

        // check if user entered payment no.
        boolean hasPaymentNo = !tfPaymentNo.getText().isBlank();
        lblErrPaymentNo.setVisible(!hasPaymentNo);

        if (!hasPaymentNo || !hasDate || !hasAmount) {
            showWarningDialog("Invalid", "Please check required fields and try again.");
            return;
        }

        // at this point, user entered the payment number, payment date, and amount
        // we will now check if payment number already exists...
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> paymentController.hasPayment(ViewUtils.normalize(tfPaymentNo.getText())))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(hasPayment -> {
                    progressBar.setVisible(false);
                    lblErrPaymentNo.setVisible(hasPayment);
                    lblOkPaymentNo.setVisible(!hasPayment);

                    if (!hasPayment) {
                        onValidated.run();
                    } else {
                        showWarningDialog("Invalid Payment No", "Payment No. already exists. Try again.");
                    }
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while querying database.\n" + err);
                }));
    }

    private void saveAndPrint() {
        save(() -> {
            if (printWindow != null) {
                printWindow.showAndWait(PrintWindow.Type.RECEIPT, mBillingNo);
            }
        });
    }

    private void saveAndExport() {
        save(() -> {
            if (saveImageWindow != null) {
                saveImageWindow.showAndWait(SaveImageWindow.Type.RECEIPT, mBillingNo);
            }
        });
    }

    private void save(Runnable onSave) {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            Payment payment = fetchPaymentData();
            return paymentController.insert(payment);
        }).flatMap(success -> Single.fromCallable(() -> {
            if (success) {
                // update billing
                mBilling.setStatus("Paid");
                mBilling.setPaymentNo(ViewUtils.normalize(tfPaymentNo.getText()));
                billingController.update(mBilling);

                // update balance
                if (mBalances != null) {
                    // old balances are updated to Paid status
                    for (Balance b : mBalances) {
                        b.setStatus(Balance.STATUS_PAID);
                        b.setDatePaid(LocalDate.now());
                        balanceController.update(b);
                    }
                    // new balance will be saved
                    if (balance > 0) {
                        Balance newBalance = new Balance();
                        newBalance.setAccountNo(mBilling.getAccountNo());
                        newBalance.setAmount(balance);
                        balanceController.insert(newBalance);
                    }
                }

                // add to revenues
                Revenue revenue = new Revenue();
                revenue.setType(Revenue.TYPE_BILLING);
                revenue.setAmount(amountPaid);
                revenue.setMode(cbModes.getValue());
                revenue.setReference(tfRef.getText());
                revenue.setDescription("Payment for Billing No" + mBillingNo);
                revenue.setDate(LocalDate.now());
                revenueController.insert(revenue);

                if (imageFile != null) {
                    File dest = new File(Utils.IMAGE_FOLDER + Utils.FILE_SEPARATOR + tfPaymentNo.getText() + "." + ViewUtils.getFileExtension(imageFile));
                    FileUtils.copyFile(imageFile, dest);
                }
            }
            return success;
        })).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to add Payment entry.");
            else {
                if (onSave != null) onSave.run();
                close();
            }
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while adding new Payment entry.\n" + err);
        }));
    }

    private Payment fetchPaymentData() {
        Payment payment = new Payment();
        payment.setPaymentNo(ViewUtils.normalize(tfPaymentNo.getText()));
        payment.setMode(cbModes.getValue());
        payment.setRef(tfRef.getText());
        payment.setName(mAccount.getName());
        payment.setAddress(mAccount.getAddress());
        payment.setContact(mAccount.getPhone());
        payment.setPaymentFor(Payment.TYPE_BILLING);
        payment.setExtraInfo(mBillingNo);
        payment.setPrevBalance(prevBalance);
        payment.setAmountToPay(amountToPay);
        payment.setDiscount(discount);
        payment.setSurcharges(penalty);
        payment.setVat(vat);
        payment.setAmountTotal(totalAmount);
        payment.setAmountPaid(amountPaid);
        payment.setBalance(balance);
        payment.setPaymentDate(dpPaymentDate.getValue());
        payment.setPreparedBy(tfCashier.getText());
        return payment;
    }

    private void fillUpFields() {
        if (mBilling == null || mBillingStatement == null) return;
        dpPaymentDate.setValue(LocalDate.now());
        amountToPay = mBillingStatement.getMonthlyFee();
        prevBalance = mBillingStatement.getPrevBalance();
        discount = amountToPay * (mBillingStatement.getDiscount() / 100);
        penalty = mBillingStatement.getPenalty();
        vat = mBillingStatement.getVat();
        totalAmount = mBillingStatement.getTotal();

        tfFee.setText(String.format("%.2f", amountToPay));
        tfPrevBalance.setText(String.format("%.2f", prevBalance));
        tfDiscount.setText(String.format("%.2f", discount));
        tfPenalty.setText(String.format("%.2f", penalty));
        tfVat.setText(String.format("%.2f", vat));

        lblTotalDue.setText(String.format("%.2f", totalAmount));
    }

    private void uploadFile() {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg"));
        }
        imageFile = fileChooser.showOpenDialog(getStage().getOwner());
        if (imageFile !=  null) {
            lblAttachment.setText(imageFile.getPath());
        }
    }

    @Override
    protected void onClose() {
        clearFields();
        imageFile = null;
        mBillingNo = null;
        mBilling = null;
        mBillingStatement = null;

        amountToPay = 0;
        prevBalance = 0;
        discount = 0;
        penalty = 0;
        vat = 0;
        totalAmount = 0;
        amountPaid = 0;
        balance = 0;
    }

    private void clearFields() {
        lblErrPaymentNo.setVisible(false);
        lblErrPaymentDate.setVisible(false);
        lblOkPaymentNo.setVisible(false);
        lblErrAmount.setVisible(false);
        cbModes.setValue("Cash");
        refGroup.setDisable(true);
        lblAttachment.setText("No File Attached");
        lblTotalDue.setText("0.00");
        lblChange.setText("0.00");
        lblBalance.setText("0.00");
        tfAmount.setText("0.00");
        tfCashier.clear();
        btnConfirm.setText("Validate");
    }

    public void dispose() {
        disposables.dispose();
    }
}
