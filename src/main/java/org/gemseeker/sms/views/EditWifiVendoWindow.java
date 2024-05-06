package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.WifiVendo;
import org.gemseeker.sms.data.controllers.WifiVendoController;

public class EditWifiVendoWindow extends AbstractWindow {

    @FXML private TextField tfName;
    @FXML private TextField tfIpAddress;
    @FXML private ComboBox<String> cbStatus;
    @FXML private Label lblError;
    @FXML private Button btnSave;

    private final WifiVendoController wifiVendoController;
    private final CompositeDisposable disposables;

    private WifiVendo mVendo;

    public EditWifiVendoWindow(Database database) {
        super("Edit WiFi Vendo", EditWifiVendoWindow.class.getResource("add_wifi.fxml"), null, null);
        wifiVendoController = new WifiVendoController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        // change Save to Update
        btnSave.setText("Update");

        cbStatus.setItems(ViewUtils.vendoStatusList);
        cbStatus.getSelectionModel().select(0); // select first

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
    }

    public void showAndWait(WifiVendo vendo) {
        if (vendo == null) {
            showWarningDialog("Invalid", "No selected WiFi Vendo entry. Try again.");
            return;
        }
        mVendo = vendo;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        tfName.setText(mVendo.getName());
        tfIpAddress.setText(mVendo.getIpAddress());
        cbStatus.setValue(mVendo.getStatus());
    }

    private boolean validated() {
        lblError.setVisible(false);
        if (tfName.getText().isBlank() || tfIpAddress.getText().isBlank() || cbStatus.getValue() == null) {
            lblError.setVisible(true);
            lblError.setText("Please fill-up empty field(s).");
            return false;
        }
        return true;
    }

    @Override
    protected void onClose() {
        clearFields();
        mVendo = null;
    }

    private void clearFields() {
        tfName.clear();
        tfIpAddress.clear();
        cbStatus.getSelectionModel().select(0);
        lblError.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }

    private void saveAndClose() {
        disposables.add(Single.fromCallable(() -> {
            mVendo.setName(ViewUtils.normalize(tfName.getText()));
            mVendo.setIpAddress(ViewUtils.normalize(tfIpAddress.getText()));
            mVendo.setStatus(cbStatus.getValue());
            return wifiVendoController.update(mVendo);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            if (!success) showWarningDialog("Failed", "Failed to update WiFi Vendo entry.");
            close();
        }, err -> {
            showErrorDialog("Database Error", "Error while updating WiFi Vendo entry.\n" + err);
        }));
    }
}
