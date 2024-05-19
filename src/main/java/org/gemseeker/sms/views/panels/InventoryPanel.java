package org.gemseeker.sms.views.panels;

import io.github.msufred.feathericons.*;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Product;
import org.gemseeker.sms.data.Service;
import org.gemseeker.sms.data.controllers.ProductController;
import org.gemseeker.sms.data.controllers.ServiceController;
import org.gemseeker.sms.views.*;
import org.gemseeker.sms.views.cells.TagTableCell;

import java.sql.Ref;
import java.util.Optional;

public class InventoryPanel extends AbstractPanel {

    @FXML private TabPane tabPane;
    @FXML private Tab tabProducts;
    @FXML private Tab tabServices;

    // Products Group
    @FXML private Button btnAddProduct;
    @FXML private Button btnEditProduct;
    @FXML private Button btnDeleteProduct;
    @FXML private Button btnRefreshProducts;
    @FXML private Button btnPrintList;
    @FXML private Label lblSearch;
    @FXML private TextField tfSearch;
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, String> colProductTag;
    @FXML private TableColumn<Product, Integer> colProductId;
    @FXML private TableColumn<Product, String> colProductName;
    @FXML private TableColumn<Product, Double> colProductPrice;
    @FXML private TableColumn<Product, Integer> colStocks;

    // Services Group
    @FXML private Button btnAddService;
    @FXML private Button btnEditService;
    @FXML private Button btnDeleteService;
    @FXML private Button btnRefreshServices;
    @FXML private TableView<Service> servicesTable;
    @FXML private TableColumn<Service, String> colServiceTag;
    @FXML private TableColumn<Service, Integer> colServiceId;
    @FXML private TableColumn<Service, String> colServiceName;
    @FXML private TableColumn<Service, Double> colServicePrice;
    @FXML private TableColumn<Service, String> colDescription;

    private FilteredList<Product> productsList;
    private final SimpleObjectProperty<Product> selectedProduct = new SimpleObjectProperty<>();

    private FilteredList<Service> servicesList;
    private final SimpleObjectProperty<Service> selectedService = new SimpleObjectProperty<>();

    private final MainWindow mainWindow;
    private final Database database;
    private final ProductController productController;
    private final ServiceController serviceController;
    private final CompositeDisposable disposables;

    // Windows
    private AddProductWindow addProductWindow;
    private EditProductWindow editProductWindow;
    private AddServiceWindow addServiceWindow;
    private EditServiceWindow editServiceWindow;

    public InventoryPanel(MainWindow mainWindow, Database database) {
        super(InventoryPanel.class.getResource("inventory.fxml"));
        this.mainWindow = mainWindow;
        this.database = database;
        this.productController = new ProductController(database);
        this.serviceController = new ServiceController(database);
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        setupProductsTable();
        setupServicesTable();

        // PRODUCT ACTIONS

        btnAddProduct.setOnAction(evt -> addProduct());
        btnEditProduct.setOnAction(evt -> editSelectedProduct());
        btnDeleteProduct.setOnAction(evt -> deleteSelectedProduct());
        btnRefreshProducts.setOnAction(evt -> refreshProducts());

        // TODO filter

        tfSearch.textProperty().addListener((o, oldVal, newVal) -> {
            if (productsList == null || newVal == null) return;
            if (newVal.isBlank()) productsList.setPredicate(p -> true);
            else productsList.setPredicate(p -> p.getName().toLowerCase().contains(newVal.toLowerCase()));
        });

        // SERVICES ACTIONS
        btnAddService.setOnAction(evt -> addService());
        btnEditService.setOnAction(evt -> editSelectedService());
        btnDeleteService.setOnAction(evt -> deleteSelectedService());
        btnRefreshServices.setOnAction(evt -> refreshServices());

        tabPane.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
            if (newVal.intValue() == 0) refreshProducts();
            else refreshServices();
        });
    }

    @Override
    public void onResume() {
        if (tabPane.getSelectionModel().getSelectedIndex() == 0) {
            refreshProducts();
        } else {
            refreshServices();
        }
    }

    @Override
    public void onPause() {
        // empty for now
    }

    private void refreshProducts() {
        showProgress(-1, "Retrieving Product entries...");
        disposables.add(Single.fromCallable(() -> productController.getAll())
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    productsList = new FilteredList<>(list);
                    // TODO clear filters
                    productsTable.setItems(productsList);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Product entries.\n" + err);
                }));
    }

    private void refreshServices() {
        showProgress(-1, "Retrieving Service entries...");
        disposables.add(Single.fromCallable(() -> serviceController.getAll())
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    servicesList = new FilteredList<>(list);
                    // TODO clear filters
                    servicesTable.setItems(servicesList);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Service entries.\n" + err);
                }));
    }

    private void addProduct() {
        if (addProductWindow == null) addProductWindow = new AddProductWindow(database, mainWindow.getStage());
        addProductWindow.showAndWait();
        refreshProducts();
    }

    private void addService() {
        if (addServiceWindow == null) addServiceWindow = new AddServiceWindow(database, mainWindow.getStage());
        addServiceWindow.showAndWait();
        refreshServices();
    }

    private void editSelectedProduct() {
        if (selectedProduct.get() == null) {
            showWarningDialog("Invalid", "No selected Product. Try again.");
        } else {
            if (editProductWindow == null) editProductWindow = new EditProductWindow(database, mainWindow.getStage());
            editProductWindow.showAndWait(selectedProduct.get());
            refreshProducts();
        }
    }

    private void editSelectedService() {
        if (selectedService.get() == null) {
            showWarningDialog("Invalid", "No selected Service. Try again.");
        } else {
            if (editServiceWindow == null) editServiceWindow = new EditServiceWindow(database, mainWindow.getStage());
            editServiceWindow.showAndWait(selectedService.get());
            refreshServices();
        }
    }

    private void deleteSelectedProduct() {
        if (selectedProduct.get() == null) {
            showWarningDialog("Invalid", "No selected Product. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete Product",
                    "Are you sure you want to delete this Product entry?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) deleteProduct(selectedProduct.get().getId());
        }
    }

    private void deleteProduct(int id) {
        showProgress(-1, "Deleting Product entry...");
        disposables.add(Single.fromCallable(() -> productController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to delete Product entry.");
                    refreshProducts();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting Product entry.\n" + err);
                }));
    }

    private void deleteSelectedService() {
        if (selectedService.get() == null) {
            showWarningDialog("Invalid", "No selected Service. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete Service",
                    "Are you sure you want to delete this Service entry?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) deleteService(selectedService.get().getId());
        }
    }

    private void deleteService(int id) {
        showProgress(-1, "Deleting Service entry...");
        disposables.add(Single.fromCallable(() -> serviceController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to delete Service entry.");
                    refreshServices();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting Service entry.\n" + err);
                }));
    }

    private void updateSelectedProductTag(String tag) {
        if (selectedProduct.get() == null) {
            showWarningDialog("Invalid", "No selected Product. Try again.");
        } else {
            showProgress(-1, "Updating Product entry tag....");
            disposables.add(Single.fromCallable(() -> productController.update(selectedProduct.get().getId(), "tag", tag))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                        hideProgress();
                        if (!success) showWarningDialog("Failed", "Failed to update Product entry.");
                        refreshProducts();
                    }, err -> {
                        hideProgress();
                        showErrorDialog("Database Error", "Error while updating Product entry.\n" + err);
                    }));
        }
    }

    private void updateSelectedServiceTag(String tag) {
        if (selectedService.get() == null) {
            showWarningDialog("Invalid", "No selected Service. Try again.");
        } else {
            showProgress(-1, "Updating Service entry tag....");
            disposables.add(Single.fromCallable(() -> serviceController.update(selectedService.get().getId(), "tag", tag))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                        hideProgress();
                        if (!success) showWarningDialog("Failed", "Failed to update Service entry.");
                        refreshServices();
                    }, err -> {
                        hideProgress();
                        showErrorDialog("Database Error", "Error while updating Service entry.\n" + err);
                    }));
        }
    }

    private void showProgress(double progress, String text) {
        mainWindow.showProgress(progress, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    private void setupIcons() {
        // Products group
        tabProducts.setGraphic(new ShoppingCartIcon(12));
        btnAddProduct.setGraphic(new PlusIcon(14));
        btnEditProduct.setGraphic(new Edit2Icon(14));
        btnDeleteProduct.setGraphic(new TrashIcon(14));
        btnRefreshProducts.setGraphic(new RefreshCwIcon(14));
        btnPrintList.setGraphic(new PrinterIcon(14));
        lblSearch.setGraphic(new SearchIcon(14));

        // Service Group
        tabServices.setGraphic(new ToolIcon(12));
        btnAddService.setGraphic(new PlusIcon(14));
        btnEditService.setGraphic(new Edit2Icon(14));
        btnDeleteService.setGraphic(new TrashIcon(14));
        btnRefreshServices.setGraphic(new RefreshCwIcon(14));
    }

    private void setupProductsTable() {
        colProductTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colProductTag.setCellFactory(col -> new TagTableCell<>());
        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStocks.setCellValueFactory(new PropertyValueFactory<>("stock"));

        MenuItem mAdd = new MenuItem("New Product");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addProduct());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(14));
        mEdit.setOnAction(evt -> editSelectedProduct());

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteSelectedProduct());

        Menu mChangeTag = new Menu("Change Tag");
        mChangeTag.setGraphic(new CircleIcon(12));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.setGraphic(icon);
            item.setOnAction(evt -> updateSelectedProductTag(tag));
            mChangeTag.getItems().add(item);
        });

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mDelete, mChangeTag);
        productsTable.setContextMenu(cm);
        selectedProduct.bind(productsTable.getSelectionModel().selectedItemProperty());
    }

    private void setupServicesTable() {
        colServiceTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colServiceTag.setCellFactory(col -> new TagTableCell<>());
        colServiceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colServiceName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colServicePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        MenuItem mAdd = new MenuItem("New Service");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addService());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(14));
        mEdit.setOnAction(evt -> editSelectedService());

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteSelectedService());

        Menu mChangeTag = new Menu("Change Tag");
        mChangeTag.setGraphic(new CircleIcon(12));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.setGraphic(icon);
            item.setOnAction(evt -> updateSelectedServiceTag(tag));
            mChangeTag.getItems().add(item);
        });

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mDelete, mChangeTag);
        servicesTable.setContextMenu(cm);
        selectedService.bind(servicesTable.getSelectionModel().selectedItemProperty());
    }

    @Override
    public void onDispose() {
        if (addProductWindow != null) addProductWindow.dispose();
        if (editProductWindow != null) editProductWindow.dispose();
        if (addServiceWindow != null) addServiceWindow.dispose();
        if (editServiceWindow != null) editServiceWindow.dispose();
        disposables.dispose();
    }
}
