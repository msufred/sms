package org.gemseeker.sms.views.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.gemseeker.sms.data.Account;
import org.gemseeker.sms.data.Billing;
import org.gemseeker.sms.data.BillingStatement;
import org.gemseeker.sms.data.Subscription;
import org.gemseeker.sms.views.ViewUtils;
import org.gemseeker.sms.views.panels.AbstractPanel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StatementEcopyForm extends AbstractPanel {

    @FXML private Label lblDuration;
    @FXML private Label lblAccountName;
    @FXML private Label lblAddress;
    @FXML private Label lblContactNo;
    @FXML private Label lblStatementDate;
    @FXML private Label lblContractPeriod;
    @FXML private Label lblPlanType;
    @FXML private Label lblBandwidth;

    @FXML private Label lblPrevRemainingBalance;
    @FXML private Label lblCurrentCharges;
    @FXML private Label lblVat;
    @FXML private Label lblTotalCurrentCharges;

    @FXML private Label lblAmountCurrent;
    @FXML private Label lblAmountPrev;
    @FXML private Label lblTotalAmount;
    @FXML private Label lblDueDate;

    @FXML private Label lblPreparedBy;
    @FXML private Label lblDesignation;

    private Account mAccount;
    private Subscription mSubscription;
    private Billing mBilling;
    private BillingStatement mBillingStatement;

    public StatementEcopyForm() {
        super(StatementEcopyForm.class.getResource("billing_statement_ecopy.fxml"));
    }

    @Override
    protected void onFxmlLoaded() {
        // empty
    }

    public void setData(Account account, Subscription subscription, Billing billing, BillingStatement billingStatement) {
        mAccount = account;
        mSubscription = subscription;
        mBilling = billing;
        mBillingStatement = billingStatement;
    }

    @Override
    public void onResume() {
        if (mAccount == null || mSubscription == null || mBilling == null || mBillingStatement == null) return;
        clearFields();
        fillUpFields();
    }

    private void fillUpFields() {
        // account info
        lblAccountName.setText(mAccount.getName());
        lblAddress.setText(mAccount.getAddress());
        lblContactNo.setText(mAccount.getPhone());

        // billing info
        lblStatementDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        lblContractPeriod.setText(ViewUtils.getDateDuration(mSubscription.getStartDate(), mSubscription.getEndDate()));
        lblPlanType.setText(mSubscription.getPlanType());
        lblBandwidth.setText(mSubscription.getSpeed() + " MBPS");

        lblDuration.setText(String.format("%s - %s",
                mBilling.getFromDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                mBilling.getToDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))));

        double balance = mBillingStatement.getPrevBalance();
        double fee = mBillingStatement.getMonthlyFee();
        double discountPercent = mBillingStatement.getDiscount();
        double penalty = mBillingStatement.getPenalty();
        double vat = mBillingStatement.getVat();
        double currentCharge = (fee - (fee * (discountPercent / 100))) + penalty; // current charges will be the total of (monthlyFee - discount) + penalty
        double currentChargeWithVat = currentCharge + vat;

        // charges info
        lblPrevRemainingBalance.setText(String.format("%.2f", balance));
        lblCurrentCharges.setText(String.format("%.2f", currentCharge));
        lblVat.setText(String.format("%.2f", vat));
        lblTotalCurrentCharges.setText(String.format("%.2f", currentChargeWithVat));
        lblAmountPrev.setText(String.format("%.2f", balance));
        lblAmountCurrent.setText(String.format("%.2f", currentChargeWithVat));
        lblTotalAmount.setText(String.format("%.2f", balance + currentChargeWithVat));

        // other info
        lblDueDate.setText(mBilling.getDueDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        lblPreparedBy.setText(mBillingStatement.getPreparedBy());
        lblDesignation.setText(mBillingStatement.getDesignation());
    }

    @Override
    public void onPause() {
        clearFields();
    }

    private void clearFields() {
        lblDuration.setText("");
        lblAccountName.setText("");
        lblAddress.setText("");
        lblContactNo.setText("");
        lblStatementDate.setText("");
        lblContractPeriod.setText("");
        lblPlanType.setText("");
        lblBandwidth.setText("");

        lblPrevRemainingBalance.setText("0.00");
        lblCurrentCharges.setText("0.00");
        lblVat.setText("0.00");
        lblTotalCurrentCharges.setText("0.00");

        lblAmountCurrent.setText("0.00");
        lblAmountPrev.setText("0.00");
        lblTotalAmount.setText("0.00");
        lblDueDate.setText("");

        lblPreparedBy.setText("");
        lblDesignation.setText("");
    }

    @Override
    public void onDispose() {
        // empty
    }
}
