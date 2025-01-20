package org.gemseeker.sms.views;

import io.github.msufred.feathericons.PlusIcon;
import io.github.msufred.feathericons.UserIcon;
import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.*;
import org.gemseeker.sms.views.cells.AmountTableCell;
import org.gemseeker.sms.views.panels.products_services.PurchasesViewController;

public class AddPurchasePaymentWindow extends AbstractWindow {

    @FXML private TextField tfOrNo;
    @FXML private DatePicker datePicker;
    @FXML private TextField tfPayee;
    @FXML private TextField tfAddress;
    @FXML private TextField tfContact;
    @FXML private Button btnList;
    @FXML private ComboBox<Product> cbProducts;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private Button btnAddItem;
    @FXML private TableView<PaymentItem> itemsTable;
    @FXML private TableColumn<PaymentItem, String> colName;
    @FXML private TableColumn<PaymentItem, Double> colPrice;
    @FXML private TableColumn<PaymentItem, Integer> colQuantity;
    @FXML private TableColumn<PaymentItem, Double> colAmount;
    @FXML private Label lblTotal;
    @FXML private ComboBox<String> cbModes;
    @FXML private TextField tfReference;
    @FXML private TextField tfAmount;
    @FXML private TextField tfDiscount;
    @FXML private Label lblBalance;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    // For Error
    @FXML private Label lblErrNo;
    @FXML private Label lblErrDate;
    @FXML private Label lblErrName;
    @FXML private Label lblErrAddress;
    @FXML private Label lblErrContact;
    @FXML private Label lblErrMode;
    @FXML private Label lblErrItems;
    @FXML private Label lblErrAmount;

    private final PurchasesViewController purchasesViewController;
    private final ProductController productController;
    private final PaymentController paymentController;
    private final PaymentItemController paymentItemController;
    private final RevenueController revenueController;
    private final CompositeDisposable disposables;

    private final ObservableList<PaymentItem> items = FXCollections.observableArrayList();

    private double total = 0;
    private double amount = 0;
    private double discount = 0;
    private double balance = 0;

    private Payment payment;

    public AddPurchasePaymentWindow(PurchasesViewController purchasesViewController, Database database, Stage owner) {
        super("Add Service Payment", AddPurchasePaymentWindow.class.getResource("add_purchase_payment.fxml"), null, owner);
        this.purchasesViewController = purchasesViewController;
        productController = new ProductController(database);
        paymentController = new PaymentController(database);
        paymentItemController = new PaymentItemController(database);
        revenueController = new RevenueController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        // Setup Icons
        btnList.setGraphic(new UserIcon(14));
        btnAddItem.setGraphic(new PlusIcon(14));

        lblErrNo.setGraphic(new XCircleIcon(14));
        lblErrNo.getStyleClass().add("label-error");
        lblErrDate.setGraphic(new XCircleIcon(14));
        lblErrDate.getStyleClass().add("label-error");
        lblErrName.setGraphic(new XCircleIcon(14));
        lblErrName.getStyleClass().add("label-error");
        lblErrAddress.setGraphic(new XCircleIcon(14));
        lblErrAddress.getStyleClass().add("label-error");
        lblErrContact.setGraphic(new XCircleIcon(14));
        lblErrContact.getStyleClass().add("label-error");
        lblErrMode.setGraphic(new XCircleIcon(14));
        lblErrMode.getStyleClass().add("label-error");
        lblErrItems.setGraphic(new XCircleIcon(14));
        lblErrItems.getStyleClass().add("label-error");
        lblErrAmount.setGraphic(new XCircleIcon(14));
        lblErrAmount.getStyleClass().add("label-error");
        hideErrorLabels();

        ViewUtils.setAsIntegerTextField(tfContact);
        ViewUtils.setAsNumericalTextField(tfAmount, tfDiscount);

        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000));

        btnAddItem.setOnAction(evt -> {
            if (cbProducts.getValue() == null) {
                showWarningDialog("Invalid Action", "Please select Product and set the quantity.");
            } else {
                addItem(cbProducts.getValue(), quantitySpinner.getValue());
            }
        });

        itemsTable.setItems(items);
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setCellFactory(col -> new AmountTableCell<>());
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAmount.setCellFactory(col -> new AmountTableCell<>());

        cbModes.setItems(Revenue.modes);

        tfAmount.textProperty().addListener((o, oldVal, newVal) -> recalculate());
        tfDiscount.textProperty().addListener((o, oldVal, newVal) -> recalculate());

        btnSave.setOnAction(evt -> {
            if (validated()) {
                saveAndClose();
            }
        });

        btnCancel.setOnAction(evt -> close());
    }

    @Override
    protected void onShow() {
        showProgress(true);
        disposables.add(Single.fromCallable(productController::getAll)
                .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(services -> {
                    showProgress(false);
                    cbProducts.setItems(services);
                }, err -> {
                    showProgress(false);
                    showErrorDialog("Database Error", "Error while retrieving products data.\n" + err);
                }));
    }

    private void addItem(Product product, int qty) {
        if (product == null) return;
        if (items.size() == 4) return;

        PaymentItem item = new PaymentItem();
        item.setItemName(product.getName());
        item.setItemNo(product.getId() + "");
        item.setSerial(product.getSerial());
        item.setQuantity(qty);
        item.setPrice(product.getPrice());
        item.setAmount(product.getPrice() * qty);

        items.add(item);
        recalculate();
    }

    private void recalculate() {
        total = 0;
        for (PaymentItem item : items) total += item.getAmount();

        amount = tfAmount.getText().isBlank() ? 0 : Double.parseDouble(tfAmount.getText().trim());
        discount = tfDiscount.getText().isBlank() ? 0 : Double.parseDouble(tfDiscount.getText().trim());

        double subtotal = total - discount;
        balance = subtotal - amount;

        lblTotal.setText(ViewUtils.toStringMoneyFormat(total));
        lblBalance.setText(ViewUtils.toStringMoneyFormat(balance));
    }

    private boolean validated() {
        hideErrorLabels();
        lblErrNo.setVisible(tfOrNo.getText().isBlank());
        lblErrDate.setVisible(datePicker.getValue() == null);
        lblErrName.setVisible(tfPayee.getText().isBlank());
        lblErrAddress.setVisible(tfAddress.getText().isBlank());
        lblErrContact.setVisible(tfContact.getText().isBlank());
        lblErrMode.setVisible(cbModes.getValue() == null);
        lblErrItems.setVisible(items.isEmpty());
        lblErrAmount.setVisible(tfAmount.getText().isBlank() || tfAmount.getText().equals("0.00") ||
                tfAmount.getText().equals("0"));

        return !tfOrNo.getText().isBlank() && !tfPayee.getText().isBlank() && !tfAddress.getText().isBlank() &&
                !tfContact.getText().isBlank() && !items.isEmpty() && !tfAmount.getText().isBlank() &&
                cbModes.getValue() != null && datePicker.getValue() != null;
    }

    private void saveAndClose() {
        payment = fetchPaymentInfo();
        doSave(this::close);
    }

    private void doSave(Runnable onNext) {
        showProgress(true);
        disposables.add(Single.fromCallable(() -> paymentController.insert(payment))
                .flatMap(success -> Single.fromCallable(() -> {
                    for (PaymentItem item : items) {
                        item.setPaymentNo(payment.getPaymentNo());
                        paymentItemController.insert(item);

                        Product product = null;
                        for (Product p : cbProducts.getItems()) {
                            if (p.getName().equals(item.getItemName())) {
                                product = p;
                                break;
                            }
                        }

                        if (product != null) {
                            int newQty = product.getStock() - item.getQuantity();
                            productController.update(product.getId(), "stock", newQty + "");
                        }
                    }
                    return success;
                })).flatMap(success -> Single.fromCallable(() -> {
                    if (success) {
                        Revenue revenue = new Revenue();
                        revenue.setType(Revenue.TYPE_PURCHASE);
                        revenue.setAmount(amount);
                        revenue.setDescription("Purchase Payment (OR NO " + payment.getPaymentNo() + ")");
                        revenue.setDate(datePicker.getValue());
                        revenue.setMode(cbModes.getValue());
                        revenue.setReference(ViewUtils.normalize(tfReference.getText()));
                        revenueController.insert(revenue);
                    }
                    return success;
                })).observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(success -> {
                    showProgress(false);
                    if (!success) showWarningDialog("Failed", "Failed to save purchase payment.");
                    if (onNext != null) onNext.run();
                }, err -> {
                    showProgress(false);
                    showErrorDialog("Database Error", "Error while saving purchase payment.\n" + err);
                }));
    }

    private Payment fetchPaymentInfo() {
        Payment payment = new Payment();
        payment.setPaymentNo(ViewUtils.normalize(tfOrNo.getText()));
        payment.setName(ViewUtils.normalize(tfPayee.getText()));
        payment.setAddress(ViewUtils.normalize(tfAddress.getText()));
        payment.setContact(ViewUtils.normalize(tfContact.getText()));
        payment.setMode(cbModes.getValue());
        payment.setRef(ViewUtils.normalize(tfReference.getText()));
        payment.setPaymentFor(Payment.TYPE_PURCHASE);
        payment.setAmountToPay(total);
        payment.setDiscount(discount);
        payment.setAmountPaid(amount);
        payment.setAmountTotal(total - discount);
        payment.setBalance(balance);
        payment.setPaymentDate(datePicker.getValue());
        return payment;
    }

    @Override
    protected void onClose() {
        clearFields();
        total = 0;
        amount = 0;
        discount = 0;
        balance = 0;
    }

    private void clearFields() {
        tfOrNo.clear();
        datePicker.setValue(null);
        tfPayee.clear();
        tfAddress.clear();
        tfContact.clear();
        items.clear();
        cbModes.setValue(null);
        tfReference.clear();
        quantitySpinner.getValueFactory().setValue(1);
        lblTotal.setText("0.00");
        tfAmount.setText("0.00");
        tfDiscount.setText("0.00");
        lblBalance.setText("0.00");
        hideErrorLabels();
    }

    private void hideErrorLabels() {
        lblErrNo.setVisible(false);
        lblErrDate.setVisible(false);
        lblErrName.setVisible(false);
        lblErrAddress.setVisible(false);
        lblErrContact.setVisible(false);
        lblErrMode.setVisible(false);
        lblErrItems.setVisible(false);
        lblErrAmount.setVisible(false);
    }

    private void showProgress(boolean show) {
        progressBar.setVisible(show);
    }

    public void dispose() {
        disposables.dispose();
    }
}
