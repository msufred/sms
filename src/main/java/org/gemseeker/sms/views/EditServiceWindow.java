package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Service;
import org.gemseeker.sms.data.controllers.ServiceController;


public class EditServiceWindow extends AbstractWindow {

    @FXML private TextField tfName;
    @FXML private TextField tfPrice;
    @FXML private TextArea taDescription;
    @FXML private Label lblError;
    @FXML private Button btnSave;

    private final ServiceController serviceController;
    private final CompositeDisposable disposables;

    private Service mService;

    public EditServiceWindow(Database database) {
        super("Add Service", EditServiceWindow.class.getResource("add_service.fxml"), null, null);
        serviceController = new ServiceController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        btnSave.setText("Update Service");
        btnSave.setOnAction(evt -> {
            if (tfName.getText().isEmpty() || tfPrice.getText().isEmpty()) {
                lblError.setVisible(true);
                lblError.setText("Please fill-up required fields.");
            } else {
                saveAndClose();
            }
        });
    }

    public void showAndWait(Service service) {
        if (service == null) {
            showWarningDialog("Invalid", "No selected Service entry. Try again.");
            return;
        }
        mService = service;
    }

    @Override
    protected void onShow() {
        clearFields();
        tfName.setText(mService.getName());
        tfPrice.setText(String.format("%.2f", mService.getPrice()));
        taDescription.setText(mService.getDescription());
    }

    private void saveAndClose() {
        disposables.add(Single.fromCallable(() -> {
            mService.setName(ViewUtils.normalize(tfName.getText()));
            String priceStr = tfPrice.getText();
            mService.setPrice(priceStr.isBlank() ? 0.0 : Double.parseDouble(priceStr.trim()));
            mService.setDescription(ViewUtils.normalize(taDescription.getText()));
            return serviceController.update(mService);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            if (!success) showWarningDialog("Failed", "Failed to update Service entry.");
            close();
        }, err -> {
            showErrorDialog("Database Error", "Error while updating Service entry.");
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
        mService = null;
    }

    private void clearFields() {
        tfName.clear();
        tfPrice.clear();
        taDescription.clear();
        lblError.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
