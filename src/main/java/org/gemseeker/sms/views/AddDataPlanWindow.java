package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import org.gemseeker.sms.data.DataPlan;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.DataPlanController;

/**
 *
 * @author Gem
 */
public class AddDataPlanWindow extends AbstractWindow {

    @FXML private TextField tfName;
    @FXML private TextField tfBandwidth;
    @FXML private TextField tfAmount;
    @FXML private Label lblError;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final DataPlanController dataPlanController;
    private final CompositeDisposable disposables;

    public AddDataPlanWindow(Database database) {
        super("Add Data Plan", AddAccountWindow.class.getResource("add_dataplan.fxml"), null, null);
        dataPlanController = new DataPlanController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        ViewUtils.setAsIntegerTextField(tfBandwidth);
        ViewUtils.setAsNumericalTextField(tfAmount);
        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
        btnCancel.setOnAction(evt -> close());
    }

    @Override
    protected void onShow() {
        clearFields();
    }

    private boolean validated() {
        boolean isValid = true;
        if (tfName.getText().isBlank() || tfBandwidth.getText().isBlank() || tfAmount.getText().isBlank()) {
            isValid = false;
            lblError.setText("Please fill-up empty field(s).");
        }
        return isValid;
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            DataPlan plan = new DataPlan();
            plan.setName(ViewUtils.normalize(tfName.getText()));
            String speedStr = tfBandwidth.getText();
            plan.setSpeed(speedStr.isBlank() ? 0 : Integer.parseInt(speedStr.trim()));
            String feeStr = tfAmount.getText();
            plan.setMonthlyFee(feeStr.isBlank() ? 0.0 : Double.parseDouble(feeStr.trim()));
            return dataPlanController.insert(plan);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Add DataPlan Failed", "Failed to add new DataPlan entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while adding DataPlan entry.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
    }

    private void clearFields() {
        tfName.clear();
        tfBandwidth.clear();
        tfAmount.clear();
        lblError.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
