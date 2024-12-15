package org.gemseeker.sms.views.panels.dashboard;

import io.github.msufred.feathericons.*;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import org.gemseeker.sms.ExportUtils;
import org.gemseeker.sms.Utils;
import org.gemseeker.sms.data.CashTransaction;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.CashTransactionController;
import org.gemseeker.sms.views.*;
import org.gemseeker.sms.views.panels.DashboardPanel;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CashTransactionsViewController {

    // Components
    private final Button btnAdd, btnEdit, btnDelete, btnExport;
    private ComboBox<String> cbModes;
    private final TableView<CashTransaction> tableView;

    private final DashboardPanel dashboardPanel;
    private final MainWindow mainWindow;
    private final Database database;
    private final CashTransactionController cashTransactionController;
    private final CompositeDisposable disposables;

    private FilteredList<CashTransaction> filteredList;
    private final SimpleObjectProperty<CashTransaction> selectedItem = new SimpleObjectProperty<>();

    // Windows
    private AddCashTransactionWindow addCashTransactionWindow;
    private EditCashTransactionWindow editCashTransactionWindow;
    private ShowAttachmentWindow showAttachmentWindow;

    private DirectoryChooser directoryChooser;

    public CashTransactionsViewController(DashboardPanel dashboardPanel, MainWindow mainWindow, Database database,
                                          Button btnAdd, Button btnEdit, Button btnDelete, Button btnExport,
                                          ComboBox<String> cbModes, TableView<CashTransaction> tableView) {
        this.btnAdd = btnAdd;
        this.btnEdit = btnEdit;
        this.btnDelete = btnDelete;
        this.btnExport = btnExport;
        this.cbModes = cbModes;
        this.tableView = tableView;

        this.dashboardPanel = dashboardPanel;
        this.mainWindow = mainWindow;
        this.database = database;
        this.cashTransactionController = new CashTransactionController(database);
        this.disposables = new CompositeDisposable();
    }

    public void init() {
        btnAdd.setGraphic(new PlusIcon(14));
        btnAdd.setOnAction(evt -> addItem());

        btnEdit.setGraphic(new Edit2Icon(14));
        btnEdit.setOnAction(evt -> editItem());

        btnDelete.setGraphic(new TrashIcon(14));
        btnDelete.setOnAction(evt -> deleteItem());

        btnExport.setGraphic(new UploadIcon(14));
        btnExport.setOnAction(evt -> exportList());

        ObservableList<String> modes = CashTransaction.modes;
        modes.addFirst("All");
        cbModes.setItems(modes);
        cbModes.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null && filteredList != null) {
                if (newVal.equals("All")) {
                    filteredList.setPredicate(p -> true);
                } else {
                    filteredList.setPredicate(cashTransaction -> cashTransaction.getMode().equals(newVal));
                }
            }
        });
        cbModes.setValue("All");

        // setup context menu
        MenuItem mAdd = new MenuItem("Add");
        mAdd.getStyleClass().add("menu-item-dark");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addItem());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.getStyleClass().add("menu-item-dark");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editItem());

        MenuItem mExport = new MenuItem("Export List");
        mExport.getStyleClass().add("menu-item-dark");
        mExport.setGraphic(new UploadIcon(12));
        mExport.setOnAction(evt -> exportList());

        MenuItem mShowAttachment = new MenuItem("Show Attachment");
        mShowAttachment.getStyleClass().add("menu-item-dark");
        mShowAttachment.setGraphic(new PaperClipIcon(12));
        mShowAttachment.setOnAction(evt -> showAttachment());

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.getStyleClass().add("menu-item-alert");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteItem());

        Menu mTag = new Menu("Change Tag");
        mTag.getStyleClass().add("menu-dark");
        mTag.setGraphic(new CircleIcon(12));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.getStyleClass().add("menu-item-" + tag);
            item.setGraphic(icon);
            item.setOnAction(evt -> updateTag(tag));
            mTag.getItems().add(item);
        });

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mExport, mShowAttachment, mTag, new SeparatorMenuItem(), mDelete);
        tableView.setContextMenu(cm);

        // bind table
        selectedItem.bind(tableView.getSelectionModel().selectedItemProperty());
    }

    public void refresh() {
        showProgress(-1, "Retrieving cash transaction entries...");
        disposables.add(Single.fromCallable(cashTransactionController::getAll)
                .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(list -> {
                    hideProgress();
                    filteredList = new FilteredList<>(list);
                    tableView.setItems(filteredList);
                    dashboardPanel.refreshSummary(null);
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error",
                            "Error while retrieving cash transaction entries.\n" + err);
                }));
    }

    private void addItem() {
        if (addCashTransactionWindow == null) addCashTransactionWindow = new AddCashTransactionWindow(database, mainWindow.getStage());
        addCashTransactionWindow.showAndWait();
        refresh();
    }

    private void editItem() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No selected entry. Try again.");
            return;
        }
        if (editCashTransactionWindow == null) editCashTransactionWindow = new EditCashTransactionWindow(database, mainWindow.getStage());
        editCashTransactionWindow.showAndWait(selectedItem.get());
        refresh();
    }

    private void deleteItem() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No selected entry. Try again.");
            return;
        }
        showProgress(-1, "Deleting entry...");
        disposables.add(Single.fromCallable(() -> cashTransactionController.delete(selectedItem.get().getId()))
                .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(success -> {
                    hideProgress();
                    if (!success) mainWindow.showWarningDialog("Action Failed", "Failed to delete entry.");
                    refresh();
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while deleting entry.\n" + err);
                }));
    }

    private void exportList() {
        if (filteredList == null) return;
        File dirFolder = getDirectoryChooser().showDialog(mainWindow.getStage());
        if (dirFolder != null) {
            String filename = String.format("cash_transactions_%s.xls", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM-dd-yyyy_hh-mm-ss")));
            File outputFile = new File(dirFolder + Utils.FILE_SEPARATOR + filename);
            showProgress(-1, "Exporting list...");
            disposables.add(Completable.fromAction(() -> {
                ExportUtils.exportCashTransactions(filteredList, outputFile);
            }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(this::hideProgress, err -> {
                hideProgress();
                mainWindow.showErrorDialog("IOException", "Error while exporting Cash Transactions list to file.\n" + err);
            }));
        }
    }

    private void showAttachment() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No selected entry. Try again.");
            return;
        }

        if (selectedItem.get().getAttachment().isBlank()) return;

        File file = new File(Utils.CASH_TRANSACTIONS_IMAGE_FOLDER + Utils.FILE_SEPARATOR + selectedItem.get().getAttachment());
        Image image = new Image(file.toURI().toString());

        if (showAttachmentWindow == null) showAttachmentWindow = new ShowAttachmentWindow(mainWindow);
        showAttachmentWindow.show(image, Utils.CASH_TRANSACTIONS_IMAGE_FOLDER);
    }

    private void updateTag(String tag) {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No selected entry. Try again.");
            return;
        }

        showProgress(-1, "Updating entry...");
        disposables.add(Single.fromCallable(() -> cashTransactionController.update(selectedItem.get().getId(), "tag", tag))
                .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(success -> {
                    hideProgress();
                    if (!success) mainWindow.showWarningDialog("Action Failed", "Failed to update selected entry.");
                    refresh();
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while updating entry.\n" + err);
                }));
    }

    private void showProgress(double progress, String text) {
        mainWindow.showProgress(progress, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    private DirectoryChooser getDirectoryChooser() {
        if (directoryChooser == null) {
            directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Destination Folder");
        }
        return directoryChooser;
    }

    public void dispose() {
        if (addCashTransactionWindow != null) addCashTransactionWindow.dispose();
        if (editCashTransactionWindow != null) editCashTransactionWindow.dispose();
        disposables.dispose();
    }
}
