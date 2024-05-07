package org.gemseeker.sms.views;

import io.github.msufred.feathericons.CheckCircleIcon;
import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import org.gemseeker.sms.data.Billing;
import org.gemseeker.sms.data.BillingStatement;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Payment;
import org.gemseeker.sms.data.controllers.BillingController;
import org.gemseeker.sms.data.controllers.BillingStatementController;
import org.gemseeker.sms.data.controllers.PaymentController;

import java.util.concurrent.Callable;

public class AcceptPaymentWindow extends AbstractWindow {

    @FXML private TextField tfPaymentNo;
    @FXML private Label lblErrPaymentNo;
    @FXML private Label lblFee;
    @FXML private Label lblPrevBalance;
    @FXML private Label lblDiscount;
    @FXML private Label lblPenalty;
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

    private final Database database;
    private final BillingController billingController;
    private final BillingStatementController billingStatementController;
    private final PaymentController paymentController;
    private final CompositeDisposable disposables;

    private String mBillingNo;
    private Billing mBilling;
    private BillingStatement mBillingStatement;

    private double amountToPay = 0; // monthly fee
    private double prevBalance = 0;
    private double discount = 0;
    private double penalty = 0; // surcharges
    private double totalAmount = 0;
    private double amountPaid = 0;
    private double balance = 0;

    private boolean mPaymentValid = false;

    public AcceptPaymentWindow(Database database) {
        super("Accept Payment", AcceptPaymentWindow.class.getResource("accept_payment.fxml"), null, null);
        this.database = database;
        this.billingController = new BillingController(database);
        this.billingStatementController = new BillingStatementController(database);
        this.paymentController = new PaymentController(database);
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        ViewUtils.setAsNumericalTextField(tfAmount);

        tfAmount.textProperty().addListener((o, oldVal, newVal) -> calculate());

        btnConfirm.setOnAction(evt -> {
            if (validated()) saveAndPrint();
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
        balance = amountToPay - amountPaid;
        lblBalance.setText(String.format("%.2f", balance));
    }

    private void checkPaymentNo() {
        lblErrPaymentNo.getStyleClass().removeAll("label-error", "label-success");
        if (tfPaymentNo.getText().isBlank()) {
            lblErrPaymentNo.getStyleClass().add("label-error");
            lblErrPaymentNo.setGraphic(xCircleIcon);
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
            Payment payment = new Payment();
            payment.setPaymentFor(Payment.TYPE_BILLING);
            payment.setExtraInfo(mBillingNo);
            pa
        }));
    }

    private void fillUpFields() {
        if (mBilling == null || mBillingStatement == null) return;
        amountToPay = mBillingStatement.getMonthlyFee();
        prevBalance = mBillingStatement.getPrevBalance();
        discount = amountToPay * (mBillingStatement.getDiscount() / 100);
        penalty = mBillingStatement.getPenalty();
        totalAmount = mBillingStatement.getTotal();
        System.out.println("Total: " + totalAmount + " vs calc=" + (amountToPay + prevBalance + penalty - discount));

        lblFee.setText(String.format("%.2f", amountToPay));
        lblPrevBalance.setText(String.format("%.2f", prevBalance));
        lblDiscount.setText(String.format("%.2f", discount));
        lblPenalty.setText(String.format("%.2f", penalty));
        lblTotalDue.setText(String.format("%.2f", totalAmount));
    }

    @Override
    protected void onClose() {
        clearFields();
    }

    private void clearFields() {
        lblErrPaymentNo.setGraphic(null);
        lblFee.setText("0.00");
        lblPrevBalance.setText("0.00");
        lblDiscount.setText("0.00");
        lblPenalty.setText("0.00");
        lblTotalDue.setText("0.00");
        lblChange.setText("0.00");
        lblBalance.setText("0.00");
        tfAmount.setText("0.00");
        tfCashier.clear();
    }

    public void dispose() {
        disposables.dispose();
    }
}
