package org.gemseeker.sms.views;

import io.github.msufred.feathericons.CheckCircleIcon;
import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.*;

import java.time.LocalDate;
import java.util.concurrent.Callable;

public class AcceptPaymentWindow extends AbstractWindow {

    @FXML private DatePicker dpPaymentDate;
    @FXML private TextField tfPaymentNo;
    @FXML private Label lblErrPaymentNo;
    @FXML private Label lblFee;
    @FXML private Label lblPrevBalance;
    @FXML private Label lblDiscount;
    @FXML private Label lblPenalty;
    @FXML private Label lblVat;
    @FXML private Label lblTotalDue;
    @FXML private Label lblChange;
    @FXML private Label lblBalance; // balance after payment
    @FXML private TextField tfAmount;
    @FXML private TextField tfCashier;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnConfirm;
    @FXML private Button btnCancel;

    // billing number validation icons
    private final XCircleIcon xCircleIcon = new XCircleIcon(14);
    private final CheckCircleIcon checkCircleIcon = new CheckCircleIcon(14);

    private final BillingController billingController;
    private final AccountController accountController;
    private final BillingStatementController billingStatementController;
    private final BalanceController balanceController;
    private final PaymentController paymentController;
    private final CompositeDisposable disposables;

    private final PrintWindow printWindow;

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

    private boolean mPaymentValid = false;

    public AcceptPaymentWindow(Database database, PrintWindow printWindow) {
        super("Accept Payment", AcceptPaymentWindow.class.getResource("accept_payment.fxml"), null, null);
        this.billingController = new BillingController(database);
        this.accountController = new AccountController(database);
        this.billingStatementController = new BillingStatementController(database);
        this.balanceController = new BalanceController(database);
        this.paymentController = new PaymentController(database);
        this.disposables = new CompositeDisposable();

        this.printWindow = printWindow;
    }

    @Override
    protected void onFxmlLoaded() {
        ViewUtils.setAsNumericalTextField(tfAmount);

        tfAmount.textProperty().addListener((o, oldVal, newVal) -> calculate());

        btnConfirm.setOnAction(evt -> {
            if (!mPaymentValid) {
                validate();
            } else {
                saveAndPrint();
            }
        });
        btnCancel.setOnAction(evt -> close());
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
        String paidStr = tfAmount.getText();
        amountPaid = paidStr.isBlank() ? 0 : Double.parseDouble(paidStr.trim());
        balance = amountToPay + prevBalance - amountPaid;
        lblBalance.setText(String.format("%.2f", balance));
    }

    private void validate() {
        lblErrPaymentNo.getStyleClass().removeAll("label-error", "label-success");
        if (tfPaymentNo.getText().isBlank()) {
            lblErrPaymentNo.getStyleClass().add("label-error");
            lblErrPaymentNo.setGraphic(xCircleIcon);
            mPaymentValid = false;
            return;
        }
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> paymentController.hasPayment(ViewUtils.normalize(tfPaymentNo.getText())))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(hasPayment -> {
                    progressBar.setVisible(false);
                    lblErrPaymentNo.getStyleClass().add(hasPayment ? "label-error" : "label-success");
                    lblErrPaymentNo.setGraphic(hasPayment ? xCircleIcon : checkCircleIcon);
                    if (!hasPayment) {
                        btnConfirm.setText("Confirm & Print");
                    }
                    mPaymentValid = !hasPayment;
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while querying database.\n" + err);
                }));

    }

    private void saveAndPrint() {
        if (tfAmount.getText().isBlank()) {
            showWarningDialog("Invalid", "No entered amount. Try again.");
            return;
        }

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
                    double oldBal = 0;
                    for (Balance b : mBalances) {
                        oldBal += b.getAmount();
                        b.setStatus(Balance.STATUS_PAID);
                        b.setDatePaid(LocalDate.now());
                        balanceController.update(b);
                    }
                    double newBal = Math.abs(oldBal - balance);
                    System.out.println("New Balance: " + newBal);
                    if (newBal > 0) {
                        Balance newBalance = new Balance();
                        newBalance.setAccountNo(mBilling.getAccountNo());
                        newBalance.setAmount(newBal);
                        balanceController.insert(newBalance);
                    }
                }
            }
            return success;
        })).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to add Payment entry.");
            if (printWindow != null) {
                printWindow.showAndWait(PrintWindow.Type.RECEIPT, mBillingNo);
            }
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while adding new Payment entry.\n" + err);
        }));
    }

    private Payment fetchPaymentData() {
        Payment payment = new Payment();
        payment.setPaymentNo(ViewUtils.normalize(tfPaymentNo.getText()));
        payment.setName(mAccount.getName());
        payment.setPaymentFor(Payment.TYPE_BILLING);
        payment.setExtraInfo(mBillingNo);
        payment.setAmountToPay(amountToPay + prevBalance);
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
        System.out.println("Total: " + totalAmount + " vs calc=" + (amountToPay + prevBalance + penalty - discount));

        lblFee.setText(String.format("%.2f", amountToPay));
        lblPrevBalance.setText(String.format("%.2f", prevBalance));
        lblDiscount.setText(String.format("%.2f", discount));
        lblPenalty.setText(String.format("%.2f", penalty));
        lblVat.setText(String.format("%.2f", vat));
        lblTotalDue.setText(String.format("%.2f", totalAmount));
    }

    @Override
    protected void onClose() {
        clearFields();
        mPaymentValid = false;
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
        lblErrPaymentNo.setGraphic(null);
        lblFee.setText("0.00");
        lblPrevBalance.setText("0.00");
        lblDiscount.setText("0.00");
        lblPenalty.setText("0.00");
        lblVat.setText("0.00");
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
