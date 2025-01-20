package org.gemseeker.sms.views.panels.products_services;

import io.github.msufred.feathericons.*;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import org.apache.commons.math3.analysis.function.Sin;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Service;
import org.gemseeker.sms.data.controllers.ServiceController;
import org.gemseeker.sms.views.AddServiceWindow;
import org.gemseeker.sms.views.EditServiceWindow;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.ViewUtils;
import org.gemseeker.sms.views.panels.InventoryPanel;

import java.util.Optional;

public class ServicesViewController {

    private final Button btnAdd, btnEdit, btnDelete, btnRefresh;
    private final TableView<Service> tableView;
    
    private final MainWindow mainWindow;
    private final InventoryPanel inventoryPanel;
    private final Database database;
    private final ServiceController serviceController;
    private final CompositeDisposable disposables;
    
    private final SimpleObjectProperty<Service> selectedItem = new SimpleObjectProperty<>();
    private FilteredList<Service> filteredList;
    
    // Windows
    private AddServiceWindow addServiceWindow;
    private EditServiceWindow editServiceWindow;
    
    public ServicesViewController(MainWindow mainWindow, InventoryPanel inventoryPanel, Database database,
                                  Button btnAdd, Button btnEdit, Button btnDelete, Button btnRefresh, TableView<Service> tableView) {
        this.btnAdd = btnAdd;
        this.btnEdit = btnEdit;
        this.btnDelete = btnDelete;
        this.btnRefresh = btnRefresh;
        this.tableView = tableView;
        
        this.mainWindow = mainWindow;
        this.inventoryPanel = inventoryPanel;
        this.database = database;
        this.serviceController = new ServiceController(database);
        this.disposables = new CompositeDisposable();
    }
    
    public void init() {
        // Setup Icons
        btnAdd.setGraphic(new PlusIcon(14));
        btnEdit.setGraphic(new Edit2Icon(14));
        btnDelete.setGraphic(new TrashIcon(14));
        btnRefresh.setGraphic(new RefreshCwIcon(14));
        
        btnAdd.setOnAction(evt -> addItem());
        btnEdit.setOnAction(evt -> editItem());
        btnDelete.setOnAction(evt -> deleteItem());
        btnRefresh.setOnAction(evt -> refresh());
        
        // Context Menus
        MenuItem mAdd = new MenuItem("New Service");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addItem());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(14));
        mEdit.setOnAction(evt -> editItem());

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteItem());

        Menu mChangeTag = new Menu("Change Tag");
        mChangeTag.setGraphic(new CircleIcon(12));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.setGraphic(icon);
            item.setOnAction(evt -> updateTag(tag));
            mChangeTag.getItems().add(item);
        });

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mDelete, mChangeTag);
        tableView.setContextMenu(cm);
        selectedItem.bind(tableView.getSelectionModel().selectedItemProperty());
    }
    
    public void refresh() {
        showProgress("Fetching services data...");
        disposables.add(Single.fromCallable(serviceController::getAll)
                .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(list -> {
                    hideProgress();
                    filteredList = new FilteredList<>(list);
                    tableView.setItems(filteredList);
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while retrieving services data.\n" + err);
                }));
    }
    
    private void addItem() {
        if (addServiceWindow == null) addServiceWindow = new AddServiceWindow(database, mainWindow.getStage());
        addServiceWindow.showAndWait();
        refresh();
    }
    
    private void editItem() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No selected service. Try again.");
            return;
        }
        
        if (editServiceWindow == null) editServiceWindow = new EditServiceWindow(database, mainWindow.getStage());
        editServiceWindow.showAndWait(selectedItem.get());
        refresh();
    }
    
    private void deleteItem() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No selected service. Try again.");
            return;
        }

        Optional<ButtonType> result = mainWindow.showConfirmDialog("Delete Service",
                "Are you sure you want to delete this entry?", ButtonType.YES, ButtonType.NO);
        if (result.isPresent() && result.get() == ButtonType.YES) {
            deleteService(selectedItem.get().getId());
        }
    }
    
    private void deleteService(int id) {
        showProgress("Deleting service entry...");
        disposables.add(Single.fromCallable(() -> serviceController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) mainWindow.showWarningDialog("Failed", "Failed to delete service entry.");
                    return;
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while deleting service entry.\n" + err);
                }));
    }
    
    private void updateTag(String tag) {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No selected service. Try again.");
            return;
        }

        showProgress("Updating service entry...");
        disposables.add(Single.fromCallable(() -> serviceController.update(selectedItem.get().getId(), "tag", tag))
                .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(success -> {
                    hideProgress();
                    if (!success) mainWindow.showWarningDialog("Failed", "Failed to update service entry.");
                    refresh();
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while updating service entry.\n" + err);
                }));
    }
    
    public void dispose() {
        if (addServiceWindow != null) addServiceWindow.dispose();
        if (editServiceWindow != null) editServiceWindow.dispose();
        disposables.dispose();
    }
    
    private void showProgress(String text) {
        mainWindow.showProgress(-1, text);
    }
    
    private void hideProgress() {
        mainWindow.hideProgress();
    }
}
