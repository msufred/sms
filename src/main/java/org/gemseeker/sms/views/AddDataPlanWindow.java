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
import javafx.stage.Stage;
import org.gemseeker.sms.data.DataPlan;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.DataPlanController;
import org.gemseeker.sms.views.icons.XCircleIcon;

/**
 *
 * @author Gem
 */
public class AddDataPlanWindow extends AbstractWindow {

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

    public AddDataPlanWindow(Database database, Stage owner) {
        super("Add Data Plan", AddAccountWindow.class.getResource("add_dataplan.fxml"), null, owner);
        dataPlanController = new DataPlanController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        lblErrName.setGraphic(new XCircleIcon(14));
        lblErrSpeed.setGraphic(new XCircleIcon(14));
        lblErrFee.setGraphic(new XCircleIcon(14));

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
        lblErrName.setVisible(false);
        lblErrSpeed.setVisible(false);
        lblErrFee.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
