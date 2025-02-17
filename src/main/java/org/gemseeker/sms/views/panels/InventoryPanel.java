package org.gemseeker.sms.views.panels;

import io.reactivex.disposables.CompositeDisposable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Payment;
import org.gemseeker.sms.data.Product;
import org.gemseeker.sms.data.Service;
import org.gemseeker.sms.views.*;
import org.gemseeker.sms.views.cells.AmountTableCell;
import org.gemseeker.sms.views.cells.DateTableCell;
import org.gemseeker.sms.views.cells.TagTableCell;
import org.gemseeker.sms.views.icons.PesoIcon;
import org.gemseeker.sms.views.icons.ShoppingCartIcon;
import org.gemseeker.sms.views.icons.ToolIcon;
import org.gemseeker.sms.views.panels.products_services.ItemStocksViewController;
import org.gemseeker.sms.views.panels.products_services.JobbingViewController;
import org.gemseeker.sms.views.panels.products_services.PurchasesViewController;
import org.gemseeker.sms.views.panels.products_services.ServicesViewController;

import java.time.LocalDate;

public class InventoryPanel extends AbstractPanel {

    @FXML private TabPane tabPane;
    @FXML private Tab tabPurchases;
    @FXML private Tab tabJobbings;
    @FXML private Tab tabProducts;
    @FXML private Tab tabServices;

    // Purchases Group
    @FXML private Button btnAddPurchase;
    @FXML private Button btnEditPurchase;
    @FXML private Button btnDeletePurchase;
    @FXML private Button btnRefreshPurchases;
    @FXML private Button btnPrintPurchase;
    @FXML private ComboBox<String> cbPurchasePaymentModes;
    @FXML private TableView<Payment> purchasesTable;
    @FXML private TableColumn<Payment, String> colPurchaseTag;
    @FXML private TableColumn<Payment, String> colPurchaseNo;
    @FXML private TableColumn<Payment, LocalDate> colPurchaseDate;
    @FXML private TableColumn<Payment, String> colPurchasePayee;
    @FXML private TableColumn<Payment, Double> colPurchaseTotal;
    @FXML private TableColumn<Payment, Double> colPurchasePaid;
    @FXML private TableColumn<Payment, Double> colPurchaseBalance;
    @FXML private TableColumn<Payment, String> colPurchaseMode;
    @FXML private TableColumn<Payment, String> colPurchaseRef;

    // Jobbing Group
    @FXML private Button btnAddJob;
    @FXML private Button btnEditJob;
    @FXML private Button btnDeleteJob;
    @FXML private Button btnRefreshJobs;
    @FXML private Button btnPrintJobs;
    @FXML private ComboBox<String> cbJobPaymentModes;
    @FXML private TableView<Payment> jobbingTable;
    @FXML private TableColumn<Payment, String> colJobTag;
    @FXML private TableColumn<Payment, String> colJobNo;
    @FXML private TableColumn<Payment, LocalDate> colJobDate;
    @FXML private TableColumn<Payment, String> colJobPayee;
    @FXML private TableColumn<Payment, Double> colJobTotal;
    @FXML private TableColumn<Payment, Double> colJobPaid;
    @FXML private TableColumn<Payment, Double> colJobBalance;
    @FXML private TableColumn<Payment, String> colJobMode;
    @FXML private TableColumn<Payment, String> colJobRef;

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


    private final MainWindow mainWindow;
    private final Database database;
    private final CompositeDisposable disposables;

    // Tabs
    private PurchasesViewController purchasesViewController;
    private JobbingViewController jobbingViewController;
    private ItemStocksViewController itemStocksViewController;
    private ServicesViewController servicesViewController;

    public InventoryPanel(MainWindow mainWindow, Database database) {
        super(InventoryPanel.class.getResource("inventory.fxml"));
        this.mainWindow = mainWindow;
        this.database = database;
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        setupPurchasesTab();
        setupJobbingsTab();
        setupProductsTab();
        setupServicesTab();

        tabPane.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
            onTabSelected(newVal.intValue());
        });
    }

    @Override
    public void onResume() {
        onTabSelected(tabPane.getSelectionModel().getSelectedIndex());
    }

    @Override
    public void onPause() {
        // empty for now
    }

    private void onTabSelected(int index) {
        switch (index) {
            case 1 -> {
                if (jobbingViewController != null) jobbingViewController.refresh();
            }
            case 2 -> {
                if (itemStocksViewController != null) itemStocksViewController.refresh();
            }
            case 3 -> {
                if (servicesViewController != null) servicesViewController.refresh();
            }
            default -> {
                if (purchasesViewController != null) purchasesViewController.refresh();
            }
        }
    }

    private void setupIcons() {
        tabPurchases.setGraphic(new PesoIcon(12));
        tabJobbings.setGraphic(new PesoIcon(12));
        tabProducts.setGraphic(new ShoppingCartIcon(12));
        tabServices.setGraphic(new ToolIcon(12));
    }

    private void setupPurchasesTab() {
        colPurchaseTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colPurchaseTag.setCellFactory(col -> new TagTableCell<>());
        colPurchaseNo.setCellValueFactory(new PropertyValueFactory<>("paymentNo"));
        colPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colPurchaseDate.setCellFactory(col -> new DateTableCell<>());
        colPurchasePayee.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPurchaseTotal.setCellValueFactory(new PropertyValueFactory<>("amountTotal"));
        colPurchaseTotal.setCellFactory(col -> new AmountTableCell<>());
        colPurchasePaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        colPurchasePaid.setCellFactory(col -> new AmountTableCell<>());
        colPurchaseBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colPurchaseBalance.setCellFactory(col -> new AmountTableCell<>());
        colPurchaseMode.setCellValueFactory(new PropertyValueFactory<>("mode"));
        colPurchaseRef.setCellValueFactory(new PropertyValueFactory<>("ref"));

        purchasesViewController = new PurchasesViewController(mainWindow, this, database,
                btnAddPurchase, btnEditPurchase, btnDeletePurchase, btnRefreshPurchases, btnPrintPurchase, cbPurchasePaymentModes,
                purchasesTable);
        purchasesViewController.init();
    }

    private void setupJobbingsTab() {
        colJobTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colJobTag.setCellFactory(col -> new TagTableCell<>());
        colJobNo.setCellValueFactory(new PropertyValueFactory<>("paymentNo"));
        colJobDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colJobDate.setCellFactory(col -> new DateTableCell<>());
        colJobPayee.setCellValueFactory(new PropertyValueFactory<>("name"));
        colJobTotal.setCellValueFactory(new PropertyValueFactory<>("amountTotal"));
        colJobTotal.setCellFactory(col -> new AmountTableCell<>());
        colJobPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        colJobPaid.setCellFactory(col -> new AmountTableCell<>());
        colJobBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colJobBalance.setCellFactory(col -> new AmountTableCell<>());
        colJobMode.setCellValueFactory(new PropertyValueFactory<>("mode"));
        colJobRef.setCellValueFactory(new PropertyValueFactory<>("ref"));

        jobbingViewController = new JobbingViewController(mainWindow, this, database,
                btnAddJob, btnEditJob, btnDeleteJob, btnRefreshJobs, btnPrintJobs, cbJobPaymentModes, jobbingTable);
        jobbingViewController.init();
    }

    private void setupProductsTab() {
        colProductTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colProductTag.setCellFactory(col -> new TagTableCell<>());
        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStocks.setCellValueFactory(new PropertyValueFactory<>("stock"));

        itemStocksViewController = new ItemStocksViewController(mainWindow, this, database,
                btnAddProduct, btnEditProduct, btnDeleteProduct, btnRefreshProducts, btnPrintList, lblSearch,
                tfSearch, productsTable);
        itemStocksViewController.init();
    }

    private void setupServicesTab() {
        colServiceTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colServiceTag.setCellFactory(col -> new TagTableCell<>());
        colServiceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colServiceName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colServicePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        servicesViewController = new ServicesViewController(mainWindow, this, database,
                btnAddService, btnEditService, btnDeleteService, btnRefreshServices, servicesTable);
        servicesViewController.init();
    }

    @Override
    public void onDispose() {
        if (jobbingViewController != null) jobbingViewController.dispose();
        if (itemStocksViewController != null) itemStocksViewController.dispose();
        if (servicesViewController != null) servicesViewController.dispose();
        disposables.dispose();
    }
}
