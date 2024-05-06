package org.gemseeker.sms.views;

import io.github.msufred.feathericons.CheckCircleIcon;
import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.gemseeker.sms.data.Account;
import org.gemseeker.sms.data.Billing;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Subscription;
import org.gemseeker.sms.data.controllers.AccountController;
import org.gemseeker.sms.data.controllers.BillingController;
import org.gemseeker.sms.data.controllers.SubscriptionController;

import java.util.concurrent.Callable;

/**
 *
 * @author Gem
 */
public class AddBillingWindow extends AbstractWindow {

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

    // billing number validation icons
    private final XCircleIcon xCircleIcon = new XCircleIcon(14);
    private final CheckCircleIcon checkCircleIcon = new CheckCircleIcon(14);

    private final AccountController accountController;
    private final SubscriptionController subscriptionController;
    private final BillingController billingController;
    private final CompositeDisposable disposables;

    private Subscription mSubscription;
    private boolean mBillingValid = false;

    public AddBillingWindow(Database database) {
        super("Add Billing Payment", AddBillingWindow.class.getResource("add_billing.fxml"), null, null);
        accountController = new AccountController(database);
        subscriptionController = new SubscriptionController(database);
        billingController = new BillingController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        btnSave.setText("Validate");
        ViewUtils.setAsNumericalTextField(tfRate);

        btnCheck.setOnAction(evt -> checkBillingNo(null));

        cbAccounts.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) loadSubscriptionDetails(newVal);
        });

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
    }

    @Override
    protected void onShow() {
        clearFields();
        disableActions(true);
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(accountController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    cbAccounts.setItems(list);
                    disableActions(false);
                    progressBar.setVisible(false);
                }, err -> {
                    disableActions(false);
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while retrieving Account entries.\n" + err);
                }));
    }

    private void checkBillingNo(Callable<Void> task) {
        lblErrBillingNo.getStyleClass().removeAll("label-error", "label-success");
        if (tfBillingNo.getText().isBlank()) {
            lblErrBillingNo.getStyleClass().add("label-error");
            lblErrBillingNo.setGraphic(xCircleIcon);
            return;
        }

        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> billingController.hasBilling(ViewUtils.normalize(tfBillingNo.getText())))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(hasBilling -> {
                    progressBar.setVisible(false);
                    lblErrBillingNo.getStyleClass().add(hasBilling ? "label-error" : "label-success");
                    lblErrBillingNo.setGraphic(hasBilling ? xCircleIcon : checkCircleIcon);
                    mBillingValid = !hasBilling;
                    if (task != null) task.call();
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while querying database.\n" + err);
                }));
    }

    private void loadSubscriptionDetails(Account account) {
        disableActions(true);
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> subscriptionController.getByAccountNo(account.getAccountNo()))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(sub -> {
                    disableActions(false);
                    progressBar.setVisible(false);
                    if (sub != null) {
                        tfPlanType.setText(sub.getPlanType());
                        tfBandwidth.setText(sub.getSpeed() + " MBPS");
                        tfRate.setText(String.format("%.2f", sub.getMonthlyFee()));
                    }
                    mSubscription = sub;
                }, err -> {
                    disableActions(false);
                    progressBar.setVisible(false);
                    // Sub is null
                    showErrorDialog("Database Error", "No Subscription found for Account entry.");
                }));
    }

    private boolean validated() {
        checkBillingNo(() -> {
            lblErrAccount.setVisible(false);
            lblErrFromDate.setVisible(false);
            lblErrToDate.setVisible(false);
            lblErrDueDate.setVisible(false);

            lblErrAccount.setVisible(cbAccounts.getValue() == null);
            lblErrFromDate.setVisible(dpFrom.getValue() == null);
            lblErrToDate.setVisible(dpTo.getValue() == null);
            lblErrDueDate.setVisible(dpDue.getValue() == null);

            mBillingValid = cbAccounts.getValue() != null && dpFrom.getValue() != null && dpTo.getValue() != null &&
                    dpDue.getValue() != null;
            if (mBillingValid) btnSave.setText("Save Billing");
            return null;
        });
        return mBillingValid;
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            Billing billing = new Billing();
            billing.setBillingNo(ViewUtils.normalize(tfBillingNo.getText()));
            billing.setAccountNo(cbAccounts.getValue().getAccountNo());
            billing.setToPay(mSubscription != null ? mSubscription.getMonthlyFee() : 0);
            billing.setFromDate(dpFrom.getValue());
            billing.setToDate(dpTo.getValue());
            billing.setDueDate(dpDue.getValue());
            return billingController.insert(billing);
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
    }

    private void clearFields() {
        cbAccounts.setItems(null);
        tfBandwidth.clear();
        tfPlanType.clear();
        tfRate.clear();
        dpFrom.setValue(null);
        dpTo.setValue(null);
        dpDue.setValue(null);

        lblErrBillingNo.setGraphic(null);
        lblErrAccount.setVisible(false);
        lblErrFromDate.setVisible(false);
        lblErrToDate.setVisible(false);
        lblErrDueDate.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }

}
