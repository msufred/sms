package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrepareBillingStatementWindow extends AbstractWindow {

    @FXML private Label lblName;
    @FXML private Label lblDuration;
    @FXML private Label lblPlanType;
    @FXML private Label lblBandwidth;
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
    @FXML private ProgressBar progressBar;
    @FXML private Button btnPrint;
    @FXML private Button btnCancel;

    //private final Settings settings;
    private final Database database;
    private final AccountController accountController;
    private final BillingController billingController;
    private final SubscriptionController subscriptionController;
    private final BalanceController balanceController;
    private final BillingStatementController billingStatementController;
    private final CompositeDisposable disposables;

    private String mBillingNo;
    private Billing mBilling;
    private Account mAccount;
    private Subscription mSubscription;
    private ObservableList<Balance> mBalances;
    private BillingStatement mBillingStatement;

    // Windows
    private PrintWindow printWindow;

    public PrepareBillingStatementWindow(Database database, Stage owner) {
        super("Billing Statement", PrepareBillingStatementWindow.class.getResource("prepare_billing_statement.fxml"), null, owner);
        this.database = database;
        accountController = new AccountController(database);
        billingController = new BillingController(database);
        subscriptionController = new SubscriptionController(database);
        balanceController = new BalanceController(database);
        billingStatementController = new BillingStatementController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        ViewUtils.setAsNumericalTextField(tfBalance, tfMonthlyFee, tfDiscount, tfPenalty, tfVat, tfTotal);

        cbIncludeBalance.selectedProperty().addListener((o, oldVal, newVal) -> {
            tfBalance.setDisable(!newVal);
            calculateTotal();
        });

        tfMonthlyFee.textProperty().addListener(o -> calculateTotal());
        tfDiscount.textProperty().addListener(o -> calculateTotal());
        tfPenalty.textProperty().addListener(o -> calculateTotal());
        tfVat.textProperty().addListener(o -> calculateTotal());

        btnPrint.setOnAction(evt -> {
            if (validated()) saveBillingAndClose();
        });

        btnCancel.setOnAction(evt -> close());
    }

    public void showAndWait(String billingNo) {
        mBillingNo = billingNo;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> billingStatementController.getByBillingNo(mBillingNo))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(statement -> {
                    progressBar.setVisible(false);
                    // not null, fill up fields
                    mBillingStatement = statement;
                    fetchBillingAndAccountInfo();
                }, err -> {
                    progressBar.setVisible(false);
                    // error or not billing statement found
                    fetchBillingAndAccountInfo();
                }));
    }

    private void fetchBillingAndAccountInfo() {
        disposables.add(Single.fromCallable(() -> {
            return billingController.getByBillingNo(mBillingNo);
        }).flatMap(billing -> {
            mBilling = billing;
            return Single.fromCallable(() -> accountController.getByAccountNo(mBilling.getAccountNo()));
        }).flatMap(account -> {
            mAccount = account;
            return Single.fromCallable(() -> subscriptionController.getByAccountNo(mAccount.getAccountNo()));
        }).flatMap(subscription -> {
            mSubscription = subscription;
            return Single.fromCallable(() -> balanceController.getUnpaidBalance(mSubscription.getAccountNo()));
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(balanceList -> {
            progressBar.setVisible(false);
            mBalances = balanceList;
            fillFields();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while querying database.\n" + err);
        }));
    }

    private void fillFields() {
        if (mAccount == null || mBilling == null || mSubscription == null) return;
        lblName.setText(mAccount.getName());
        lblDuration.setText(String.format("%s-%s",
                mSubscription.getStartDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                mSubscription.getEndDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
        ));
        lblPlanType.setText(mSubscription.getPlanType());
        lblBandwidth.setText(mSubscription.getSpeed() + " MBPS");

        double monthlyFee = mBillingStatement == null ? mBilling.getToPay() : mBillingStatement.getMonthlyFee();
        tfMonthlyFee.setText(String.format("%.2f", monthlyFee));

        cbIncludeBalance.setSelected(mBillingStatement != null && mBillingStatement.isIncludeBalance());
        double balance = 0;
        if (mBillingStatement == null && mBalances.isEmpty()) {
            balance = 0;
        } else if (mBillingStatement == null) {
            for (Balance b : mBalances) balance += b.getAmount();
        } else {
            balance = mBillingStatement.getPrevBalance();
        }
        tfBalance.setText(String.format("%.2f", balance));

        double discount = mBillingStatement == null ? 0.0 : mBillingStatement.getDiscount();
        tfDiscount.setText(String.format("%.2f", discount));

        double penalty = mBillingStatement == null ? 0.0 : mBillingStatement.getPenalty();
        tfPenalty.setText(String.format("%.2f", penalty));

        double vat = mBillingStatement == null ? 0.0 : mBillingStatement.getVat();
        tfVat.setText(String.format("%.2f", vat));

        if (mBillingStatement != null) {
            tfTotal.setText(String.format("%.2f", mBillingStatement.getTotal()));
        } else {
            calculateTotal();
        }

        tfPreparedBy.setText(mBillingStatement == null ? "" : mBillingStatement.getPreparedBy());
        tfDesignation.setText(mBillingStatement == null ? "" : mBillingStatement.getDesignation());
        tfReceivedBy.setText(mBillingStatement == null ? "" : mBillingStatement.getReceivedBy());
    }

    private void calculateTotal() {
        String feeStr = tfMonthlyFee.getText();
        double fee = feeStr.isBlank() ? 0 : Double.parseDouble(feeStr.trim());

        String balanceStr = tfBalance.getText();
        double balance = (balanceStr.isBlank() || !cbIncludeBalance.isSelected()) ? 0 : Double.parseDouble(balanceStr.trim());

        String discountStr = tfDiscount.getText();
        double discount = discountStr.isBlank() ? 0 : Double.parseDouble(discountStr.trim());

        String penaltyStr = tfPenalty.getText();
        double penalty = penaltyStr.isBlank() ? 0 : Double.parseDouble(penaltyStr.trim());

        String vatStr = tfVat.getText();
        double vat = vatStr.isBlank() ? 0 : Double.parseDouble(vatStr.trim());

        double total = (fee - (fee * (discount / 100))) + balance + penalty + vat;
        tfTotal.setText(String.format("%.2f", total));
    }

    private boolean validated() {
        return true; // for now
    }

    private void saveBillingAndClose() {
        if (mBillingStatement != null) {
            updateAndPrint();
        } else {
            saveAndPrint();
        }
    }

    private void updateAndPrint() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            mBillingStatement.setIncludeBalance(cbIncludeBalance.isSelected());

            String feeStr = tfMonthlyFee.getText();
            double fee = feeStr.isBlank() ? 0 : Double.parseDouble(feeStr.trim());
            mBillingStatement.setMonthlyFee(fee);

            String balanceStr = tfBalance.getText();
            double balance = (balanceStr.isBlank() || !cbIncludeBalance.isSelected()) ? 0 : Double.parseDouble(balanceStr.trim());
            mBillingStatement.setPrevBalance(balance);

            String discountStr = tfDiscount.getText();
            double discount = discountStr.isBlank() ? 0 : Double.parseDouble(discountStr.trim());
            mBillingStatement.setDiscount(discount);

            String penaltyStr = tfPenalty.getText();
            double penalty = penaltyStr.isBlank() ? 0 : Double.parseDouble(penaltyStr.trim());
            mBillingStatement.setPenalty(penalty);

            String vatStr = tfVat.getText();
            double vat = vatStr.isBlank() ? 0 : Double.parseDouble(vatStr.trim());
            mBillingStatement.setVat(vat);

            mBillingStatement.setTotal(Double.parseDouble(tfTotal.getText().trim()));
            mBillingStatement.setPreparedBy(ViewUtils.normalize(tfPreparedBy.getText()));
            mBillingStatement.setDesignation(ViewUtils.normalize(tfDesignation.getText()));
            mBillingStatement.setReceivedBy(ViewUtils.normalize(tfReceivedBy.getText()));
            mBillingStatement.setDatePrinted(LocalDateTime.now());
            return billingStatementController.update(mBillingStatement);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to update Billing Statement.");
            if (printWindow == null) printWindow = new PrintWindow(database, getStage());
            printWindow.showAndWait(PrintWindow.Type.STATEMENT, mBillingNo);
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while updating Billing Statement entry.\n" + err);
        }));
    }

    private void saveAndPrint() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            BillingStatement billingStatement = new BillingStatement();
            billingStatement.setBillingNo(mBillingNo);
            billingStatement.setIncludeBalance(cbIncludeBalance.isSelected());

            String feeStr = tfMonthlyFee.getText();
            double fee = feeStr.isBlank() ? 0 : Double.parseDouble(feeStr.trim());
            billingStatement.setMonthlyFee(fee);

            String balanceStr = tfBalance.getText();
            double balance = (balanceStr.isBlank() || !cbIncludeBalance.isSelected()) ? 0 : Double.parseDouble(balanceStr.trim());
            billingStatement.setPrevBalance(balance);

            String discountStr = tfDiscount.getText();
            double discount = discountStr.isBlank() ? 0 : Double.parseDouble(discountStr.trim());
            billingStatement.setDiscount(discount);

            String penaltyStr = tfPenalty.getText();
            double penalty = penaltyStr.isBlank() ? 0 : Double.parseDouble(penaltyStr.trim());
            billingStatement.setPenalty(penalty);

            String vatStr = tfVat.getText();
            double vat = vatStr.isBlank() ? 0 : Double.parseDouble(vatStr.trim());
            billingStatement.setVat(vat);

            billingStatement.setTotal(Double.parseDouble(tfTotal.getText().trim()));
            billingStatement.setPreparedBy(ViewUtils.normalize(tfPreparedBy.getText()));
            billingStatement.setDesignation(ViewUtils.normalize(tfDesignation.getText()));
            billingStatement.setReceivedBy(ViewUtils.normalize(tfReceivedBy.getText()));
            billingStatement.setDatePrinted(LocalDateTime.now());
            return billingStatementController.insert(billingStatement);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to add new Billing Statement.");
            if (printWindow == null) printWindow = new PrintWindow(database, getStage());
            printWindow.showAndWait(PrintWindow.Type.STATEMENT, mBillingNo);
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while adding new Billing Statement entry.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
        mBillingNo = null;
        mBilling = null;
        mAccount = null;
        mSubscription = null;
        mBillingStatement = null;
        mBalances = null;
    }

    private void clearFields() {
        lblName.setText("");
        lblDuration.setText("");
        lblPlanType.setText("");
        lblBandwidth.setText("");
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
    }

    public void dispose() {
        if (printWindow != null) printWindow.dispose();
        disposables.dispose();
    }
}
