package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Service;
import org.gemseeker.sms.data.controllers.ServiceController;


public class AddServiceWindow extends AbstractWindow {

    @FXML private TextField tfName;
    @FXML private TextField tfPrice;
    @FXML private TextArea taDescription;
    @FXML private Label lblError;
    @FXML private Button btnSave;

    private final ServiceController serviceController;
    private final CompositeDisposable disposables;

    public AddServiceWindow(Database database, Stage owner) {
        super("Add Service", AddServiceWindow.class.getResource("add_service.fxml"), null, owner);
        serviceController = new ServiceController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        btnSave.setOnAction(evt -> {
            if (tfName.getText().isEmpty() || tfPrice.getText().isEmpty()) {
                lblError.setVisible(true);
                lblError.setText("Please fill-up required fields.");
            } else {
                saveAndClose();
            }
        });
    }

    @Override
    protected void onShow() {
        clearFields();
    }

    private void saveAndClose() {
        disposables.add(Single.fromCallable(() -> {
            Service service = new Service();
            service.setName(ViewUtils.normalize(tfName.getText()));
            String priceStr = tfPrice.getText();
            service.setPrice(priceStr.isBlank() ? 0.0 : Double.parseDouble(priceStr.trim()));
            service.setDescription(ViewUtils.normalize(taDescription.getText()));
            return serviceController.insert(service);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            if (!success) showWarningDialog("Failed", "Failed to add new Service entry.");
            close();
        }, err -> {
            showErrorDialog("Database Error", "Error while adding new Service entry.");
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
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
