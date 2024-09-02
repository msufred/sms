package org.gemseeker.sms.views.panels;

import io.github.msufred.feathericons.*;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.gemseeker.sms.Utils;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Tower;
import org.gemseeker.sms.data.controllers.TowerController;
import org.gemseeker.sms.views.AddTowerWindow;
import org.gemseeker.sms.views.EditTowerWindow;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.cells.TowerTypeTableCell;
import org.gemseeker.sms.views.panels.maps.LinePainter;
import org.gemseeker.sms.views.panels.maps.TowerPainter;
import org.gemseeker.sms.views.panels.maps.TowerPoint;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.*;

import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MapsPanel extends AbstractPanel {

    // comment no function

    private static final int ZOOM_MAX = 11;
    private static final int ZOOM_MIN = 0;

    private static final double CENTER_LAT = 6.34137;
    private static final double CENTER_LON = 124.72314;
    private static final GeoPosition CENTER_POS = new GeoPosition(CENTER_LAT, CENTER_LON);

    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private StackPane stackPane;

    @FXML private TableView<Tower> tableView;
    @FXML private TableColumn<Tower, String> colType;
    @FXML private TableColumn<Tower, String> colName;
    @FXML private TableColumn<Tower, String> colParent;

    // Map controls
    @FXML private Button btnCenter;
    @FXML private Button btnZoomIn;
    @FXML private Button btnZoomOut;
    @FXML private TextField tfLatitude;
    @FXML private TextField tfLongitude;
    @FXML private Label lblMapView;
    @FXML private RadioButton rbDefault;
    @FXML private RadioButton rbVirtualEarth;
    @FXML private RadioButton rbSatellite;
    @FXML private RadioButton rbHybrid;

    // private MapView mapView;
    private JXMapViewer mapViewer;
    private int mZoomLevel = 7;
    // TileFactories
    private DefaultTileFactory osmTileFactory;
    private DefaultTileFactory veMapTileFactory; // Virtual Earth Map
    private DefaultTileFactory veSatelliteTileFactory; // Virtual Earth Satellite
    private DefaultTileFactory veHybridTileFactory; // Virtual Earth Hybrid (Satellite & Map)

    private FilteredList<Tower> filteredList;
    private final SimpleObjectProperty<Tower> selectedItem = new SimpleObjectProperty<>();

    private final MainWindow mainWindow;
    private final Database database;
    private final TowerController towerController;
    private final CompositeDisposable disposables;

    private AddTowerWindow addTowerWindow;
    private EditTowerWindow editTowerWindow;

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
        setupTable();
        setupMapView();

        btnAdd.setOnAction(evt -> addItem());
        btnEdit.setOnAction(evt -> editSelected());
        btnDelete.setOnAction(evt -> deleteSelected());

        btnCenter.setOnAction(evt -> {
            mZoomLevel = 7;
            mapViewer.setCenterPosition(CENTER_POS);
            mapViewer.setZoom(mZoomLevel);
        });

        btnZoomIn.setOnAction(evt -> {
            mZoomLevel -= 1;
            if (mZoomLevel < ZOOM_MIN) mZoomLevel = ZOOM_MIN;
            mapViewer.setZoom(mZoomLevel);
        });

        btnZoomOut.setOnAction(evt -> {
            mZoomLevel += 1;
            if (mZoomLevel > ZOOM_MAX) mZoomLevel = ZOOM_MAX;
            mapViewer.setZoom(mZoomLevel);
        });

        rbDefault.selectedProperty().addListener((o, ov, selected) -> {
            if (selected && mapViewer != null) {
                GeoPosition pos = mapViewer.getCenterPosition();
                mapViewer.setTileFactory(getOsmTileFactory());
                mapViewer.setZoom(mZoomLevel);
                mapViewer.setCenterPosition(pos);
            }
        });

        rbVirtualEarth.selectedProperty().addListener((o, ov, selected) -> {
            if (selected && mapViewer != null) {
                GeoPosition pos = mapViewer.getCenterPosition();
                mapViewer.setTileFactory(getVeMapTileFactory());
                mapViewer.setZoom(mZoomLevel);
                mapViewer.setCenterPosition(pos);
            }
        });

        rbSatellite.selectedProperty().addListener((o, ov, selected) -> {
            if (selected && mapViewer != null) {
                GeoPosition pos = mapViewer.getCenterPosition();
                mapViewer.setTileFactory(getVeSatelliteTileFactory());
                mapViewer.setZoom(mZoomLevel);
                mapViewer.setCenterPosition(pos);
            }
        });

        rbHybrid.selectedProperty().addListener((o, ov, selected) -> {
            if (selected && mapViewer != null) {
                mapViewer.setTileFactory(getVeHybridTileFactory());
                mapViewer.setZoom(mZoomLevel);
            }
        });
    }

    private void setupMapView() {
        mapViewer = new JXMapViewer();
        mapViewer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // create TileFactoryInfo for OpenStreetMap
        mapViewer.setTileFactory(getOsmTileFactory());

        // center position
        mapViewer.setZoom(mZoomLevel);
        mapViewer.setCenterPosition(CENTER_POS);

        // add interactions
        PanMouseInputListener panMouseInputListener = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(panMouseInputListener);
        mapViewer.addMouseMotionListener(panMouseInputListener);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));
        mapViewer.addPropertyChangeListener("zoom", evt -> {
            mZoomLevel = mapViewer.getZoom();
        });
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Platform.runLater(() -> {
                    GeoPosition pos = mapViewer.convertPointToGeoPosition(e.getPoint());
                    if (pos != null) {
                        tfLatitude.setText(pos.getLatitude() + "");
                        tfLongitude.setText(pos.getLongitude() + "");
                    }
                });
            }
        });

        SwingNode swingNode = new SwingNode();
        swingNode.setOnMouseEntered(evt -> swingNode.setCursor(javafx.scene.Cursor.HAND));
        swingNode.setOnMouseDragged(evt -> swingNode.setCursor(javafx.scene.Cursor.CLOSED_HAND));
        swingNode.setOnMouseReleased(evt -> swingNode.setCursor(javafx.scene.Cursor.HAND));
        SwingUtilities.invokeLater(() -> {
            swingNode.setContent(mapViewer);
        });
        stackPane.getChildren().add(swingNode);
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
                    tableView.setItems(filteredList);
                    refreshMap(list);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Tower entries.\n" + err);
                }));
    }

    private void refreshMap(ObservableList<Tower> list) {
        mZoomLevel = 7;
        mapViewer.setCenterPosition(CENTER_POS);
        mapViewer.setZoom(mZoomLevel);
        redraw(list);
    }

    private void redraw(ObservableList<Tower> list) {
        if (list.isEmpty()) return;
        ObservableList<TowerPoint> towers = getTowerPoints(list);

        LinePainter linePainter = new LinePainter(towers);
        TowerPainter towerPainter = new TowerPainter(list);

        List<Painter<JXMapViewer>> painters = new ArrayList<>();
        painters.add(linePainter);
        painters.add(towerPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(painter);
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

    private void showMapPosition() {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Tower entry. Try again.");
        } else {
            if (mapViewer != null) {
                Tower tower = selectedItem.get();
                mapViewer.setCenterPosition(new GeoPosition(tower.getLatitude(), tower.getLongitude()));
                mZoomLevel = 5;
                mapViewer.setZoom(mZoomLevel);
            }
        }
    }

    private void setupIcons() {
        btnAdd.setGraphic(new PlusIcon(14));
        btnEdit.setGraphic(new Edit2Icon(14));
        btnDelete.setGraphic(new TrashIcon(14));

        btnCenter.setGraphic(new MapPinIcon(14));
        btnZoomIn.setGraphic(new ZoomInIcon(14));
        btnZoomOut.setGraphic(new ZoomOutIcon(14));

        lblMapView.setGraphic(new MapIcon(14));
    }

    private void setupTable() {
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colType.setCellFactory(col -> new TowerTypeTableCell());
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colParent.setCellValueFactory(new PropertyValueFactory<>("parentName"));
        colParent.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String name, boolean empty) {
                if (empty || name == null) {
                    setText("");
                    setGraphic(null);
                } else if (name.equals("null")) {
                    setText("N/A");
                } else {
                    setText(name);
                }
            }
        });

        MenuItem mAdd = new MenuItem("Add Tower");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addItem());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editSelected());

        MenuItem mShowPos = new MenuItem("Show Map Position");
        mShowPos.setGraphic(new MapPinIcon(12));
        mShowPos.setOnAction(evt -> showMapPosition());

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteSelected());

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mShowPos, new SeparatorMenuItem(), mDelete);
        tableView.setContextMenu(cm);
        selectedItem.bind(tableView.getSelectionModel().selectedItemProperty());
    }

    private DefaultTileFactory getOsmTileFactory() {
        if (osmTileFactory == null) {
            osmTileFactory = new DefaultTileFactory(new OSMTileFactoryInfo());
            osmTileFactory.setThreadPoolSize(8);
            // setup local cache
            File cacheDir = new File(Utils.TEMP_FOLDER);
            osmTileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));
        }
        return osmTileFactory;
    }

    private DefaultTileFactory getVeMapTileFactory() {
        if (veMapTileFactory == null) {
            veMapTileFactory = new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP));
            veMapTileFactory.setThreadPoolSize(8);
            veMapTileFactory.setLocalCache(new FileBasedLocalCache(new File(Utils.TEMP_FOLDER), false));
        }
        return veMapTileFactory;
    }

    private DefaultTileFactory getVeSatelliteTileFactory() {
        if (veSatelliteTileFactory == null) {
            veSatelliteTileFactory = new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE));
            veSatelliteTileFactory.setThreadPoolSize(8);
            veSatelliteTileFactory.setLocalCache(new FileBasedLocalCache(new File(Utils.TEMP_FOLDER), false));
        }
        return veSatelliteTileFactory;
    }

    private DefaultTileFactory getVeHybridTileFactory() {
        if (veHybridTileFactory == null) {
            veHybridTileFactory = new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID));
            veHybridTileFactory.setThreadPoolSize(8);
            veHybridTileFactory.setLocalCache(new FileBasedLocalCache(new File(Utils.TEMP_FOLDER), false));
        }
        return veHybridTileFactory;
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
