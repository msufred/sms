package org.gemseeker.sms.views.panels;

import com.gluonhq.attach.storage.StorageService;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import io.github.msufred.feathericons.Edit2Icon;
import io.github.msufred.feathericons.MapPinIcon;
import io.github.msufred.feathericons.PlusIcon;
import io.github.msufred.feathericons.TrashIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Tower;
import org.gemseeker.sms.data.controllers.TowerController;
import org.gemseeker.sms.views.AddTowerWindow;
import org.gemseeker.sms.views.EditTowerWindow;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.panels.maps.LineLayer;
import org.gemseeker.sms.views.panels.maps.SourceLayer;
import org.gemseeker.sms.views.panels.maps.TowerLayer;
import org.gemseeker.sms.views.panels.maps.TowerPoint;

import java.util.Optional;

public class MapsPanel extends AbstractPanel {

    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private ListView<Tower> listview;
    @FXML private StackPane stackPane;
    @FXML private Label lblCoordinate;
    @FXML private Label lblLatitude;
    @FXML private Label lblLongitude;
    private MapView mapView;

    private FilteredList<Tower> filteredList;
    private final SimpleObjectProperty<Tower> selectedItem = new SimpleObjectProperty<>();

    private final MainWindow mainWindow;
    private final Database database;
    private final TowerController towerController;
    private final CompositeDisposable disposables;

    private AddTowerWindow addTowerWindow;
    private EditTowerWindow editTowerWindow;

    private SourceLayer sourceLayer;
    private TowerLayer towerLayer;
    private LineLayer lineLayer;

    public MapsPanel(MainWindow mainWindow, Database database) {
        super(MapsPanel.class.getResource("maps.fxml"));
        this.mainWindow = mainWindow;
        this.database = database;
        towerController = new TowerController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();

        btnAdd.setOnAction(evt -> addItem());
        btnEdit.setOnAction(evt -> editSelected());
        btnDelete.setOnAction(evt -> deleteSelected());

        setupListView();
        setupMapView();
    }

    private void setupListView() {
        // ListView ContextMenu
        MenuItem mAdd = new MenuItem("Add Tower");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addItem());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editSelected());

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteSelected());

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mDelete);
        listview.setContextMenu(cm);
        selectedItem.bind(listview.getSelectionModel().selectedItemProperty());
    }

    private void setupMapView() {
        mapView = new MapView();
        mapView.addEventHandler(MouseEvent.MOUSE_MOVED, evt -> {
            MapPoint mapPoint = mapView.getMapPosition(evt.getSceneX(), evt.getSceneY());
            lblLatitude.setText(mapPoint.getLatitude() + "");
            lblLongitude.setText(mapPoint.getLongitude() + "");
        });
        mapView.setZoom(15);
        mapView.setCenter(new MapPoint(6.3414027, 124.7205275));
        stackPane.getChildren().add(mapView);
    }

    @Override
    public void onResume() {
        refreshList();
    }

    private void refreshList() {
        showProgress("Retrieving Tower entries...");
        disposables.add(Single.fromCallable(towerController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    filteredList = new FilteredList<>(list);
                    listview.setItems(filteredList);
                    refreshMap(list);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Tower entries.\n" + err);
                }));
    }

    private void refreshMap(ObservableList<Tower> list) {
        if (sourceLayer == null) {
            sourceLayer = new SourceLayer();
        }

        // clear layers
        mapView.removeLayer(lineLayer);
        mapView.removeLayer(towerLayer);
        mapView.removeLayer(sourceLayer);

        ObservableList<TowerPoint> towers = getTowerPoints(list);

        lineLayer = new LineLayer(towers);
        towerLayer = new TowerLayer(list);
        mapView.addLayer(lineLayer);
        mapView.addLayer(towerLayer);
        mapView.addLayer(sourceLayer);
    }

    private static ObservableList<TowerPoint> getTowerPoints(ObservableList<Tower> list) {
        ObservableList<TowerPoint> towers = FXCollections.observableArrayList();
        for (Tower t : list) {
            Tower parent = null;
            for (Tower t2 : list) {
                if (t2.getId() == t.getParentTowerId()) {
                    parent = t2;
                    break;
                }
            }
            TowerPoint towerPoint = new TowerPoint(t, parent);
            towers.add(towerPoint);
        }
        return towers;
    }


    @Override
    public void onPause() {

    }

    private void addItem() {
        if (addTowerWindow == null) addTowerWindow = new AddTowerWindow(database, mainWindow.getStage());
        addTowerWindow.showAndWait();
        refreshList();
    }

    private void editSelected() {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Tower entry. Try again.");
        } else {
            if (editTowerWindow == null) editTowerWindow = new EditTowerWindow(database, mainWindow.getStage());
            editTowerWindow.showAndWait(selectedItem.get().getAccountNo());
            refreshList();
        }
    }

    private void deleteSelected() {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Tower entry. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete Tower Entry",
                    "Are you sure you want to delete this Tower entry?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) {
                delete(selectedItem.get().getId());
            }
        }
    }

    private void delete(int id) {
        showProgress("Deleting Tower entry...");
        disposables.add(Single.fromCallable(() -> towerController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to delete Tower entry.");
                    refreshList();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting Tower entry.\n" + err);
                }));
    }

    private void setupIcons() {
        btnAdd.setGraphic(new PlusIcon(14));
        btnEdit.setGraphic(new Edit2Icon(14));
        btnDelete.setGraphic(new TrashIcon(14));
        lblCoordinate.setGraphic(new MapPinIcon(14));
    }

    private void showProgress(String text) {
        mainWindow.showProgress(-1, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    @Override
    public void onDispose() {
        disposables.dispose();
    }
}
