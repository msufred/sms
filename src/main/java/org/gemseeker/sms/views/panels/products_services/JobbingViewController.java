package org.gemseeker.sms.views.panels.products_services;

import io.github.msufred.feathericons.*;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Payment;
import org.gemseeker.sms.data.Revenue;
import org.gemseeker.sms.data.controllers.PaymentController;
import org.gemseeker.sms.views.AddServicePaymentWindow;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.PrintWindow;
import org.gemseeker.sms.views.ViewUtils;
import org.gemseeker.sms.views.panels.InventoryPanel;

import java.util.Optional;

public class JobbingViewController {

    private final Button btnAdd, btnEdit, btnDelete, btnRefresh, btnPrint;
    private final ComboBox<String> cbModes;
    private final TableView<Payment> tableView;

    private final MainWindow mainWindow;
    private final InventoryPanel inventoryPanel;
    private final Database database;
    private final PaymentController paymentController;
    private final CompositeDisposable disposables;

    private final SimpleObjectProperty<Payment> selectedItem = new SimpleObjectProperty<>();
    private FilteredList<Payment> filteredList;

    private AddServicePaymentWindow addServicePaymentWindow;
    private PrintWindow printWindow;

    public JobbingViewController(MainWindow mainWindow, InventoryPanel inventoryPanel, Database database,
                                 Button btnAdd, Button btnEdit, Button btnDelete, Button btnRefresh, Button btnPrint,
                                 ComboBox<String> cbModes, TableView<Payment> tableView) {
        this.btnAdd = btnAdd;
        this.btnEdit = btnEdit;
        this.btnDelete = btnDelete;
        this.btnRefresh = btnRefresh;
        this.btnPrint = btnPrint;
        this.cbModes = cbModes;
        this.tableView = tableView;

        this.mainWindow = mainWindow;
        this.inventoryPanel = inventoryPanel;
        this.database = database;
        this.paymentController = new PaymentController(database);
        this.disposables = new CompositeDisposable();
    }

    public void init() {
        btnAdd.setGraphic(new PlusIcon(14));
        btnEdit.setGraphic(new Edit2Icon(14));
        btnDelete.setGraphic(new TrashIcon(14));
        btnRefresh.setGraphic(new RefreshCwIcon(14));
        btnPrint.setGraphic(new PrinterIcon(14));

        btnAdd.setOnAction(evt -> addItem());
        btnEdit.setOnAction(evt -> editItem());
        btnDelete.setOnAction(evt -> deleteItem());
        btnRefresh.setOnAction(evt -> refresh());
        btnPrint.setOnAction(evt -> {
            if (selectedItem.get() == null) {
                mainWindow.showWarningDialog("Invalid Action", "No selected payment entry. Try again.");
                return;
            }
            print(selectedItem.get().getPaymentNo());
        });

        ObservableList<String> modes = FXCollections.observableArrayList(
                "All", Revenue.MODE_CASH, Revenue.MODE_GCASH, Revenue.MODE_BANK_CASH, Revenue.MODE_BANK_CHEQUE, Revenue.MODE_PALAWAN_EXP
        );
        cbModes.setItems(modes);
        cbModes.valueProperty().addListener((o, oldVal, newVal) -> {
            if (filteredList == null) return;
            if (newVal == null || newVal.equals("All")) filteredList.setPredicate(p -> true);
            else filteredList.setPredicate(p -> p.getMode().equals(newVal));
        });

        // ContextMenu
        MenuItem miAdd = new MenuItem("Add");
        miAdd.setGraphic(new PlusIcon(12));
        miAdd.setOnAction(evt -> addItem());

        MenuItem miEdit = new MenuItem("Edit");
        miEdit.setGraphic(new Edit2Icon(12));
        miEdit.setOnAction(evt -> editItem());

        MenuItem miDelete = new MenuItem("Delete");
        miDelete.getStyleClass().add("menu-item-alert");
        miDelete.setGraphic(new TrashIcon(12));
        miDelete.setOnAction(evt -> deleteItem());

        MenuItem miPrint = new MenuItem("Print Receipt");
        miPrint.setGraphic(new PrinterIcon(12));
        miPrint.setOnAction(evt -> {
            if (selectedItem.get() == null) {
                mainWindow.showWarningDialog("Invalid Action", "No selected payment entry. Try again.");
                return;
            }
            print(selectedItem.get().getPaymentNo());
        });

        Menu mChangeTag = new Menu("Change Tag");
        mChangeTag.setGraphic(new CircleIcon(12));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.setGraphic(icon);
            item.setOnAction(evt -> updateTag(tag));
            mChangeTag.getItems().add(item);
        });

        ContextMenu cm = new ContextMenu(miAdd, miEdit, miPrint, mChangeTag, new SeparatorMenuItem(), miDelete);
        tableView.setContextMenu(cm);
        selectedItem.bind(tableView.getSelectionModel().selectedItemProperty());
    }

    public void refresh() {
        showProgress("Retrieving service payments...");
        disposables.add(Single.fromCallable(() -> paymentController.getPayments(Payment.TYPE_SERVICE))
                .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(list -> {
                    hideProgress();
                    filteredList = new FilteredList<>(list);
                    tableView.setItems(filteredList);
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while retrieving service payments.\n" + err);
                }));
    }

    private void addItem() {
        if (addServicePaymentWindow == null) addServicePaymentWindow = new AddServicePaymentWindow(this, database, mainWindow.getStage());
        addServicePaymentWindow.showAndWait();
        refresh();
    }

    private void editItem() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No item selected. Try again.");
            return;
        }

    }

    private void deleteItem() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No item selected. Try again.");
            return;
        }
        Optional<ButtonType> result = mainWindow.showConfirmDialog("Delete Payment",
                "Are you sure you want to delete this entry?", ButtonType.YES, ButtonType.NO);
        if (result.isPresent() && result.get() == ButtonType.YES) deleteJob(selectedItem.get().getId());
    }

    private void deleteJob(int id) {
        showProgress("Deleting entry...");
        disposables.add(Single.fromCallable(() -> paymentController.delete(id))
                .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(success -> {
                    hideProgress();
                    if (!success) mainWindow.showWarningDialog("Failed", "Failed to delete payment entry.");
                    refresh();
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while deleting payment entry.\n" + err);
                }));
    }

    private void updateTag(String tag) {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No payment entry selected. Try again.");
            return;
        }
        showProgress("Updating entry...");
        disposables.add(Single.fromCallable(() -> paymentController.update(selectedItem.get().getId(), "tag", tag))
                .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(success -> {
                    hideProgress();
                    if (!success) mainWindow.showWarningDialog("Failed", "Failed to update payment entry.");
                    refresh();
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while updating payment entry.\n" + err);
                }));
    }

    private void print(String paymentNo) {
        if (printWindow == null) printWindow = new PrintWindow(mainWindow.getUser(), database, mainWindow.getStage());
        printWindow.showAndWait(paymentNo);
    }

    private void showProgress(String text) {
        mainWindow.showProgress(-1, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    public void dispose() {
        if (addServicePaymentWindow != null) addServicePaymentWindow.dispose();
        if (printWindow != null) printWindow.dispose();
        disposables.dispose();
    }
}
