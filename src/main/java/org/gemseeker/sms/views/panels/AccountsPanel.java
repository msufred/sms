package org.gemseeker.sms.views.panels;

import io.github.msufred.feathericons.*;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gemseeker.sms.data.DataPlan;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.AccountController;
import org.gemseeker.sms.data.controllers.DataPlanController;
import org.gemseeker.sms.data.controllers.TowerController;
import org.gemseeker.sms.data.controllers.models.AccountSubscription;
import org.gemseeker.sms.views.*;
import org.gemseeker.sms.views.cells.AmountTableCell;
import org.gemseeker.sms.views.cells.DateTableCell;
import org.gemseeker.sms.views.cells.StatusTableCell;
import org.gemseeker.sms.views.cells.TagTableCell;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Optional;

public class AccountsPanel extends AbstractPanel {

    @FXML private TabPane tabPane;
    @FXML private Tab tabAccounts;
    @FXML private Tab tabPlans;

    @FXML private Button btnAdd;
    @FXML private MenuButton mEdit;
    @FXML private MenuItem mEditAccount;
    @FXML private MenuItem mEditSubscription;
    @FXML private MenuItem mEditTower;
    @FXML private Button btnDelete;
    @FXML private Button btnRefresh;
    @FXML private Label lblFilter;
    @FXML private ComboBox<String> cbStatus;
    @FXML private Label lblSearch;
    @FXML private TextField tfSearch;
    @FXML private TableView<AccountSubscription> accountsTable;
    @FXML private TableColumn<AccountSubscription, String> colTag;
    @FXML private TableColumn<AccountSubscription, String> colNo;
    @FXML private TableColumn<AccountSubscription, String> colStatus;
    @FXML private TableColumn<AccountSubscription, String> colName;
    @FXML private TableColumn<AccountSubscription, String> colContact;
    @FXML private TableColumn<AccountSubscription, String> colAddress;
    @FXML private TableColumn<AccountSubscription, String> colEmail;
    @FXML private TableColumn<AccountSubscription, String> colSubscription;
    @FXML private TableColumn<AccountSubscription, LocalDate> colStartDate;
    @FXML private TableColumn<AccountSubscription, LocalDate> colEndDate;
    @FXML private TableColumn<AccountSubscription, Double> colMonthlyFee;

    // DataPlan
    @FXML private Button btnAddPlan;
    @FXML private Button btnEditPlan;
    @FXML private Button btnDeletePlan;
    @FXML private TableView<DataPlan> dataPlansTable;
    @FXML private TableColumn<DataPlan, String> colPlanTag;
    @FXML private TableColumn<DataPlan, String> colPlanName;
    @FXML private TableColumn<DataPlan, Integer> colPlanSpeed;
    @FXML private TableColumn<DataPlan, Double> colPlanFee;

    // Tag Icons
    private final LinkedHashMap<String, SVGIcon> tags = ViewUtils.getTags();

    private FilteredList<AccountSubscription> filteredList;
    private final SimpleObjectProperty<AccountSubscription> selectedItem = new SimpleObjectProperty<>();
    private FilteredList<DataPlan> dataPlanList;
    private final SimpleObjectProperty<DataPlan> selectedPlan = new SimpleObjectProperty<>();

    private final MainWindow mainWindow;
    private final Database database;
    private final AccountController accountController;
    private final DataPlanController dataPlanController;
    private final TowerController towerController;
    private final CompositeDisposable disposables;

    // Windows
    private AddAccountWindow addAccountWindow;
    private EditAccountInfoWindow editAccountInfoWindow;
    private EditSubscriptionWindow editSubscriptionWindow;
    private AddTowerWindow addTowerWindow;
    private EditTowerWindow editTowerWindow;
    private AddDataPlanWindow addDataPlanWindow;
    private EditDataPlanWindow editDataPlanWindow;

    public AccountsPanel(MainWindow mainWindow, Database database) {
        super(AccountsPanel.class.getResource("accounts.fxml"));
        this.mainWindow = mainWindow;
        this.database = database;
        this.accountController = new AccountController(database);
        this.dataPlanController = new DataPlanController(database);
        this.towerController = new TowerController(database);
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        setupTable();
        setupDataPlanTable();

        tabPane.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
            if (newVal.intValue() == 0) refresh();
            else refreshPlans();
        });

        btnAdd.setOnAction(evt -> addItem());
        mEditAccount.setOnAction(evt -> editAccount());
        mEditSubscription.setOnAction(evt -> editSubscription());
        mEditTower.setOnAction(evt -> editTowerInfo());
        btnDelete.setOnAction(evt -> deleteSelected());
        btnRefresh.setOnAction(evt -> refresh());

        cbStatus.setItems(FXCollections.observableArrayList(
                "All", "Active", "Inactive", "Deactivated"
        ));
        cbStatus.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal.isBlank() || filteredList == null) return;
            if (newVal == "All") filteredList.setPredicate(p -> true);
            else filteredList.setPredicate(acct -> acct.getStatus().equalsIgnoreCase(newVal));
        });
        cbStatus.setValue("All");

        tfSearch.textProperty().addListener((o, oldVal, newVal) -> {
            if (filteredList == null) return;
            if (newVal.isBlank()) filteredList.setPredicate(p -> true);
            else filteredList.setPredicate(account -> {
                return account.getName().toLowerCase().contains(newVal.toLowerCase()) ||
                        account.getAddress().toLowerCase().contains(newVal.toLowerCase()) ||
                        account.getEmail().toLowerCase().contains(newVal.toLowerCase());
            });
        });

        // Data Plan
        btnAddPlan.setOnAction(evt -> addPlan());
        btnEditPlan.setOnAction(evt -> editPlan());
        btnDeletePlan.setOnAction(evt -> deletePlan());
    }

    @Override
    public void onResume() {
        if (tabPane.getSelectionModel().getSelectedIndex() == 0) refresh();
        else refreshPlans();
    }

    @Override
    public void onPause() {
        // empty for now
    }

    private void refresh() {
        showProgress("Retrieving Accounts...");
        disposables.add(Single.fromCallable(accountController::getAccountsWithSubscription)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    filteredList = new FilteredList<>(list);
                    // TODO clear filters
                    accountsTable.setItems(filteredList);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Account list.\n" + err);
                }));
    }

    private void refreshPlans() {
        showProgress("Retrieving Data Plan entries...");
        disposables.add(Single.fromCallable(dataPlanController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    dataPlanList = new FilteredList<>(list);
                    dataPlansTable.setItems(dataPlanList);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Data Plan entries.\n" + err);
                }));
    }

    private void addItem() {
        if (addAccountWindow == null) addAccountWindow = new AddAccountWindow(database, mainWindow.getStage());
        addAccountWindow.showAndWait();
        refresh();
    }

    private void editAccount() {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid", "No selected Account. Try again.");
        } else {
            if (editAccountInfoWindow == null) editAccountInfoWindow = new EditAccountInfoWindow(database, mainWindow.getStage());
            editAccountInfoWindow.showAndWait(selectedItem.get().getAccountNo());
            refresh();
        }
    }

    private void editSubscription() {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid", "No selected Account. Try again.");
        } else {
            if (editSubscriptionWindow == null) editSubscriptionWindow = new EditSubscriptionWindow(database, mainWindow.getStage());
            editSubscriptionWindow.showAndWait(selectedItem.get().getAccountNo());
            refresh();
        }
    }

    private void editTowerInfo() {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid", "No selected Account. Try again.");
        } else {
            showProgress("Checking Tower info...");
            disposables.add(Single.fromCallable(() -> towerController.hasTower(selectedItem.get().getAccountNo()))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(hasTower -> {
                        hideProgress();
                        if (hasTower) {
                            if (editTowerWindow == null) editTowerWindow = new EditTowerWindow(database, mainWindow.getStage());
                            editTowerWindow.showAndWait(selectedItem.get().getAccountNo());
                        } else {
                            if (addTowerWindow == null) addTowerWindow = new AddTowerWindow(database, mainWindow.getStage());
                            addTowerWindow.showAndWait();
                        }
                        refresh();
                    }, err -> {
                        showErrorDialog("Database Error", "Error while checking Tower info.\n" + err);
                    }));
        }
    }

    private void deleteSelected() {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid", "No selected Account. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete Account",
                    "Are you sure you want to delete this Account?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) {
                delete(selectedItem.get().getId());
            }
        }
    }

    private void delete(int id) {
        showProgress("Deleting Account...");
        disposables.add(Single.fromCallable(() -> accountController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    refresh();
                    if (!success) showWarningDialog("Failed", "Failed to delete Account entry.");
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting Account.\n" + err);
                }));
    }

    private void changeSelectedStatus(String newStatus) {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid", "No selected Account. Try again.");
        } else {
            showProgress("Changing Account status...");
            disposables.add(Single.fromCallable(() -> accountController.update(selectedItem.get().getId(), "status", newStatus))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                        hideProgress();
                        refresh();
                    }, err -> {
                        hideProgress();
                        showErrorDialog("Database Error", "Error while updating Account status.\n" + err);
                    }));
        }
    }

    private void changeSelectedTag(String tag) {
        if (selectedItem.get() == null || tag == null) return;
        showProgress("Changing Account tag...");
        disposables.add(Single.fromCallable(() -> accountController.update(selectedItem.get().getId(), "tag", tag))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    refresh();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while updating Account tag.\n" + err);
                }));
    }

    // DataPlan
    private void addPlan() {
        if (addDataPlanWindow == null) addDataPlanWindow = new AddDataPlanWindow(database, mainWindow.getStage());
        addDataPlanWindow.showAndWait();
        refreshPlans();
    }

    private void editPlan() {
        if (selectedPlan.get() == null) {
            showWarningDialog("Invalid Action", "No selected DataPlan entry. Try again.");
        } else {
            if (editDataPlanWindow == null) editDataPlanWindow = new EditDataPlanWindow(database, mainWindow.getStage());
            editDataPlanWindow.showAndWait(selectedPlan.get());
            refreshPlans();
        }
    }

    private void deletePlan() {
        if (selectedPlan.get() == null) {
            showWarningDialog("Invalid Action", "No selected DataPlan entry. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete Data Plan",
                    "Are you sure you want to delete this Data Plan entry?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) deletePlan(selectedPlan.get().getId());
        }
    }

    private void deletePlan(int id) {
        showProgress("Deleting Data Plan entry...");
        disposables.add(Single.fromCallable(() -> dataPlanController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to delete Data Plan entry.");
                    refreshPlans();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting Data Plan entry.\n" + err);
                }));
    }

    private void updatePlanTag(String tag) {
        if (selectedPlan.get() == null) {
            showWarningDialog("Invalid Action", "No selected DataPlan entry. Try again.");
        } else {
            showProgress("Upading Data Plan entry...");
            disposables.add(Single.fromCallable(() -> dataPlanController.update(selectedPlan.get().getId(), "tag", tag))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                        hideProgress();
                        if (!success) showWarningDialog("Failed", "Failed to update Data Plan entry.");
                        refreshPlans();
                    }, err -> {
                        hideProgress();
                        showErrorDialog("Database Error", "Error while updating Data Plan entry.\n" + err);
                    }));
        }
    }

    private void setupIcons() {
        tabAccounts.setGraphic(new UsersIcon(14));
        tabPlans.setGraphic(new WifiIcon(14));

        btnAdd.setGraphic(new PlusIcon(14));
        mEdit.setGraphic(new Edit2Icon(14));
        mEditAccount.setGraphic(new UserIcon(14));
        mEditSubscription.setGraphic(new RssIcon(14));
        mEditTower.setGraphic(new WifiIcon(14));
        btnRefresh.setGraphic(new RefreshCwIcon(14));
        btnDelete.setGraphic(new TrashIcon(14));
        lblFilter.setGraphic(new FilterIcon(14));
        lblSearch.setGraphic(new SearchIcon(14));

        btnAddPlan.setGraphic(new PlusIcon(14));
        btnEditPlan.setGraphic(new Edit2Icon(14));
        btnDeletePlan.setGraphic(new TrashIcon(14));
    }

    private void setupTable() {
        colTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colTag.setCellFactory(col -> new TagTableCell<>());
        colNo.setCellValueFactory(new PropertyValueFactory<>("accountNo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellFactory(col -> new StatusTableCell<>());
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSubscription.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colStartDate.setCellFactory(col -> new DateTableCell<>());
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colEndDate.setCellFactory(col -> new DateTableCell<>());
        colMonthlyFee.setCellValueFactory(new PropertyValueFactory<>("monthlyFee"));
        colMonthlyFee.setCellFactory(col -> new AmountTableCell<>());

        MenuItem mAdd = new MenuItem("New Account");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addItem());

        MenuItem mEditAccount = new MenuItem("Account Info");
        mEditAccount.setGraphic(new UserIcon(12));
        mEditAccount.setOnAction(evt -> editAccount());

        MenuItem mEditSubscription = new MenuItem("Subscription");
        mEditSubscription.setGraphic(new RssIcon(12));
        mEditSubscription.setOnAction(evt -> editSubscription());

        MenuItem mEditTower = new MenuItem("Tower Info");
        mEditTower.setGraphic(new WifiIcon(12));
        mEditTower.setOnAction(evt -> editTowerInfo());

        Menu mEdit = new Menu("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.getItems().addAll(mEditAccount, mEditSubscription, mEditTower);

        MenuItem mStatActive = new MenuItem("Active");
        mStatActive.setGraphic(new SmileIcon(12));
        mStatActive.setOnAction(evt -> changeSelectedStatus("Active"));

        MenuItem mStatInactive = new MenuItem("Inactive");
        mStatInactive.setGraphic(new FrownIcon(12));
        mStatInactive.setOnAction(evt -> changeSelectedStatus("Inactive"));

        MenuItem mStatDeactivated = new MenuItem("Deactivated");
        mStatDeactivated.setGraphic(new XOctagonIcon(12));
        mStatDeactivated.setOnAction(evt -> changeSelectedStatus("Deactivated"));

        Menu mChangeStatus = new Menu("Change Status");
        mChangeStatus.setGraphic(new SmileIcon(12));
        mChangeStatus.getItems().addAll(mStatActive, mStatInactive, mStatDeactivated);

        Menu mChangeTag = new Menu("Change Tag");
        mChangeTag.setGraphic(new CircleIcon(12));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.setGraphic(icon);
            item.setOnAction(evt -> changeSelectedTag(tag));
            mChangeTag.getItems().add(item);
        });

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteSelected());

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mDelete, mChangeStatus, mChangeTag);
        accountsTable.setContextMenu(cm);

        selectedItem.bind(accountsTable.getSelectionModel().selectedItemProperty());
    }

    private void setupDataPlanTable() {
        colPlanTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colPlanTag.setCellFactory(col -> new TagTableCell<>());
        colPlanName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPlanSpeed.setCellValueFactory(new PropertyValueFactory<>("speed"));
        colPlanFee.setCellValueFactory(new PropertyValueFactory<>("monthlyFee"));
        colPlanFee.setCellFactory(col -> new AmountTableCell<>());

        MenuItem mAdd = new MenuItem("Add");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addPlan());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editPlan());

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deletePlan());

        Menu mTag = new Menu("Change Tag");
        mTag.setGraphic(new CircleIcon(12));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.setGraphic(icon);
            item.setOnAction(evt -> updatePlanTag(tag));
            mTag.getItems().add(item);
        });

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mDelete, mTag);
        dataPlansTable.setContextMenu(cm);
        selectedPlan.bind(dataPlansTable.getSelectionModel().selectedItemProperty());
    }

    private void showProgress(String text) {
        mainWindow.showProgress(-1, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    @Override
    public void onDispose() {
        if (addAccountWindow != null) addAccountWindow.dispose();
        if (editAccountInfoWindow != null) editAccountInfoWindow.dispose();
        if (addTowerWindow != null) addTowerWindow.dispose();
        if (editTowerWindow != null) editTowerWindow.dispose();
        if (addDataPlanWindow != null) addDataPlanWindow.dispose();
        if (editDataPlanWindow != null) editDataPlanWindow.dispose();
        disposables.dispose();
    }
}
