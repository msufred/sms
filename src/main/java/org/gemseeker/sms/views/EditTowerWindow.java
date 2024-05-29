package org.gemseeker.sms.views;

import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Tower;
import org.gemseeker.sms.data.controllers.TowerController;

public class EditTowerWindow extends AbstractWindow {

    public static final double LATITUDE = 6.34137;
    public static final double LONGITUDE = 124.72314;

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

    private final TowerController towerController;
    private final CompositeDisposable disposables;

    private String mAccountNo;
    private Tower mTower;

    public EditTowerWindow(Database database, Stage owner) {
        super("Edit Tower Info", EditTowerWindow.class.getResource("edit_tower.fxml"), null, owner);
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

        cbTowerTypes.setItems(FXCollections.observableArrayList(
                Tower.TYPE_DEFAULT, Tower.TYPE_ACCESS_POINT, Tower.TYPE_RELAY
        ));

        cbParentTower.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null && mTower != null) {
                if (newVal.getId() == mTower.getId()) {
                    showWarningDialog("Invalid Tower Value", "You can't select the same Tower. Try again.");
                }
            }
        });

        btnSave.setOnAction(evt -> validateAndSave());

        btnCancel.setOnAction(evt -> close());
    }

    public void showAndWait(String accountNo) {
        if (accountNo == null) {
            showWarningDialog("Invalid", "No selected Account entry. Try again.");
            return;
        }

        mAccountNo = accountNo;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> towerController.getByAccountNo(mAccountNo))
                .flatMap(tower -> {
                    mTower = tower;
                    return Single.fromCallable(towerController::getAll);
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    progressBar.setVisible(false);
                    cbParentTower.setItems(list);
                    fillUpFields();
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error while retrieving Account and Tower entries.\n" + err);
                }));
    }

    private void fillUpFields() {
        if (mTower == null) return;
        tfName.setText(mTower.getName());
        cbTowerTypes.setValue(mTower.getType());
        tfTowerHeight.setText(String.format("%f", mTower.getTowerHeight()));
        tfLatitude.setText(String.format("%f", mTower.getLatitude()));
        tfLongitude.setText(String.format("%f", mTower.getLongitude()));
        tfElevation.setText(String.format("%f", mTower.getElevation()));
        Tower parent = null;
        for (Tower t : cbParentTower.getItems()) {
            if (t.getId() == mTower.getParentTowerId()) {
                parent = t;
                break;
            }
        }
        cbParentTower.setValue(parent);
    }

    private void validateAndSave() {
        lblErrName.setVisible(false);
        lblErrTowerType.setVisible(false);

        lblErrName.setVisible(tfName.getText().isBlank());
        lblErrTowerType.setVisible(cbTowerTypes.getValue() == null);

        boolean isValid = !tfName.getText().isBlank() && cbTowerTypes.getValue() != null;

        if (isValid) saveAndClose();
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            mTower.setName(ViewUtils.normalize(tfName.getText()));
            mTower.setType(cbTowerTypes.getValue());
            String latStr = tfLatitude.getText();
            mTower.setLatitude(latStr.isBlank() ? 0.0f : Float.parseFloat(latStr.trim()));
            String longStr = tfLongitude.getText();
            mTower.setLongitude((longStr.isBlank() ? 0.0f : Float.parseFloat(longStr.trim())));
            String heightStr = tfTowerHeight.getText();
            mTower.setTowerHeight(heightStr.isBlank() ? 0.0f : Float.parseFloat(heightStr.trim()));
            String elevStr = tfElevation.getText();
            mTower.setElevation(elevStr.isBlank() ? 0.0f : Float.parseFloat(elevStr.trim()));
            Tower parent = cbParentTower.getValue();
            if (parent != null && parent.getId() != mTower.getId()) {
                mTower.setParentTowerId(parent.getId());
                mTower.setParentName(parent.getName());
            }
            return towerController.update(mTower);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to update Tower entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while updating Tower info.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
        mAccountNo = null;
        mTower = null;
    }

    private void clearFields() {
        cbTowerTypes.getSelectionModel().select(0);
        tfName.clear();
        tfTowerHeight.setText("0.0");
        tfLatitude.setText(LATITUDE + "");
        tfLongitude.setText(LONGITUDE + "");
        tfElevation.setText("0.0");

        lblErrName.setVisible(false);
        lblErrTowerType.setVisible(false);
    }

    private void setupIcons() {
        lblErrName.setGraphic(new XCircleIcon(12));
        lblErrTowerType.setGraphic(new XCircleIcon(12));
    }

    public void dispose() {
        disposables.dispose();
    }
}
