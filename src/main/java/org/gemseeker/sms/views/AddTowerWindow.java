package org.gemseeker.sms.views;

import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.Account;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Tower;
import org.gemseeker.sms.data.controllers.AccountController;
import org.gemseeker.sms.data.controllers.TowerController;

public class AddTowerWindow extends AbstractWindow {

    @FXML private ComboBox<Account> cbAccounts;
    @FXML private Label lblErrAccount;
    @FXML private TextField tfName;
    @FXML private Label lblErrName;
    @FXML private ComboBox<String> cbTowerTypes;
    @FXML private CheckBox cbAddTowerInfo;
    @FXML private TextField tfTowerHeight;
    @FXML private TextField tfLatitude;
    @FXML private TextField tfLongitude;
    @FXML private TextField tfElevation;
    @FXML private ComboBox<Tower> cbParentTower;
    @FXML private Label lblErrTowerType;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final AccountController accountController;
    private final TowerController towerController;
    private final CompositeDisposable disposables;

    public AddTowerWindow(Database database) {
        super("Add Tower Info", AddTowerWindow.class.getResource("add_tower.fxml"), null, null);
        accountController = new AccountController(database);
        towerController = new TowerController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        ViewUtils.setAsNumericalTextField(tfTowerHeight, tfLatitude, tfLongitude, tfElevation);

        cbAccounts.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) checkIfTowerExist(newVal);
        });

        cbTowerTypes.setItems(FXCollections.observableArrayList(
                Tower.TYPE_DEFAULT, Tower.TYPE_ACCESS_POINT, Tower.TYPE_RELAY
        ));

        btnSave.setOnAction(evt -> validateAndSave());

        btnCancel.setOnAction(evt -> close());
    }


    @Override
    protected void onShow() {
        clearFields();
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(towerController::getAll)
                .flatMap(towers -> {
                    Platform.runLater(() -> {
                        cbParentTower.setItems(towers);
                    });
                    return Single.fromCallable(accountController::getAll);
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(accounts -> {
                    progressBar.setVisible(false);
                    cbAccounts.setItems(accounts);
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while retrieving Account and Tower entries.\n" + err);
                }));
    }

    private void checkIfTowerExist(Account account) {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> towerController.hasTower(account.getAccountNo()))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(hasTower -> {
                    progressBar.setVisible(false);
                    if (hasTower) showWarningDialog("Tower Exist", "This account already has a " +
                            "Tower entry. Select another account.");
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while querying database.\n" + err);
                }));
    }

    private void validateAndSave() {
        lblErrAccount.setVisible(false);
        lblErrName.setVisible(false);
        lblErrTowerType.setVisible(false);

        lblErrName.setVisible(tfName.getText().isBlank());
        lblErrTowerType.setVisible(cbTowerTypes.getValue() == null);

        boolean isValid = cbAccounts.getValue() != null && !tfName.getText().isBlank()
                        && cbTowerTypes.getValue() != null;

        if (isValid) saveAndClose();
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            Tower tower = new Tower();
            tower.setAccountNo(cbAccounts.getValue().getAccountNo());
            tower.setName(ViewUtils.normalize(tfName.getText()));
            tower.setType(cbTowerTypes.getValue());
            String latStr = tfLatitude.getText();
            tower.setLatitude(latStr.isBlank() ? 0.0f : Float.parseFloat(latStr.trim()));
            String longStr = tfLongitude.getText();
            tower.setLongitude((longStr.isBlank() ? 0.0f : Float.parseFloat(longStr.trim())));
            String heightStr = tfTowerHeight.getText();
            tower.setTowerHeight(heightStr.isBlank() ? 0.0f : Float.parseFloat(heightStr.trim()));
            String elevStr = tfElevation.getText();
            tower.setElevation(elevStr.isBlank() ? 0.0f : Float.parseFloat(elevStr.trim()));
            Tower parent = cbParentTower.getValue();
            if (parent != null) tower.setParentTowerId(parent.getId());
            return towerController.insert(tower);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to add new Tower entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while adding new Tower info.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
    }

    private void clearFields() {
        cbAccounts.setValue(null);
        cbTowerTypes.getSelectionModel().select(0);
        tfName.clear();
        tfTowerHeight.setText("0.0");
        tfLatitude.setText("0.0");
        tfLongitude.setText("0.0");
        tfElevation.setText("0.0");

        lblErrAccount.setVisible(false);
        lblErrName.setVisible(false);
        lblErrTowerType.setVisible(false);
    }

    private void setupIcons() {
        lblErrAccount.setGraphic(new XCircleIcon(12));
        lblErrName.setGraphic(new XCircleIcon(12));
        lblErrTowerType.setGraphic(new XCircleIcon(12));
    }

    public void dispose() {
        disposables.dispose();
    }
}
