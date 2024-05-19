package org.gemseeker.sms.views;

import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.DataPlan;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Subscription;
import org.gemseeker.sms.data.controllers.DataPlanController;
import org.gemseeker.sms.data.controllers.SubscriptionController;

public class EditSubscriptionWindow extends AbstractWindow {

    @FXML private ComboBox<DataPlan> cbDataPlans;
    @FXML private TextField tfBandwidth;
    @FXML private TextField tfIpAddress;
    @FXML private TextField tfAmount;
    @FXML private DatePicker dpStart;
    @FXML private DatePicker dpEnd;
    @FXML private Label lblErrPlanType;
    @FXML private Label lblErrStartDate;
    @FXML private Label lblErrEndDate;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final SubscriptionController subscriptionController;
    private final DataPlanController dataPlanController;
    private final CompositeDisposable disposables;

    private String mAccountNo;
    private Subscription mSubscription;
    private ObservableList<DataPlan> mPlans;

    public EditSubscriptionWindow(Database database, Stage owner) {
        super("Subscription Info", EditAccountInfoWindow.class.getResource("edit_subscription.fxml"), null, owner);
        subscriptionController = new SubscriptionController(database);
        dataPlanController = new DataPlanController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();

        ViewUtils.setAsIntegerTextField(tfBandwidth);
        ViewUtils.setAsNumericalTextField(tfAmount);

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
        btnCancel.setOnAction(evt -> close());
    }

    public void showAndWait(String accountNo) {
        if (accountNo == null) {
            showWarningDialog("Invalid Action", "No selected Account entry. Try again.");
            return;
        }
        mAccountNo = accountNo;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(dataPlanController::getAll)
                .flatMap(plans -> {
                    mPlans = plans;
                    return Single.fromCallable(() -> subscriptionController.getByAccountNo(mAccountNo));
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(subscription -> {
                    progressBar.setVisible(false);
                    mSubscription = subscription;
                    fillupFields();
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error or no Subscription found:\n" + err);
                }));
    }

    private void fillupFields() {
        if (mPlans != null && mSubscription != null) {
            cbDataPlans.setItems(mPlans);
            DataPlan plan = null;
            for (DataPlan p : mPlans) {
                if (p.getName().equals(mSubscription.getPlanType())) {
                    plan = p;
                    break;
                }
            }
            cbDataPlans.setValue(plan);

            tfBandwidth.setText(mSubscription.getSpeed() + "");
            tfAmount.setText(mSubscription.getMonthlyFee() + "");
            tfIpAddress.setText(mSubscription.getIpAddress());
            dpStart.setValue(mSubscription.getStartDate());
            dpEnd.setValue(mSubscription.getEndDate());
        }
    }

    private boolean validated() {
        lblErrPlanType.setVisible(false);
        lblErrStartDate.setVisible(false);
        lblErrEndDate.setVisible(false);
        lblErrPlanType.setVisible(cbDataPlans.getValue() == null);
        lblErrStartDate.setVisible(dpStart.getValue() == null);
        lblErrEndDate.setVisible(dpEnd.getValue() == null);
        return cbDataPlans.getValue() != null && dpStart.getValue() != null && dpEnd != null;
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            mSubscription.setPlanType(cbDataPlans.getValue().getName());
            String speedStr = tfBandwidth.getText().trim();
            mSubscription.setSpeed(speedStr.isBlank() ? 0 : Integer.parseInt(speedStr));
            String amountStr = tfAmount.getText().trim();
            mSubscription.setMonthlyFee(amountStr.isBlank() ? 0.0 : Double.parseDouble(amountStr));
            mSubscription.setIpAddress(ViewUtils.normalize(tfIpAddress.getText()));
            mSubscription.setStartDate(dpStart.getValue());
            mSubscription.setEndDate(dpEnd.getValue());
            return subscriptionController.update(mSubscription);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to update Subscription entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while updating Subscription entry.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
        mAccountNo = null;
        mSubscription = null;
        mPlans = null;
    }

    private void setupIcons() {
        lblErrPlanType.setGraphic(new XCircleIcon(14));
        lblErrStartDate.setGraphic(new XCircleIcon(14));
        lblErrEndDate.setGraphic(new XCircleIcon(14));
    }

    private void clearFields() {
        cbDataPlans.setValue(null);
        tfBandwidth.setText("0");
        tfAmount.setText("0.0");
        tfIpAddress.clear();
        dpStart.setValue(null);
        dpEnd.setValue(null);
        lblErrPlanType.setVisible(false);
        lblErrStartDate.setVisible(false);
        lblErrEndDate.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
