package org.gemseeker.sms.views.forms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.gemseeker.sms.data.Account;
import org.gemseeker.sms.data.Payment;
import org.gemseeker.sms.data.PaymentItem;
import org.gemseeker.sms.data.User;
import org.gemseeker.sms.views.panels.AbstractPanel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalesReceiptForm extends AbstractPanel {

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
    @FXML private Label lblTotalSales;
    @FXML private Label lblDiscount;
    @FXML private Label lblPenalty;
    @FXML private Label lblPaymentDue;
    @FXML private Label lblAmountPaid;
    @FXML private Label lblBalance;
    @FXML private Label lblPreparedBy;
    @FXML private ImageView tempBg;

    private ObservableList<Label> columnA, columnB, columnC, columnD;
    private ObservableList<ObservableList<Label>> columns;

    private User mUser;
    private Account mAccount;
    private Payment mPayment;
    private ObservableList<PaymentItem> paymentItems;

    public SalesReceiptForm() {
        super(SalesReceiptForm.class.getResource("sales_receipt.fxml"));
    }

    @Override
    protected void onFxmlLoaded() {
        columnA = FXCollections.observableArrayList(lblItem1A, lblItem1B, lblItem1C, lblItem1D);
        columnB = FXCollections.observableArrayList(lblItem2A, lblItem2B, lblItem2C, lblItem2D);
        columnC = FXCollections.observableArrayList(lblItem3A, lblItem3B, lblItem3C, lblItem3D);
        columnD = FXCollections.observableArrayList(lblItem4A, lblItem4B, lblItem4C, lblItem4D);
        columns = FXCollections.observableArrayList(columnA, columnB, columnC, columnD);
    }

    public void setData(User user, Payment payment, ObservableList<PaymentItem> items) {
        resetData();
        mUser = user;
        mAccount = null;
        mPayment = payment;
        paymentItems = items;
    }

    public void setData(User user, Account account, Payment payment) {
        resetData();
        mUser = user;
        mAccount =  account;
        mPayment = payment;
    }

    @Override
    public void onResume() {
        if (mPayment == null) return;
        clearFields();
        fillUpFields();
    }

    private void fillUpFields() {
        lblAccountName.setText(mPayment.getName());
        lblAddress.setText(mPayment.getAddress());
        lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));

        // paymentItems can only be null if printing for subscription billing or receipt
        if (paymentItems == null) {
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
        } else { // printing for Service or Purchase payment
            for (int i = 0; i < paymentItems.size(); i++) {
                PaymentItem item = paymentItems.get(i);
                ObservableList<Label> column = columns.get(i);
                column.get(0).setText(item.getItemName());
                column.get(1).setText(String.format("%.2f", item.getAmount()));
                column.get(2).setText(String.format("%d", item.getQuantity()));
                column.get(3).setText(String.format("%.2f", item.getAmount()));
            }


//            PaymentItem item1 = paymentItems.get(0);
//            if (item1 != null) {
//                lblItem1A.setText(item1.getItemName());
//                lblItem1B.setText(String.format("%.2f", item1.getAmount()));
//                lblItem1C.setText(String.format("%d", item1.getQuantity()));
//                lblItem1D.setText(String.format("%.2f", item1.getAmount()));
//            }
//
//            PaymentItem item2 = paymentItems.get(1);
//            if (item2 != null) {
//                lblItem2A.setText(item2.getItemName());
//                lblItem2B.setText(String.format("%.2f", item2.getAmount()));
//                lblItem2C.setText(String.format("%d", item2.getQuantity()));
//                lblItem2D.setText(String.format("%.2f", item2.getAmount()));
//            }
//
//            PaymentItem item3 = paymentItems.get(2);
//            if (item3 != null) {
//                lblItem3A.setText(item3.getItemName());
//                lblItem3B.setText(String.format("%.2f", item3.getAmount()));
//                lblItem3C.setText(String.format("%d", item3.getQuantity()));
//                lblItem3D.setText(String.format("%.2f", item3.getAmount()));
//            }
//
//            PaymentItem item4 = paymentItems.get(3);
//            if (item4 != null) {
//                lblItem4A.setText(item4.getItemName());
//                lblItem4B.setText(String.format("%.2f", item4.getAmount()));
//                lblItem4C.setText(String.format("%d", item4.getQuantity()));
//                lblItem4D.setText(String.format("%.2f", item4.getAmount()));
//            }
        }

        double subtotal = mPayment.getPrevBalance() + mPayment.getAmountToPay() + mPayment.getVat();
        lblTotalSales.setText(String.format("%.2f", subtotal));
        lblDiscount.setText(String.format("%.2f", mPayment.getDiscount()));
        lblPenalty.setText(String.format("%.2f", mPayment.getSurcharges()));
        lblPaymentDue.setText(String.format("%.2f", mPayment.getAmountTotal()));
        lblAmountPaid.setText(String.format("%.2f", mPayment.getAmountPaid()));
        lblBalance.setText(String.format("%.2f", mPayment.getBalance()));
        lblPreparedBy.setText(mUser.getFullname());
    }

    @Override
    public void onPause() {
        clearFields();
    }

    public void showTempBg(boolean show) {
        tempBg.setVisible(show);
    }

    private void clearFields() {
        lblAccountName.setText("");
        lblDate.setText("");
        lblAddress.setText("");
        lblTin.setText("");

        for (ObservableList<Label> col : columns) {
            for (Label label : col) {
                label.setText("");
            }
        }

        lblTotalSales.setText("0.00");
        lblDiscount.setText("0.00");
        lblPenalty.setText("0.00");
        lblPaymentDue.setText("0.00");
        lblAmountPaid.setText("0.00");
        lblBalance.setText("0.00");
        lblPreparedBy.setText("");

        showTempBg(true);
    }

    private void resetData() {
        mUser = null;
        mAccount = null;
        mPayment = null;
        paymentItems = null;
    }

    @Override
    public void onDispose() {

    }
}
