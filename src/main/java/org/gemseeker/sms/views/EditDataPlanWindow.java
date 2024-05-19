package org.gemseeker.sms.views;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.DataPlan;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.DataPlanController;

/**
 *
 * @author Gem
 */
public class EditDataPlanWindow extends AbstractWindow {

    @FXML private TextField tfName;
    @FXML private TextField tfBandwidth;
    @FXML private TextField tfAmount;
    @FXML private Label lblErrName;
    @FXML private Label lblErrSpeed;
    @FXML private Label lblErrFee;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final DataPlanController dataPlanController;
    private final CompositeDisposable disposables;

    private DataPlan mPlan;

    public EditDataPlanWindow(Database database, Stage owner) {
        super("Add Data Plan", AddAccountWindow.class.getResource("add_dataplan.fxml"), null, owner);
        dataPlanController = new DataPlanController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        lblErrName.setGraphic(new XCircleIcon(14));
        lblErrSpeed.setGraphic(new XCircleIcon(14));
        lblErrFee.setGraphic(new XCircleIcon(14));
        btnSave.setText("Update");

        ViewUtils.setAsIntegerTextField(tfBandwidth);
        ViewUtils.setAsNumericalTextField(tfAmount);

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
        btnCancel.setOnAction(evt -> close());
    }

    public void showAndWait(DataPlan plan) {
        if (plan == null) {
            showWarningDialog("Invalid Action", "No selected Data Plan entry. Try again.");
            return;
        }
        mPlan = plan;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        tfName.setText(mPlan.getName());
        tfBandwidth.setText(mPlan.getSpeed() + "");
        tfAmount.setText(mPlan.getMonthlyFee() + "");
    }

    private boolean validated() {
        lblErrName.setVisible(false);
        lblErrSpeed.setVisible(false);
        lblErrFee.setVisible(false);

        lblErrName.setVisible(tfName.getText().isBlank());
        lblErrSpeed.setVisible(tfBandwidth.getText().isBlank());
        lblErrFee.setVisible(tfAmount.getText().isBlank());

        return !tfName.getText().isBlank() && !tfAmount.getText().isBlank() && !tfBandwidth.getText().isBlank();
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            mPlan.setName(ViewUtils.normalize(tfName.getText()));
            String speedStr = tfBandwidth.getText();
            mPlan.setSpeed(speedStr.isBlank() ? 0 : Integer.parseInt(speedStr.trim()));
            String feeStr = tfAmount.getText();
            mPlan.setMonthlyFee(feeStr.isBlank() ? 0.0 : Double.parseDouble(feeStr.trim()));
            return dataPlanController.update(mPlan);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Add DataPlan Failed", "Failed to update DataPlan entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while updating DataPlan entry.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
        mPlan = null;
    }

    private void clearFields() {

        tfName.clear();
        tfBandwidth.setText("0");
        tfAmount.setText("0.0");
        lblErrName.setVisible(false);
        lblErrSpeed.setVisible(false);
        lblErrFee.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
