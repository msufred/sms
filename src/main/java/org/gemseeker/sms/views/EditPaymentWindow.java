package org.gemseeker.sms.views;

import io.github.msufred.feathericons.SVGIcon;
import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Payment;
import org.gemseeker.sms.data.controllers.PaymentController;

import java.util.LinkedHashMap;

public class EditPaymentWindow extends AbstractWindow {

    @FXML private TextField tfName;
    @FXML private TextField tfPreparedBy;
    @FXML private DatePicker dpPaymentDate;
    @FXML private ComboBox<String> cbStatus;
    @FXML private MenuButton mbTags;
    @FXML private Label lblErrName;
    @FXML private Label lblErrDate;
    @FXML private Label lblErrStatus;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final LinkedHashMap<String, SVGIcon> tags = ViewUtils.getTags();

    private final PaymentController paymentController;
    private final CompositeDisposable disposables;

    private int mId = -1;
    private String mTag = "normal";
    private Payment mPayment;

    public EditPaymentWindow(Database database) {
        super("Edit Payment", EditPaymentWindow.class.getResource("edit_payment.fxml"), null, null);
        this.paymentController = new PaymentController(database);
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        lblErrName.setGraphic(new XCircleIcon(12));
        lblErrDate.setGraphic(new XCircleIcon(12));
        lblErrStatus.setGraphic(new XCircleIcon(12));

        cbStatus.setItems(FXCollections.observableArrayList("Valid", "Void"));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.setGraphic(icon);
            item.setOnAction(evt -> changeTag(tag));
            mbTags.getItems().add(item);
        });

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });

        btnCancel.setOnAction(evt -> close());
    }

    public void showAndWait(int id) {
        mId = id;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        disposables.add(Single.fromCallable(() -> paymentController.get(mId))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(payment -> {
                    mPayment = payment;
                    fillUpFields();
                }, err -> {
                    showWarningDialog("Payment Not Found", "Theres no Payment entry with id=" + mId);
                }));
    }

    private void fillUpFields() {
        if (mPayment == null) return;
        tfName.setText(mPayment.getName());
        tfPreparedBy.setText(mPayment.getPreparedBy());
        dpPaymentDate.setValue(mPayment.getPaymentDate());
        cbStatus.setValue(ViewUtils.capitalize(mPayment.getStatus()));
        changeTag(mPayment.getTag());
    }

    private void changeTag(String tag) {
        mTag = tag;
        mbTags.setText(ViewUtils.capitalize(tag));
        mbTags.setGraphic(tags.get(tag));
    }

    private boolean validated() {
        lblErrName.setVisible(false);
        lblErrDate.setVisible(false);
        lblErrStatus.setVisible(false);

        lblErrName.setVisible(tfName.getText().isBlank());
        lblErrDate.setVisible(dpPaymentDate.getValue() == null);
        lblErrStatus.setVisible(cbStatus.getValue() == null);
        return !tfName.getText().isBlank() && dpPaymentDate.getValue() != null && cbStatus.getValue() != null;
    }

    private void saveAndClose() {
        disposables.add(Single.fromCallable(() -> {
            mPayment.setName(ViewUtils.normalize(tfName.getText()));
            mPayment.setPreparedBy(ViewUtils.normalize(tfPreparedBy.getText()));
            mPayment.setPaymentDate(dpPaymentDate.getValue());
            mPayment.setStatus(cbStatus.getValue());
            System.out.println(mTag);
            mPayment.setTag(mTag);
            return paymentController.update(mPayment);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            if (!success) showWarningDialog("Failed", "Failed to update Payment entry.");
            close();
        }, err -> {
            showErrorDialog("Database Error", "Error while updating Payment entry.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
        mId = -1;
        mPayment = null;
    }

    private void clearFields() {
        tfName.clear();
        tfPreparedBy.clear();
        dpPaymentDate.setValue(null);
        cbStatus.setValue("Valid");
        changeTag("normal");
        lblErrName.setVisible(false);
        lblErrDate.setVisible(false);
        lblErrStatus.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
