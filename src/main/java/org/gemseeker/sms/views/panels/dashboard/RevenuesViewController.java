package org.gemseeker.sms.views.panels.dashboard;

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
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Revenue;
import org.gemseeker.sms.data.controllers.RevenueController;
import org.gemseeker.sms.views.*;
import org.gemseeker.sms.views.icons.*;
import org.gemseeker.sms.views.panels.DashboardPanel;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class RevenuesViewController {

    private final Button btnAdd, btnEdit, btnDelete, btnExport;
    private final ComboBox<String> cbModes;
    private final Label lblCash, lblGCash, lblBank, lblCheque, lblPalawan;
    private final TableView<Revenue> tableView;

    private final DashboardPanel dashboardPanel;
    private final MainWindow mainWindow;
    private final Database database;
    private final RevenueController revenueController;
    private final CompositeDisposable disposables;

    private DirectoryChooser directoryChooser;

    // Windows
    private AddRevenueWindow addRevenueWindow;
    private EditRevenueWindow editRevenueWindow;
    private ShowAttachmentWindow showAttachmentWindow;

    private final SimpleObjectProperty<Revenue> selectedItem = new SimpleObjectProperty<>();
    private FilteredList<Revenue> filteredList;

    public RevenuesViewController(DashboardPanel dashboardPanel, MainWindow mainWindow, Database database,
                                  Button btnAdd, Button btnEdit, Button btnDelete, Button btnExport,
                                  ComboBox<String> cbModes, Label lblCash, Label lblGCash, Label lblBank, Label lblCheque,
                                  Label lblPalawan, TableView<Revenue> tableView) {
        this.btnAdd = btnAdd;
        this.btnEdit = btnEdit;
        this.btnDelete = btnDelete;
        this.btnExport = btnExport;
        this.cbModes = cbModes;
        this.lblCash = lblCash;
        this.lblGCash = lblGCash;
        this.lblBank = lblBank;
        this.lblCheque = lblCheque;
        this.lblPalawan = lblPalawan;
        this.tableView = tableView;

        this.dashboardPanel = dashboardPanel;
        this.mainWindow = mainWindow;
        this.database = database;
        this.revenueController = new RevenueController(database);
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

        ObservableList<String> modes = Revenue.modes;
        modes.addFirst("All");
        cbModes.setItems(modes);
        cbModes.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null && filteredList != null) {
                if( newVal.equals("All")) {
                    filteredList.setPredicate(p -> true);
                } else {
                    filteredList.setPredicate(revenue -> revenue.getMode().equals(newVal));
                }
            }
        });
        cbModes.setValue("All");

        lblCash.setGraphic(new PesoIcon(14));
        lblGCash.setGraphic(new PesoIcon(14));
        lblBank.setGraphic(new PesoIcon(14));
        lblCheque.setGraphic(new PesoIcon(14));
        lblPalawan.setGraphic(new PesoIcon(14));

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
        showProgress(-1, "Retrieving revenue entries...");
        disposables.add(Single.fromCallable(revenueController::getAll)
                .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(list -> {
                    hideProgress();
                    filteredList = new FilteredList<>(list);
                    tableView.setItems(filteredList);
                    tally(list);
                    dashboardPanel.refresh(null);
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while retrieving revenue entries.\n" + err);
                }));
    }

    private void tally(ObservableList<Revenue> list) {
        if (list == null) return;
        double cash = 0;
        double gcash = 0;
        double bank = 0;
        double cheque = 0;
        double palawan = 0;
        for (Revenue rev : list) {
            switch (rev.getMode()) {
                case Revenue.MODE_CASH -> cash += rev.getAmount();
                case Revenue.MODE_GCASH -> gcash += rev.getAmount();
                case Revenue.MODE_BANK_CASH -> bank += rev.getAmount();
                case Revenue.MODE_BANK_CHEQUE -> cheque += rev.getAmount();
                default -> palawan += rev.getAmount();
            }
        }
        lblCash.setText(ViewUtils.toStringMoneyFormat(cash));
        lblGCash.setText(ViewUtils.toStringMoneyFormat(gcash));
        lblBank.setText(ViewUtils.toStringMoneyFormat(bank));
        lblCheque.setText(ViewUtils.toStringMoneyFormat(cheque));
        lblPalawan.setText(ViewUtils.toStringMoneyFormat(palawan));
    }

    private void addItem() {
        if (addRevenueWindow == null) addRevenueWindow = new AddRevenueWindow(database, mainWindow.getStage());
        addRevenueWindow.showAndWait();
        refresh();
    }

    private void editItem() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No selected entry. Try again.");
            return;
        }

        if (editRevenueWindow == null) editRevenueWindow = new EditRevenueWindow(database, mainWindow.getStage());
        editRevenueWindow.showAndWait(selectedItem.get());
        refresh();
    }

    private void deleteItem() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No selected entry. Try again.");
            return;
        }

        Optional<ButtonType> result = mainWindow.showConfirmDialog("Delete Entry",
                "Are you sure you want to delete this entry?", ButtonType.YES, ButtonType.NO);
        if (result.isPresent() && result.get() == ButtonType.YES) {
            showProgress(-1, "Deleting entry...");
            disposables.add(Single.fromCallable(() -> revenueController.delete(selectedItem.get().getId()))
                    .observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(success -> {
                        hideProgress();
                        if (!success) mainWindow.showWarningDialog("Action Failed", "Failed to delete entry");
                        refresh();
                    }, err -> {
                        hideProgress();
                        mainWindow.showErrorDialog("Database Error", "Error while deleting entry.\n" + err);
                    }));
        }
    }

    private void exportList() {
        if (filteredList == null) return;
        File dirFolder = getDirectoryChooser().showDialog(mainWindow.getStage());
        if (dirFolder != null) {
            String filename = String.format("revenues_%s.xls", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM-dd-yyyy_hh-mm-ss")));
            File outputFile = new File(dirFolder + Utils.FILE_SEPARATOR + filename);
            showProgress(-1, "Exporting list...");
            disposables.add(Completable.fromAction(() -> {
                ExportUtils.exportRevenues(filteredList, outputFile);
            }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(this::hideProgress, err -> {
                hideProgress();
                mainWindow.showErrorDialog("IOException", "Error while exporting Revenue list to file.\n" + err);
            }));
        }
    }

    private void showAttachment() {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No selected entry. Try again.");
            return;
        }

        if (selectedItem.get().getAttachment().isBlank()) return;

        File file = new File(Utils.REVENUES_IMAGE_FOLDER + Utils.FILE_SEPARATOR + selectedItem.get().getAttachment());
        Image image = new Image(file.toURI().toString());

        if (showAttachmentWindow == null) showAttachmentWindow = new ShowAttachmentWindow(mainWindow);
        showAttachmentWindow.show(image, Utils.REVENUES_IMAGE_FOLDER);
    }

    private void updateTag(String tag) {
        if (selectedItem.get() == null) {
            mainWindow.showWarningDialog("Invalid Action", "No selected entry. Try again.");
            return;
        }

        showProgress(-1, "Updating entry...");
        disposables.add(Single.fromCallable(() -> revenueController.update(selectedItem.get().getId(), "tag", tag))
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
            directoryChooser.setTitle("Set Destination Folder");
        }
        return directoryChooser;
    }

    public void dispose() {
        if (addRevenueWindow != null) addRevenueWindow.dispose();
        if (editRevenueWindow != null) editRevenueWindow.dispose();
        disposables.dispose();
    }
}
