package org.gemseeker.sms.views.panels.products_services;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Product;
import org.gemseeker.sms.data.controllers.ProductController;
import org.gemseeker.sms.views.AddProductWindow;
import org.gemseeker.sms.views.EditProductWindow;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.ViewUtils;
import org.gemseeker.sms.views.icons.*;
import org.gemseeker.sms.views.panels.InventoryPanel;

import java.util.Optional;

public class ItemStocksViewController {

    private final Button btnAdd, btnEdit, btnDelete, btnRefresh, btnExport;
    private final Label lblSearch;
    private final TextField tfSearch;
    private final TableView<Product> tableView;

    private final MainWindow mainWindow;
    private final InventoryPanel inventoryPanel;
    private final Database database;
    private final ProductController productController;
    private final CompositeDisposable disposables;

    private final SimpleObjectProperty<Product> selectedItem = new SimpleObjectProperty<>();
    private FilteredList<Product> filteredList;

    // Windows
    private AddProductWindow addProductWindow;
    private EditProductWindow editProductWindow;

    public ItemStocksViewController(MainWindow mainWindow, InventoryPanel inventoryPanel, Database database, Button btnAdd, Button btnEdit,
                                    Button btnDelete, Button btnRefresh, Button btnExport, Label lblSearch, TextField tfSearch,
                                    TableView<Product> tableView) {
        this.btnAdd = btnAdd;
        this.btnEdit = btnEdit;
        this.btnDelete = btnDelete;
        this.btnRefresh = btnRefresh;
        this.btnExport = btnExport;
        this.lblSearch = lblSearch;
        this.tfSearch = tfSearch;
        this.tableView = tableView;

        this.mainWindow = mainWindow;
        this.inventoryPanel = inventoryPanel;
        this.database = database;
        this.productController = new ProductController(database);
        this.disposables = new CompositeDisposable();
    }

    public void init() {
        // Setup Icons
        btnAdd.setGraphic(new PlusIcon(14));
        btnEdit.setGraphic(new Edit2Icon(14));
        btnDelete.setGraphic(new TrashIcon(14));
        btnRefresh.setGraphic(new RefreshCwIcon(14));
        btnExport.setGraphic(new PrinterIcon(14));
        lblSearch.setGraphic(new SearchIcon(14));

        // Buttons
        btnAdd.setOnAction(evt -> addItem());
        btnEdit.setOnAction(evt -> editItem());
        btnDelete.setOnAction(evt -> deleteItem());
        btnRefresh.setOnAction(evt -> refresh());
        btnExport.setOnAction(evt -> export());

        tfSearch.textProperty().addListener((o, oldVal, newVal) -> {
            if (filteredList == null) return;
            if (newVal == null || newVal.isBlank()) filteredList.setPredicate(p -> true);
            else filteredList.setPredicate(p -> p.getName().toLowerCase().contains(newVal) ||
                    p.getSerial().toLowerCase().contains(newVal));
        });

        // Context Menu
        MenuItem mAdd = new MenuItem("New Product");
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
        showProgress("Fetching products data...");
        disposables.add(Single.fromCallable(productController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    filteredList = new FilteredList<>(list);
                    tableView.setItems(filteredList);
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while retrieving products data.\n" + err);
                }));
    }

    private void addItem() {
        if (addProductWindow == null) addProductWindow = new AddProductWindow(database, mainWindow.getStage());
        addProductWindow.showAndWait();
        refresh();
    }

    private void editItem() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No product selected. Try again.");
            return;
        }

        if (editProductWindow == null) editProductWindow = new EditProductWindow(database, mainWindow.getStage());
        editProductWindow.showAndWait(selectedItem.get());
        refresh();
    }

    private void deleteItem() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid", "No selected Product. Try again.");
            return;
        }

        Optional<ButtonType> result = mainWindow.showConfirmDialog("Delete Product",
                "Are you sure you want to delete this Product entry?",
                ButtonType.YES, ButtonType.NO);
        if (result.isPresent() && result.get() == ButtonType.YES) deleteProduct(selectedItem.get().getId());
    }

    private void deleteProduct(int id) {
        showProgress("Deleting product...");
        disposables.add(Single.fromCallable(() -> productController.delete(id))
                .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(success -> {
                    hideProgress();
                    if (!success) mainWindow.showWarningDialog("Failed", "Failed to delete product.");
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while deleting product.\n" + err);
                }));
    }

    private void export() {

    }

    private void updateTag(String tag) {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid", "No selected Product. Try again.");
            return;
        }

        showProgress("Updating Product entry tag....");
        disposables.add(Single.fromCallable(() -> productController.update(selectedItem.get().getId(), "tag", tag))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) mainWindow.showWarningDialog("Failed", "Failed to update Product entry.");
                    refresh();
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while updating Product entry.\n" + err);
                }));
    }

    public void dispose() {
        if (addProductWindow != null) addProductWindow.dispose();
        if (editProductWindow != null) editProductWindow.dispose();
        disposables.dispose();
    }

    private void showProgress(String text) {
        mainWindow.showProgress(-1, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }
}
