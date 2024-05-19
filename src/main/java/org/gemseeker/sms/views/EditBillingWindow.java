package org.gemseeker.sms.views;

import io.github.msufred.feathericons.CheckCircleIcon;
import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.Account;
import org.gemseeker.sms.data.Billing;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Subscription;
import org.gemseeker.sms.data.controllers.AccountController;
import org.gemseeker.sms.data.controllers.BillingController;
import org.gemseeker.sms.data.controllers.SubscriptionController;

/**
 *
 * @author Gem
 */
public class EditBillingWindow extends AbstractWindow {

    @FXML private TextField tfBillingNo;
    @FXML private Button btnCheck;
    @FXML private ComboBox<Account> cbAccounts;
    @FXML private TextField tfPlanType;
    @FXML private TextField tfBandwidth;
    @FXML private TextField tfRate;
    @FXML private DatePicker dpFrom;
    @FXML private DatePicker dpTo;
    @FXML private DatePicker dpDue;

    @FXML private Label lblErrBillingNo;
    @FXML private Label lblErrAccount;
    @FXML private Label lblErrFromDate;
    @FXML private Label lblErrToDate;
    @FXML private Label lblErrDueDate;

    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;

    private final AccountController accountController;
    private final SubscriptionController subscriptionController;
    private final BillingController billingController;
    private final CompositeDisposable disposables;

    private Subscription mSubscription;
    private String mBillingNo;
    private Billing mBilling;
    private Account mAccount;

    public EditBillingWindow(Database database, Stage owner) {
        super("Edit Billing", EditBillingWindow.class.getResource("add_billing.fxml"), null, owner);
        accountController = new AccountController(database);
        subscriptionController = new SubscriptionController(database);
        billingController = new BillingController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        btnSave.setText("Update Billing");
        btnCheck.setVisible(false);
        lblErrBillingNo.setVisible(false);
        tfBillingNo.setEditable(false);

        ViewUtils.setAsNumericalTextField(tfRate);

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
    }

    public void showAndWait(String billingNo) {
        mBillingNo = billingNo;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        disableActions(true);
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> billingController.getByBillingNo(mBillingNo)) // get Billing entry
                .flatMap(billing -> Single.fromCallable(() -> {
                    // get Account & Subscription
                    mAccount = accountController.getByAccountNo(billing.getAccountNo());
                    mSubscription = subscriptionController.getByAccountNo(billing.getAccountNo());
                    if (mAccount != null && mSubscription != null) {
                        Platform.runLater(() -> {
                            tfBillingNo.setText(billing.getBillingNo());
                            cbAccounts.setItems(FXCollections.observableArrayList(mAccount));
                            cbAccounts.setValue(mAccount);
                            tfPlanType.setText(mSubscription.getPlanType());
                            tfBandwidth.setText(mSubscription.getSpeed() + " MBPS");
                            tfRate.setText(String.format("%.2f", mSubscription.getMonthlyFee()));
                        });
                    }
                    return billing;
                })).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(billing -> {
                    disableActions(false);
                    progressBar.setVisible(false);
                    if (billing != null) {
                        dpFrom.setValue(billing.getFromDate());
                        dpTo.setValue(billing.getToDate());
                        dpDue.setValue(billing.getDueDate());
                        mBilling = billing;
                    } else {
                        close();
                    }
                }, err -> {
                    disableActions(false);
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while retrieving Billing entry.\n" + err);
                }));
    }

    private boolean validated() {
        lblErrAccount.setVisible(false);
        lblErrFromDate.setVisible(false);
        lblErrToDate.setVisible(false);
        lblErrDueDate.setVisible(false);

        lblErrAccount.setVisible(cbAccounts.getValue() == null);
        lblErrFromDate.setVisible(dpFrom.getValue() == null);
        lblErrToDate.setVisible(dpTo.getValue() == null);
        lblErrDueDate.setVisible(dpDue.getValue() == null);

        return cbAccounts.getValue() != null && dpFrom.getValue() != null && dpTo.getValue() != null &&
                dpDue.getValue() != null;
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            mBilling.setFromDate(dpFrom.getValue());
            mBilling.setToDate(dpTo.getValue());
            mBilling.setDueDate(dpDue.getValue());
            return billingController.update(mBilling);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to add new Billing entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while adding new Billing entry.\n" + err);
        }));
    }

    private void disableActions(boolean disable) {
        btnSave.setDisable(disable);
    }

    private void setupIcons() {
        lblErrAccount.setGraphic(new XCircleIcon(14));
        lblErrFromDate.setGraphic(new XCircleIcon(14));
        lblErrToDate.setGraphic(new XCircleIcon(14));
        lblErrDueDate.setGraphic(new XCircleIcon(14));
    }

    @Override
    protected void onClose() {
        clearFields();
        mSubscription = null;
        mBillingNo = null;
        mBilling = null;
        mAccount = null;
    }

    private void clearFields() {
        cbAccounts.setItems(null);
        tfBandwidth.clear();
        tfPlanType.clear();
        tfRate.clear();
        dpFrom.setValue(null);
        dpTo.setValue(null);
        dpDue.setValue(null);

        lblErrAccount.setVisible(false);
        lblErrFromDate.setVisible(false);
        lblErrToDate.setVisible(false);
        lblErrDueDate.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }

}
