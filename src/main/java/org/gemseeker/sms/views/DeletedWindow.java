package org.gemseeker.sms.views;

import io.github.msufred.feathericons.Edit2Icon;
import io.github.msufred.feathericons.RefreshCcwIcon;
import io.github.msufred.feathericons.UsersIcon;
import io.github.msufred.feathericons.WifiIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.Account;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Tower;
import org.gemseeker.sms.data.controllers.AccountController;
import org.gemseeker.sms.data.controllers.BillingController;
import org.gemseeker.sms.data.controllers.TowerController;
import org.gemseeker.sms.views.cells.DateTimeTableCell;
import org.gemseeker.sms.views.cells.StatusTableCell;
import org.gemseeker.sms.views.cells.TagTableCell;
import org.gemseeker.sms.views.icons.PesoIcon;

import java.time.LocalDateTime;

public class DeletedWindow extends AbstractWindow {

    @FXML private TabPane tabPane;
    @FXML private Tab tabAccounts;
    @FXML private Tab tabPayments;
    @FXML private Tab tabTowers;
    @FXML private Tab tabVendos;

    // Accounts
    @FXML private Button btnEditAccount;
    @FXML private Button btnRestoreAccount;
    @FXML private TableView<Account> accountsTable;
    @FXML private TableColumn<Account, String> colAccountTag;
    @FXML private TableColumn<Account, String> colAccountNo;
    @FXML private TableColumn<Account, String> colAccountStatus;
    @FXML private TableColumn<Account, String> colAccountName;
    @FXML private TableColumn<Account, String> colAccountContact;
    @FXML private TableColumn<Account, String> colAccountAddress;
    @FXML private TableColumn<Account, String> colAccountEmail;
    @FXML private TableColumn<Account, LocalDateTime> colAccountCreatedAt;
    @FXML private TableColumn<Account, LocalDateTime> colAccountUpdatedAt;
    @FXML private TableColumn<Account, LocalDateTime> colAccountDeletedAt;

    // Payments
    @FXML private Button btnEditPayment;
    @FXML private Button btnRestorePayment;

    // Towers
    @FXML private Button btnEditTower;
    @FXML private Button btnRestoreTower;
    @FXML private TableView<Tower> towersTable;
    @FXML private TableColumn<Tower, String> colTowerTag;
    @FXML private TableColumn<Tower, String> colTowerStatus;
    @FXML private TableColumn<Tower, String> colTowerType;
    @FXML private TableColumn<Tower, String> colTowerAccountNo;
    @FXML private TableColumn<Tower, String> colTowerName;
    @FXML private TableColumn<Tower, Float> colTowerLatitude;
    @FXML private TableColumn<Tower, Float> colTowerLongitude;
    @FXML private TableColumn<Tower, Float> colTowerElevation;
    @FXML private TableColumn<Tower, Double> colTowerHeight;
    @FXML private TableColumn<Tower, String> colTowerIpAddress;
    @FXML private TableColumn<Tower, Integer> colTowerParentId;
    @FXML private TableColumn<Tower, String> colTowerParentName;
    @FXML private TableColumn<Tower, LocalDateTime> colTowerCreatedAt;
    @FXML private TableColumn<Tower, LocalDateTime> colTowerUpdatedAt;
    @FXML private TableColumn<Tower, LocalDateTime> colTowerDeletedAt;

    // WiFi Vendo
    @FXML private Button btnEditVendo;
    @FXML private Button btnRestoreVendo;

    @FXML private ProgressBar progressBar;
    @FXML private Label lblProgress;

    private FilteredList<Account> accountList;
    private FilteredList<Tower> towersList;

    private final SimpleObjectProperty<Account> selectedAccount = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Tower> selectedTower = new SimpleObjectProperty<>();

    private final Database database;
    private final AccountController accountController;
    private final TowerController towerController;
    private final CompositeDisposable disposables;

    private EditAccountInfoWindow editAccountInfoWindow;
    private EditTowerWindow editTowerWindow;

    public DeletedWindow(Database database, Stage owner) {
        super("Deleted Entries", DeletedWindow.class.getResource("deleted_window.fxml"), null, owner);
        this.database = database;
        accountController = new AccountController(database);
        towerController = new TowerController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.NONE);
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        setupAccountsTable();
        setupTowersTable();

        tabPane.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
            switch (newVal.intValue()) {
                case 1 -> refreshPayments();
                case 2 -> refreshTowers();
                case 3 -> refreshVendos();
                default -> refreshAccounts();
            }
        });
    }

    @Override
    protected void onShow() {
        tabPane.getSelectionModel().select(0);
        refreshAccounts();
    }

    private void refreshAccounts() {
        showProgress("Retrieving deleted Account entries...");
        disposables.add(Single.fromCallable(accountController::getDeleted)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    accountList = new FilteredList<>(list);
                    accountsTable.setItems(accountList);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving deleted Account entries.\n" + err);
                }));
    }

    private void editAccount() {
        if (selectedAccount.get() == null) {
            showWarningDialog("Invalid Action", "No selected Account entry. Try again.");
        } else {
            if (editAccountInfoWindow == null) editAccountInfoWindow = new EditAccountInfoWindow(database, getStage());
            editAccountInfoWindow.showAndWait(selectedAccount.get().getAccountNo());
            refreshAccounts();
        }
    }

    private void restoreAccount() {
        if (selectedAccount.get() == null) {
            showWarningDialog("Invalid Action", "No selected Account entry. Try again.");
        } else {
            showProgress("Restoring Account...");
            disposables.add(Single.fromCallable(() -> accountController.restore(selectedAccount.get().getId()))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                        hideProgress();
                        if (!success) showWarningDialog("Failed", "Failed to restore Account entry.");
                        refreshAccounts();
                    }, err -> {
                        hideProgress();
                        showErrorDialog("Database Error", "Error while restoring Account entry.\n" + err);
                    }));
        }
    }

    private void refreshPayments() {

    }

    private void refreshTowers() {
        showProgress("Retrieving deleted Tower entries...");
        disposables.add(Single.fromCallable(towerController::getDeleted)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    towersList = new FilteredList<>(list);
                    towersTable.setItems(towersList);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving deleted Tower entries.\n" + err);
                }));
    }

    private void editTower() {
        if (selectedTower.get() == null) {
            showWarningDialog("Invalid Action", "No selected Tower entry. Try again.");
        } else {
            if (editTowerWindow == null) editTowerWindow = new EditTowerWindow(database, getStage());
            editTowerWindow.showAndWait(selectedTower.get().getAccountNo());
            refreshTowers();
        }
    }

    private void restoreTower() {
        if (selectedTower.get() == null) {
            showWarningDialog("Invalid Action", "No selected Tower entry. Try again.");
        } else {
            showProgress("Restoring deleted Tower entry...");
            disposables.add(Single.fromCallable(() -> towerController.restore(selectedTower.get().getId()))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                        hideProgress();
                        if (!success) showWarningDialog("Failed", "Failed to restore deleted Tower entry.");
                        refreshTowers();
                    }, err -> {
                        hideProgress();
                        showErrorDialog("Database Error", "Error while restoring deleted Tower entry.\n" + err);
                    }));
        }
    }

    private void refreshVendos() {

    }

    @Override
    protected void onClose() {
        // empty
    }

    private void setupAccountsTable() {
        btnEditAccount.setOnAction(evt -> editAccount());
        btnRestoreAccount.setOnAction(evt -> restoreAccount());

        colAccountTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colAccountTag.setCellFactory(col -> new TagTableCell<>());
        colAccountNo.setCellValueFactory(new PropertyValueFactory<>("accountNo"));
        colAccountStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAccountStatus.setCellFactory(col -> new StatusTableCell<>());
        colAccountName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAccountAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAccountContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAccountEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAccountCreatedAt.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        colAccountCreatedAt.setCellFactory(col -> new DateTimeTableCell<>());
        colAccountUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("dateUpdated"));
        colAccountUpdatedAt.setCellFactory(col -> new DateTimeTableCell<>());
        colAccountDeletedAt.setCellValueFactory(new PropertyValueFactory<>("dateDeleted"));
        colAccountDeletedAt.setCellFactory(col -> new DateTimeTableCell<>());

        MenuItem mRestore = new MenuItem("Restore");
        mRestore.setGraphic(new RefreshCcwIcon(12));
        mRestore.setOnAction(evt -> restoreAccount());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editAccount());

        ContextMenu cm = new ContextMenu(mEdit, mRestore);
        accountsTable.setContextMenu(cm);
        selectedAccount.bind(accountsTable.getSelectionModel().selectedItemProperty());
    }

    private void setupTowersTable() {
        btnEditTower.setOnAction(evt -> editTower());
        btnRestoreTower.setOnAction(evt -> restoreTower());

        colTowerTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colTowerTag.setCellFactory(col -> new TagTableCell<>());
        colTowerStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTowerStatus.setCellFactory(col -> new StatusTableCell<>());
        colTowerType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colTowerAccountNo.setCellValueFactory(new PropertyValueFactory<>("accountNo"));
        colTowerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTowerLatitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        colTowerLongitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        colTowerElevation.setCellValueFactory(new PropertyValueFactory<>("elevation"));
        colTowerHeight.setCellValueFactory(new PropertyValueFactory<>("towerHeight"));
        colTowerIpAddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        colTowerParentId.setCellValueFactory(new PropertyValueFactory<>("parentId"));
        colTowerParentName.setCellValueFactory(new PropertyValueFactory<>("parentName"));
        colTowerCreatedAt.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        colTowerCreatedAt.setCellFactory(col -> new DateTimeTableCell<>());
        colTowerUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("dateUpdated"));
        colTowerUpdatedAt.setCellFactory(col -> new DateTimeTableCell<>());
        colTowerDeletedAt.setCellValueFactory(new PropertyValueFactory<>("dateDeleted"));
        colTowerDeletedAt.setCellFactory(col -> new DateTimeTableCell<>());

        MenuItem mRestore = new MenuItem("Restore");
        mRestore.setGraphic(new RefreshCcwIcon(12));
        mRestore.setOnAction(evt -> restoreTower());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editTower());

        ContextMenu cm = new ContextMenu(mEdit, mRestore);
        towersTable.setContextMenu(cm);
        selectedTower.bind(towersTable.getSelectionModel().selectedItemProperty());
    }

    private void setupIcons() {
        tabAccounts.setGraphic(new UsersIcon(14));
        tabPayments.setGraphic(new PesoIcon(14));
        tabTowers.setGraphic(new WifiIcon(14));
        tabVendos.setGraphic(new WifiIcon(14));

        btnEditAccount.setGraphic(new Edit2Icon(14));
        btnRestoreAccount.setGraphic(new RefreshCcwIcon(14));
        btnEditPayment.setGraphic(new Edit2Icon(14));
        btnRestorePayment.setGraphic(new RefreshCcwIcon(14));
        btnEditTower.setGraphic(new Edit2Icon(14));
        btnRestoreTower.setGraphic(new RefreshCcwIcon(14));
        btnEditVendo.setGraphic(new Edit2Icon(14));
        btnRestoreVendo.setGraphic(new RefreshCcwIcon(14));
    }

    private void showProgress(String text) {
        progressBar.setVisible(true);
        lblProgress.setText(text);
        lblProgress.setVisible(true);
    }

    private void hideProgress() {
        progressBar.setVisible(false);
        lblProgress.setVisible(false);
        lblProgress.setText("");
    }

    public void dispose() {
        disposables.dispose();
    }
}
