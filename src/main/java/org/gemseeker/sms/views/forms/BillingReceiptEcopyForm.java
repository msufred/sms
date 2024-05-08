package org.gemseeker.sms.views.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.gemseeker.sms.data.Account;
import org.gemseeker.sms.data.Payment;
import org.gemseeker.sms.views.panels.AbstractPanel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BillingReceiptEcopyForm extends AbstractPanel {

    @FXML private Label lblOrNo;
    @FXML private Label lblAccountName;
    @FXML private Label lblDate;
    @FXML private Label lblAddress;
    @FXML private Label lblTin;
    @FXML private Label lblItem1A;
    @FXML private Label lblItem2A;
    @FXML private Label lblItem3A;
    @FXML private Label lblItem4A;
    @FXML private Label lblItem1B;
    @FXML private Label lblItem2B;
    @FXML private Label lblItem3B;
    @FXML private Label lblItem4B;
    @FXML private Label lblItem1C;
    @FXML private Label lblItem2C;
    @FXML private Label lblItem3C;
    @FXML private Label lblItem4C;
    @FXML private Label lblItem1D;
    @FXML private Label lblItem2D;
    @FXML private Label lblItem3D;
    @FXML private Label lblItem4D;
    @FXML private Label lblAuthorized;
    @FXML private Label lblTotalSales;
    @FXML private Label lblDiscount;
    @FXML private Label lblPenalty;
    @FXML private Label lblPaymentDue;
    @FXML private Label lblAmountPaid;
    @FXML private Label lblBalance;

    private Account mAccount;
    private Payment mPayment;

    public BillingReceiptEcopyForm() {
        super(BillingReceiptEcopyForm.class.getResource("receipt_ecopy.fxml"));
    }

    @Override
    protected void onFxmlLoaded() {

    }

    public void setData(Account account, Payment payment) {
        mAccount =  account;
        mPayment = payment;
    }

    @Override
    public void onResume() {
        if (mAccount == null || mPayment == null) return;
        clearFields();
        fillUpFields();
    }

    private void fillUpFields() {
        lblOrNo.setText(mPayment.getPaymentNo());
        lblAccountName.setText(mAccount.getName());
        lblAddress.setText(mAccount.getAddress());
        lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));

        if (mPayment.getPrevBalance() > 0) {
            lblItem1A.setText("Prev. Balance");
            lblItem1B.setText(String.format("%.2f", mPayment.getPrevBalance()));
            lblItem1D.setText(String.format("%.2f", mPayment.getPrevBalance()));
        }

        lblItem2A.setText("Monthly Fee");
        lblItem2B.setText(String.format("%.2f", mPayment.getAmountToPay()));
        lblItem2D.setText(String.format("%.2f", mPayment.getAmountToPay()));

        if (mPayment.getVat() > 0) {
            lblItem3A.setText("VAT");
            lblItem3B.setText(String.format("%.2f", mPayment.getVat()));
            lblItem3B.setText(String.format("%.2f", mPayment.getVat()));
        }

        double subtotal = mPayment.getPrevBalance() + mPayment.getAmountToPay() + mPayment.getVat();
        lblTotalSales.setText(String.format("%.2f", subtotal));
        lblDiscount.setText(String.format("%.2f", mPayment.getDiscount()));
        lblPenalty.setText(String.format("%.2f", mPayment.getSurcharges()));
        lblPaymentDue.setText(String.format("%.2f", mPayment.getAmountTotal()));
        lblAmountPaid.setText(String.format("%.2f", mPayment.getAmountPaid()));
        lblBalance.setText(String.format("%.2f", mPayment.getBalance()));
        lblAuthorized.setText(mPayment.getPreparedBy());
    }

    @Override
    public void onPause() {
        clearFields();
    }

    private void clearFields() {
        lblAccountName.setText("");
        lblDate.setText("");
        lblAddress.setText("");
        lblTin.setText("");
        lblItem1A.setText("");
        lblItem2A.setText("");
        lblItem3A.setText("");
        lblItem4A.setText("");
        lblItem1B.setText("");
        lblItem2B.setText("");
        lblItem3B.setText("");
        lblItem4B.setText("");
        lblItem1C.setText("");
        lblItem2C.setText("");
        lblItem3C.setText("");
        lblItem4C.setText("");
        lblItem1D.setText("");
        lblItem2D.setText("");
        lblItem3D.setText("");
        lblItem4D.setText("");
        lblAuthorized.setText("");
        lblTotalSales.setText("0.00");
        lblDiscount.setText("0.00");
        lblPenalty.setText("0.00");
        lblPaymentDue.setText("0.00");
        lblAmountPaid.setText("0.00");
        lblBalance.setText("0.00");
    }

    @Override
    public void onDispose() {
    }
}
