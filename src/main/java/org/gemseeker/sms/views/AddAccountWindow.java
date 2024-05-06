package org.gemseeker.sms.views;

import io.github.msufred.feathericons.CheckCircleIcon;
import io.github.msufred.feathericons.PlusIcon;
import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.AccountController;
import org.gemseeker.sms.data.controllers.DataPlanController;
import org.gemseeker.sms.data.controllers.SubscriptionController;
import org.gemseeker.sms.data.controllers.TowerController;

import java.util.concurrent.Callable;

public class AddAccountWindow extends AbstractWindow {

    // account info group
    @FXML private TextField tfAccountNo;
    @FXML private Button btnCheck;
    @FXML private Label lblAccountNo;
    @FXML private TextField tfName;
    @FXML private TextField tfAddress;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPhone;
    @FXML private Label lblErrName;
    @FXML private Label lblErrAddress;

    // subscription group
    @FXML private CheckBox cbAddSubscription;
    @FXML private ComboBox<DataPlan> cbDataPlans;
    @FXML private Button btnAdd;
    @FXML private TextField tfBandwidth;
    @FXML private TextField tfIpAddress;
    @FXML private TextField tfAmount;
    @FXML private DatePicker dpStart;
    @FXML private DatePicker dpEnd;
    @FXML private Label lblErrPlanType;
    @FXML private Label lblErrStartDate;
    @FXML private Label lblErrEndDate;

    // tower info group
    @FXML private ComboBox<String> cbTowerTypes;
    @FXML private CheckBox cbAddTowerInfo;
    @FXML private TextField tfTowerHeight;
    @FXML private TextField tfLatitude;
    @FXML private TextField tfLongitude;
    @FXML private TextField tfElevation;
    @FXML private ComboBox<Tower> cbParentTower;

    @FXML private VBox subscriptionGroup;
    @FXML private VBox towerInfoGroup;

    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final Database database;

    private final DataPlanController dataPlanController;
    private final AccountController accountController;
    private final SubscriptionController subscriptionController;
    private final TowerController towerController;
    private final CompositeDisposable disposables;

    private AddDataPlanWindow addDataPlanWindow;

    // accountNo validation icons
    private final XCircleIcon xCircleIcon = new XCircleIcon(14);
    private final CheckCircleIcon checkCircleIcon = new CheckCircleIcon(14);

    private boolean mValidAccountNo = false;
    private boolean mAccountValid = false;
    private boolean mSubscriptionValid = false;

    public AddAccountWindow(Database database) {
        super("Add Account", AddAccountWindow.class.getResource("add_account.fxml"), null, null);
        dataPlanController = new DataPlanController(database);
        accountController = new AccountController(database);
        subscriptionController = new SubscriptionController(database);
        towerController = new TowerController(database);
        disposables = new CompositeDisposable();
        this.database = database;
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        btnSave.setText("Validate");

        ViewUtils.setAsNumericalTextField(tfTowerHeight, tfLatitude, tfLongitude, tfElevation);

        btnCheck.setOnAction(evt -> checkAccountNo(null));

        cbAddSubscription.selectedProperty().addListener((o, oldVal, selected) -> {
            subscriptionGroup.setDisable(!selected);
        });

        cbAddTowerInfo.selectedProperty().addListener((o, oldVal, selected) -> {
            towerInfoGroup.setDisable(!selected);
        });

        cbDataPlans.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
                tfBandwidth.setText(newVal.getSpeed() + "");
                tfAmount.setText(String.format("%.2f", newVal.getMonthlyFee()));
            }
        });

        cbTowerTypes.setItems(FXCollections.observableArrayList(
                Tower.TYPE_DEFAULT, Tower.TYPE_RELAY, Tower.TYPE_ACCESS_POINT
        ));
        cbTowerTypes.setValue(Tower.TYPE_DEFAULT);

        btnAdd.setOnAction(evt -> {
            if (addDataPlanWindow == null) addDataPlanWindow = new AddDataPlanWindow(database);
            addDataPlanWindow.showAndWait();
            refreshDataPlans();
        });

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });

        btnCancel.setOnAction(evt -> close());
    }

    @Override
    protected void onShow() {
        clearFields();
        loadData();
    }

    private void loadData() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> dataPlanController.getAll())
                .flatMap(plans -> {
                    Platform.runLater(() -> cbDataPlans.setItems(plans));
                    return Single.fromCallable(() -> towerController.getAll());
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(towers -> {
                    progressBar.setVisible(false);
                    cbParentTower.setItems(towers);
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while retrieving Data Plan and Tower list.\n" + err);
                }));
    }

    private void refreshDataPlans() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> dataPlanController.getAll())
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    progressBar.setVisible(false);
                    cbDataPlans.setItems(list);
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while retrieving DataPlan entries.\n" + err);
                }));
    }

    private void checkAccountNo(Callable<Boolean> task) {
        lblAccountNo.getStyleClass().removeAll("label-error", "label-success");
        if (tfAccountNo.getText().isBlank()) {
            lblAccountNo.getStyleClass().add("label-error");
            lblAccountNo.setGraphic(xCircleIcon);
            return;
        }

        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> accountController.hasAccount(ViewUtils.normalize(tfAccountNo.getText())))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(hasAccount -> {
                    progressBar.setVisible(false);
                    lblAccountNo.getStyleClass().add(hasAccount ? "label-error" : "label-success");
                    lblAccountNo.setGraphic(hasAccount ? xCircleIcon : checkCircleIcon);
                    mValidAccountNo = !hasAccount;
                    if (task != null) task.call(); // do task after
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while querying database.\n" + err);
                }));
    }

    private boolean validated() {
        // check account first, then validate fields (just in case account number wasn't checked)
        checkAccountNo(() -> {
            // reset error labels
            lblErrName.setVisible(false);
            lblErrAddress.setVisible(false);
            lblErrPlanType.setVisible(false);
            lblErrStartDate.setVisible(false);
            lblErrEndDate.setVisible(false);

            // account info
            lblErrName.setVisible(tfName.getText().isBlank());
            lblErrAddress.setVisible(tfAddress.getText().isBlank());
            mAccountValid = !tfName.getText().isBlank() && !tfAddress.getText().isBlank();

            // subscription info
            if (cbAddSubscription.isSelected()) {
                lblErrPlanType.setVisible(cbDataPlans.getValue() == null);
                lblErrStartDate.setVisible(dpStart.getValue() == null);
                lblErrEndDate.setVisible(dpEnd.getValue() == null);

                mSubscriptionValid = cbDataPlans.getValue() != null &&
                        !tfBandwidth.getText().isBlank() && !tfAmount.getText().isBlank() &&
                        dpStart.getValue() != null && dpEnd.getValue() != null;
            } else {
                mSubscriptionValid = true;
            }
            if (mValidAccountNo && mAccountValid && mSubscriptionValid) {
                btnSave.setText("Save Account");
            }
            return null;
        });
        // NOTE: no need to validate Tower info, if fields are empty, set values to 0
        return mValidAccountNo && mAccountValid && mSubscriptionValid;
    }

    private void saveAndClose() {
        disposables.add(Single.fromCallable(() -> {
            Account account = getAccountInfo();
            boolean added = accountController.insert(account);
            return added ? account.getAccountNo() : "";
        }).flatMap(accountNo -> Single.fromCallable(() -> {
            if (!accountNo.isBlank() && cbAddSubscription.isSelected()) {
                Subscription sub = getSubscriptionInfo();
                sub.setAccountNo(accountNo);
                subscriptionController.insert(sub);
            }
            return accountNo;
        })).flatMap(accountNo -> Single.fromCallable(() -> {
            if (!accountNo.isBlank() && cbAddTowerInfo.isSelected()) {
                Tower tower = getTowerInfo();
                tower.setAccountNo(accountNo);
                towerController.insert(tower);
            }
            return accountNo;
        })).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(accountNo -> {
            progressBar.setVisible(false);
            if (accountNo.isBlank()) showWarningDialog("Add Account Failed", "Account No. is empty.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while adding Account entry.\n" + err);
        }));
    }

    private Account getAccountInfo() {
        Account account = new Account();
        account.setAccountNo(ViewUtils.normalize(tfAccountNo.getText()));
        account.setName(ViewUtils.normalize(tfName.getText()));
        account.setAddress(ViewUtils.normalize(tfAddress.getText()));
        account.setEmail(ViewUtils.normalize(tfEmail.getText()));
        account.setPhone(ViewUtils.normalize(tfPhone.getText()));
        return account;
    }

    private Subscription getSubscriptionInfo() {
        Subscription sub = new Subscription();
        sub.setPlanType(cbDataPlans.getValue().getName());
        String speedStr = tfBandwidth.getText();
        sub.setSpeed(speedStr.isBlank() ? 0 : Integer.parseInt(speedStr.trim()));
        String feeStr = tfAmount.getText();
        sub.setMonthlyFee(feeStr.isBlank() ? 0 : Double.parseDouble(feeStr.trim()));
        sub.setIpAddress(ViewUtils.normalize(tfIpAddress.getText()));
        sub.setStartDate(dpStart.getValue());
        sub.setEndDate(dpEnd.getValue());
        return sub;
    }

    private Tower getTowerInfo() {
        Tower tower = new Tower();
        tower.setType(cbTowerTypes.getValue());
        String latStr = tfLatitude.getText();
        tower.setLatitude(latStr.isBlank() ? 0.0f : Float.parseFloat(latStr.trim()));
        String longStr = tfLongitude.getText();
        tower.setLongitude(longStr.isBlank() ? 0.0f : Float.parseFloat(longStr.trim()));
        String elevStr = tfElevation.getText();
        tower.setElevation(elevStr.isBlank() ? 0.0f : Float.parseFloat(elevStr.trim()));
        String heightStr = tfTowerHeight.getText();
        tower.setTowerHeight(heightStr.isBlank() ? 0.0 : Double.parseDouble(heightStr.trim()));
        tower.setIpAddress(ViewUtils.normalize(tfIpAddress.getText()));
        if (cbParentTower.getValue() != null) tower.setParentTowerId(cbParentTower.getValue().getId());
        return tower;
    }

    @Override
    protected void onClose() {
        clearFields();
        mValidAccountNo = false;
        mAccountValid = false;
        mSubscriptionValid = false;
    }

    private void setupIcons() {
        btnAdd.setGraphic(new PlusIcon(14));
        lblErrName.setGraphic(new XCircleIcon(14));
        lblErrAddress.setGraphic(new XCircleIcon(14));
        lblErrPlanType.setGraphic(new XCircleIcon(14));
        lblErrStartDate.setGraphic(new XCircleIcon(14));
        lblErrEndDate.setGraphic(new XCircleIcon(14));
    }

    private void clearFields() {
        tfName.clear();
        tfAccountNo.clear();
        tfAddress.clear();
        tfEmail.clear();
        tfPhone.clear();
        cbAddSubscription.setSelected(false);
        cbDataPlans.getSelectionModel().select(-1);
        tfBandwidth.setText("0");
        tfAmount.setText("0.0");
        tfIpAddress.clear();
        dpStart.setValue(null);
        dpEnd.setValue(null);
        cbAddTowerInfo.setSelected(false);
        cbTowerTypes.getSelectionModel().select(0);
        tfTowerHeight.setText("0.0");
        tfLatitude.setText("0.0");
        tfLongitude.setText("0.0");
        tfElevation.setText("0.0");
        cbParentTower.setValue(null);

        lblAccountNo.setGraphic(null);
        lblErrName.setVisible(false);
        lblErrAddress.setVisible(false);
        lblErrPlanType.setVisible(false);
        lblErrStartDate.setVisible(false);
        lblErrEndDate.setVisible(false);
    }

    public void dispose() {
        if (addDataPlanWindow != null) addDataPlanWindow.dispose();
        disposables.dispose();
    }
}
