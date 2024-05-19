package org.gemseeker.sms.views.panels;

import io.github.msufred.feathericons.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.WifiVendo;
import org.gemseeker.sms.data.controllers.WifiVendoController;
import org.gemseeker.sms.views.AddWifiVendoWindow;
import org.gemseeker.sms.views.EditWifiVendoWindow;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.ViewUtils;
import org.gemseeker.sms.views.cells.DateTimeTableCell;
import org.gemseeker.sms.views.cells.StatusTableCell;
import org.gemseeker.sms.views.cells.TagTableCell;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class WiFiHotspotsPanel extends AbstractPanel {

    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnRefresh;
    @FXML private Label lblStatus;
    @FXML private ComboBox<String> cbStatus;
    @FXML private Label lblSearch;
    @FXML private TextField tfSearch;
    @FXML private TableView<WifiVendo> wifiTable;
    @FXML private TableColumn<WifiVendo, String> colTag;
    @FXML private TableColumn<WifiVendo, String> colName;
    @FXML private TableColumn<WifiVendo, String> colIpAddress;
    @FXML private TableColumn<WifiVendo, String> colStatus;
    @FXML private TableColumn<WifiVendo, LocalDateTime> colCreatedAt;
    @FXML private TableColumn<WifiVendo, LocalDateTime> colUpdatedAt;

    private FilteredList<WifiVendo> filteredList;
    private final SimpleObjectProperty<WifiVendo> selectedItem = new SimpleObjectProperty<>();

    private final MainWindow mainWindow;
    private final Database database;
    private final WifiVendoController wifiVendoController;
    private final CompositeDisposable disposables;

    // Windows
    private AddWifiVendoWindow addWifiVendoWindow;
    private EditWifiVendoWindow editWifiVendoWindow;

    public WiFiHotspotsPanel(MainWindow mainWindow, Database database) {
        super(WiFiHotspotsPanel.class.getResource("wifi_hotspots.fxml"));
        this.mainWindow = mainWindow;
        this.database = database;
        this.wifiVendoController = new WifiVendoController(database);
        this.disposables = new CompositeDisposable();
    }
    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        setupTable();

        btnAdd.setOnAction(evt -> addItem());
        btnEdit.setOnAction(evt -> editSelected());
        btnDelete.setOnAction(evt -> deleteSelected());
        btnRefresh.setOnAction(evt -> refresh());

        cbStatus.setItems(FXCollections.observableArrayList(
                "All", "Active", "Inactive", "Maintenance", "Out Of Order"
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
            else filteredList.setPredicate(vendo -> vendo.getName().toLowerCase().contains(newVal.toLowerCase()));
        });
    }

    @Override
    public void onResume() {
        refresh();
    }

    @Override
    public void onPause() {
        // empty for now
    }

    private void refresh() {
        showProgress(-1, "Retrieving WiFi Vendo entries...");
        disposables.add(Single.fromCallable(() -> wifiVendoController.getAll())
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    filteredList = new FilteredList<>(list);
                    // TODO clear filters
                    wifiTable.setItems(filteredList);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving WiFi Vendo entries.\n" + err);
                }));
    }

    private void addItem() {
        if (addWifiVendoWindow == null) addWifiVendoWindow = new AddWifiVendoWindow(database, mainWindow.getStage());
        addWifiVendoWindow.showAndWait();
        refresh();
    }

    private void editSelected() {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid", "No selected WiFi Vendo. Try again.");
        } else {
            if (editWifiVendoWindow == null) editWifiVendoWindow = new EditWifiVendoWindow(database, mainWindow.getStage());
            editWifiVendoWindow.showAndWait(selectedItem.get());
            refresh();
        }
    }

    private void deleteSelected() {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid", "No selected WiFi Vendo. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete WiFi Vendo",
                    "Are you sure you want to delete this WiFi Vendo?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) {
                delete(selectedItem.get().getId());
            }
        }
    }

    private void delete(int id) {
        showProgress(-1, "Deleting WiFi Vendo...");
        disposables.add(Single.fromCallable(() -> wifiVendoController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    refresh();
                    if (!success) showWarningDialog("Failed", "Failed to delete WiFi Vendo entry.");
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting WiFi Vendo.\n" + err);
                }));
    }

    private void changeSelectedStatus(String newStatus) {
        if (selectedItem.get() == null || newStatus == null) return;
        showProgress(-1, "Changing WiFi Vendo status...");
        disposables.add(Single.fromCallable(() -> wifiVendoController.update(selectedItem.get().getId(), "status", newStatus))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    refresh();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while updating WiFi Vendo status.\n" + err);
                }));
    }

    private void changeSelectedTag(String tag) {
        if (selectedItem.get() == null || tag == null) return;
        showProgress(-1, "Changing Account tag...");
        disposables.add(Single.fromCallable(() -> wifiVendoController.update(selectedItem.get().getId(), "tag", tag))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    refresh();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while updating WiFi Vendo tag.\n" + err);
                }));
    }

    private void showProgress(double progress, String text) {
        mainWindow.showProgress(progress, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    private void setupIcons() {
        btnAdd.setGraphic(new PlusIcon(14));
        btnEdit.setGraphic(new Edit2Icon(14));
        btnDelete.setGraphic(new TrashIcon(14));
        btnRefresh.setGraphic(new RefreshCwIcon(14));
        lblStatus.setGraphic(new FilterIcon(14));
        lblSearch.setGraphic(new SearchIcon(14));
    }

    private void setupTable() {
        colTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colTag.setCellFactory(col -> new TagTableCell<>());
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colIpAddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellFactory(col -> new StatusTableCell<>());
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        colCreatedAt.setCellFactory(col -> new DateTimeTableCell<>());
        colUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("dateUpdated"));
        colUpdatedAt.setCellFactory(col -> new DateTimeTableCell<>());

        MenuItem mAdd = new MenuItem("New WiFi Vendo");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addItem());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editSelected());

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteSelected());

        MenuItem mStatActive = new MenuItem("Active");
        mStatActive.setGraphic(new SmileIcon(12));
        mStatActive.setOnAction(evt -> changeSelectedStatus("Active"));

        MenuItem mStatInactive = new MenuItem("Inactive");
        mStatInactive.setGraphic(new FrownIcon(12));
        mStatInactive.setOnAction(evt -> changeSelectedStatus("Inactive"));

        MenuItem mStatMaintenance = new MenuItem("Maintenance");
        mStatMaintenance.setGraphic(new ToolIcon(12));
        mStatMaintenance.setOnAction(evt -> changeSelectedStatus("Maintenance"));

        MenuItem mStatOutOfOrder = new MenuItem("Out Of Order");
        mStatOutOfOrder.setGraphic(new AlertOctagonIcon(12));
        mStatOutOfOrder.setOnAction(evt -> changeSelectedStatus("Out Of Order"));

        Menu mChangeStatus = new Menu("Change Status");
        mChangeStatus.setGraphic(new SmileIcon(12));
        mChangeStatus.getItems().addAll(mStatActive, mStatInactive, mStatMaintenance, mStatOutOfOrder);

        Menu mChangeTag = new Menu("Change Tag");
        mChangeTag.setGraphic(new CircleIcon(12));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.setGraphic(icon);
            item.setOnAction(evt -> changeSelectedTag(tag));
            mChangeTag.getItems().add(item);
        });

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mDelete, mChangeStatus, mChangeTag);
        wifiTable.setContextMenu(cm);

        selectedItem.bind(wifiTable.getSelectionModel().selectedItemProperty());
    }

    @Override
    public void onDispose() {
        if (addWifiVendoWindow != null) addWifiVendoWindow.dispose();
        if (editWifiVendoWindow != null) editWifiVendoWindow.dispose();
        disposables.dispose();
    }
}
