package org.gemseeker.sms.views;

import io.github.msufred.feathericons.CheckCircleIcon;
import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.*;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

/**
 *
 * @author Gem
 */
public class AddBillingWindow extends AbstractWindow {

    private static final int BTN_SAVE_INDEX = 3;

    @FXML private TextField tfBillingNo;
    @FXML private ComboBox<Account> cbAccounts;
    @FXML private Label lblName;
    @FXML private Label lblDuration;
    @FXML private Label lblPlanType;
    @FXML private Label lblBandwidth;
    @FXML private DatePicker dpFrom;
    @FXML private DatePicker dpTo;
    @FXML private DatePicker dpDue;

    @FXML private CheckBox cbIncludeBalance;
    @FXML private TextField tfBalance;
    @FXML private TextField tfMonthlyFee;
    @FXML private TextField tfDiscount;
    @FXML private TextField tfPenalty;
    @FXML private TextField tfVat;
    @FXML private TextField tfTotal;
    @FXML private TextField tfPreparedBy;
    @FXML private TextField tfDesignation;
    @FXML private TextField tfReceivedBy;

    @FXML private Label lblErrBillingNo;
    @FXML private Label lblErrAccount;
    @FXML private Label lblErrFrom;
    @FXML private Label lblErrTo;
    @FXML private Label lblErrDue;

    @FXML private HBox actionGroup;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnPrint; // save and print
    @FXML private Button btnSave; // save and export as image
    @FXML private Button btnCancel;

    // billing number validation icons
    private final XCircleIcon xCircleIcon = new XCircleIcon(14);
    private final CheckCircleIcon checkCircleIcon = new CheckCircleIcon(14);

    private final Database database;
    private final AccountController accountController;
    private final SubscriptionController subscriptionController;
    private final BillingController billingController;
    private final BalanceController balanceController;
    private final BillingStatementController billingStatementController;
    private final CompositeDisposable disposables;

    private PrintWindow printWindow;
    private SaveImageWindow saveImageWindow;

    private Account mAccount;
    private Subscription mSubscription;
    private ObservableList<Balance> mBalances;

    private double monthlyFee = 0;
    private double balance = 0;
    private double discount = 0; // percent
    private double penalty = 0;
    private double vat = 0;
    private double total = 0;

    public AddBillingWindow(Database database, PrintWindow printWindow, SaveImageWindow saveImageWindow) {
        super("Add Billing Payment", AddBillingWindow.class.getResource("add_billing_2.fxml"), null, null);
        this.database = database;
        this.printWindow = printWindow;
        this.saveImageWindow = saveImageWindow;
        accountController = new AccountController(database);
        subscriptionController = new SubscriptionController(database);
        billingController = new BillingController(database);
        balanceController = new BalanceController(database);
        billingStatementController = new BillingStatementController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();

        ViewUtils.setAsNumericalTextField(tfBalance, tfMonthlyFee, tfDiscount, tfPenalty, tfVat, tfTotal);

        cbAccounts.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) loadSubscriptionDetails(newVal);
        });

        cbIncludeBalance.selectedProperty().addListener((o, oldVal, newVal) -> {
            tfBalance.setDisable(!newVal);
            calculateTotal();
        });

        tfMonthlyFee.textProperty().addListener(o -> calculateTotal());
        tfDiscount.textProperty().addListener(o -> calculateTotal());
        tfPenalty.textProperty().addListener(o -> calculateTotal());
        tfVat.textProperty().addListener(o -> calculateTotal());

        btnPrint.setOnAction(evt -> validateAndSave(this::saveAndPrint));

        btnSave.setOnAction(evt -> validateAndSave(this::saveAndExport));

        btnCancel.setOnAction(evt -> close());
    }

    @Override
    protected void onShow() {
        clearFields();
        disableActions(true);
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(accountController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    cbAccounts.setItems(list);
                    disableActions(false);
                    progressBar.setVisible(false);
                }, err -> {
                    disableActions(false);
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while retrieving Account entries.\n" + err);
                }));
    }

    private void loadSubscriptionDetails(Account account) {
        mAccount = account;
        disableActions(true);
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> subscriptionController.getByAccountNo(account.getAccountNo()))
                .flatMap(subscription -> {
                    mSubscription = subscription;
                    return Single.fromCallable(() -> balanceController.getUnpaidBalance(account.getAccountNo()));
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(balances -> {
                    disableActions(false);
                    progressBar.setVisible(false);
                    mBalances = balances;
                    fillUpFields();
                }, err -> {
                    disableActions(false);
                    progressBar.setVisible(false);
                    // Sub is null
                    showErrorDialog("Database Error", "No Subscription found for Account entry.");
                }));
    }

    private void fillUpFields() {
        if (mAccount == null || mSubscription == null || mBalances == null) return;

        lblName.setText(mAccount.getName());
        lblDuration.setText(String.format("%s-%s",
                mSubscription.getStartDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                mSubscription.getEndDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
        ));
        lblPlanType.setText(mSubscription.getPlanType());
        lblBandwidth.setText(mSubscription.getSpeed() + " MBPS");

        monthlyFee = mSubscription.getMonthlyFee();
        tfMonthlyFee.setText(String.format("%.2f", monthlyFee));

        double balance = 0;
        for (Balance b : mBalances) balance += b.getAmount();
        tfBalance.setText(String.format("%.2f", balance));

        calculateTotal();
    }

    private void calculateTotal() {
        String feeStr = tfMonthlyFee.getText();
        monthlyFee = feeStr.isBlank() ? 0 : Double.parseDouble(feeStr.trim());

        String balanceStr = tfBalance.getText();
        balance = (balanceStr.isBlank() || !cbIncludeBalance.isSelected()) ? 0 : Double.parseDouble(balanceStr.trim());

        String discountStr = tfDiscount.getText();
        discount = discountStr.isBlank() ? 0 : Double.parseDouble(discountStr.trim());

        String penaltyStr = tfPenalty.getText();
        penalty = penaltyStr.isBlank() ? 0 : Double.parseDouble(penaltyStr.trim());

        String vatStr = tfVat.getText();
        vat = vatStr.isBlank() ? 0 : Double.parseDouble(vatStr.trim());

        total = (monthlyFee - (monthlyFee * (discount / 100))) + balance + penalty + vat;
        tfTotal.setText(String.format("%.2f", total));
    }

    /**
     * Checks if all required fields are not empty/blank, then checks if the entered billing no.
     * already exists. If billing no. already exists, an error icon will appear next to the
     * Billing No TextField to indicate that the entered billing no. is invalid. If the billing no.
     * doesn't exist, the Runnable onValidated parameter will be called.
     * @param onValidated Runnable - called if the entered billing no. does not exist, and all required fields
     *                    are not empty/blank.
     */
    private void validateAndSave(Runnable onValidated) {
        // reset error labels
        lblErrBillingNo.getStyleClass().removeAll("label-error", "label-success");
        lblErrAccount.setVisible(false);
        lblErrFrom.setVisible(false);
        lblErrTo.setVisible(false);
        lblErrDue.setVisible(false);

        // check if billing # is entered
        if (tfBillingNo.getText().isBlank()) {
            lblErrBillingNo.getStyleClass().add("label-error");
            lblErrBillingNo.setGraphic(xCircleIcon);
        }

        // check required fields
        lblErrAccount.setVisible(cbAccounts.getValue() == null);
        lblErrFrom.setVisible(dpFrom.getValue() == null);
        lblErrTo.setVisible(dpTo.getValue() == null);
        lblErrDue.setVisible(dpDue.getValue() == null);

        boolean mValid = !tfBillingNo.getText().isBlank() && cbAccounts.getValue() != null &&
                dpFrom.getValue() != null && dpTo.getValue() != null && dpDue.getValue() != null;

        if (mValid) {
            disableActions(true);
            progressBar.setVisible(true);
            disposables.add(Single.fromCallable(() -> billingController.hasBilling(ViewUtils.normalize(tfBillingNo.getText())))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(hasBilling -> {
                        progressBar.setVisible(false);
                        disableActions(false);
                        lblErrBillingNo.getStyleClass().add(hasBilling ? "label-error" : "label-success");
                        lblErrBillingNo.setGraphic(hasBilling ? xCircleIcon : checkCircleIcon);

                        if (!hasBilling) {
                            onValidated.run();
                        }
                    }, err -> {
                        progressBar.setVisible(false);
                        disableActions(false);
                        showErrorDialog("Database Error", "Error while validating.\n" + err);
                    }));
        }
    }

    private void saveAndPrint() {
        save(() -> {
            if (printWindow == null) printWindow = new PrintWindow(database);
            printWindow.showAndWait(PrintWindow.Type.STATEMENT, ViewUtils.normalize(tfBillingNo.getText()));
            return null;
        });
    }

    private void saveAndExport() {
        save(() -> {
            if (saveImageWindow == null) saveImageWindow = new SaveImageWindow(database);
            saveImageWindow.showAndWait(PrintWindow.Type.STATEMENT, ViewUtils.normalize(tfBillingNo.getText()));
            return null;
        });
    }

    private void save(Callable<Void> onSave) {
        progressBar.setVisible(true);
        disableActions(true);
        disposables.add(Single.fromCallable(() -> {
            // save billing
            Billing billing = new Billing();
            billing.setBillingNo(ViewUtils.normalize(tfBillingNo.getText()));
            billing.setAccountNo(mAccount.getAccountNo());
            billing.setToPay(mSubscription != null ? mSubscription.getMonthlyFee() : 0);
            billing.setFromDate(dpFrom.getValue());
            billing.setToDate(dpTo.getValue());
            billing.setDueDate(dpDue.getValue());
            return billingController.insert(billing);
        }).flatMap(success -> Single.fromCallable(() -> {
            if (success) {
                BillingStatement statement = new BillingStatement();
                statement.setBillingNo(ViewUtils.normalize(tfBillingNo.getText()));
                statement.setIncludeBalance(cbIncludeBalance.isSelected());
                statement.setPrevBalance(balance);
                statement.setMonthlyFee(monthlyFee);
                statement.setDiscount(discount);
                statement.setPenalty(penalty);
                statement.setVat(vat);
                statement.setTotal(total);
                statement.setPreparedBy(ViewUtils.normalize(tfPreparedBy.getText()));
                statement.setDesignation(ViewUtils.normalize(tfDesignation.getText()));
                statement.setReceivedBy(ViewUtils.normalize(tfReceivedBy.getText()));
                billingStatementController.insert(statement);
            }
            return success;
        })).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            disableActions(false);
            if (!success) showWarningDialog("Failed", "Failed to add new Billing entry.");
            else {
                if (onSave != null) onSave.call();
            }
        }, err -> {
            progressBar.setVisible(false);
            disableActions(false);
            showErrorDialog("Database Error", "Error while saving Billing entry.\n" + err);
        }));
    }

    private void disableActions(boolean disable) {
        btnPrint.setDisable(disable);
        btnSave.setDisable(disable);
        btnCancel.setDisable(disable);
    }

    private void setupIcons() {
        lblErrAccount.setGraphic(new XCircleIcon(14));
        lblErrFrom.setGraphic(new XCircleIcon(14));
        lblErrTo.setGraphic(new XCircleIcon(14));
        lblErrDue.setGraphic(new XCircleIcon(14));
    }

    @Override
    protected void onClose() {
        clearFields();
        mAccount = null;
        mSubscription = null;
        mBalances = null;
    }

    private void clearFields() {
        cbAccounts.setItems(null);
        lblName.setText("");
        lblDuration.setText("");
        lblPlanType.setText("");
        lblBandwidth.setText("");
        dpFrom.setValue(null);
        dpTo.setValue(null);
        dpDue.setValue(null);
        cbIncludeBalance.setSelected(false);
        tfBalance.setText("0.0");
        tfMonthlyFee.setText("0.0");
        tfDiscount.setText("0.0");
        tfPenalty.setText("0.0");
        tfVat.setText("0.0");
        tfTotal.setText("0.0");
        tfPreparedBy.clear();
        tfDesignation.clear();
        tfReceivedBy.clear();

        lblErrBillingNo.setGraphic(null);
        lblErrAccount.setVisible(false);
        lblErrFrom.setVisible(false);
        lblErrTo.setVisible(false);
        lblErrDue.setVisible(false);

        btnPrint.setText("Validate");
        actionGroup.getChildren().remove(btnSave);
    }

    public void dispose() {
        disposables.dispose();
    }

}
