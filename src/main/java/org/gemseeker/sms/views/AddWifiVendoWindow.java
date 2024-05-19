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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.WifiVendo;
import org.gemseeker.sms.data.controllers.WifiVendoController;

public class AddWifiVendoWindow extends AbstractWindow {

    @FXML private TextField tfName;
    @FXML private TextField tfIpAddress;
    @FXML private ComboBox<String> cbStatus;
    @FXML private Label lblError;
    @FXML private Button btnSave;

    private final WifiVendoController wifiVendoController;
    private final CompositeDisposable disposables;

    public AddWifiVendoWindow(Database database, Stage owner) {
        super("Add WiFi Vendo", AddWifiVendoWindow.class.getResource("add_wifi.fxml"), null, owner);
        wifiVendoController = new WifiVendoController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        cbStatus.setItems(ViewUtils.vendoStatusList);
        cbStatus.getSelectionModel().select(0); // select first

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
    }

    @Override
    protected void onShow() {
        clearFields();
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
            WifiVendo vendo = new WifiVendo();
            vendo.setName(ViewUtils.normalize(tfName.getText()));
            vendo.setIpAddress(ViewUtils.normalize(tfIpAddress.getText()));
            vendo.setStatus(cbStatus.getValue());
            return wifiVendoController.insert(vendo);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            if (!success) showWarningDialog("Failed", "Failed to add new WiFi Vendo entry.");
            close();
        }, err -> {
            showErrorDialog("Database Error", "Error while adding new WiFi Vendo entry.\n" + err);
        }));
    }
}
