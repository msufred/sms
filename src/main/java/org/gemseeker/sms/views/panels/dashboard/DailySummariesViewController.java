package org.gemseeker.sms.views.panels.dashboard;

import io.github.msufred.feathericons.ArrowUpIcon;
import io.github.msufred.feathericons.SettingsIcon;
import io.github.msufred.feathericons.UploadIcon;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import org.gemseeker.sms.ExportUtils;
import org.gemseeker.sms.Utils;
import org.gemseeker.sms.data.DailySummary;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.DailySummaryController;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.RecalculateWindow;
import org.gemseeker.sms.views.panels.DashboardPanel;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class DailySummariesViewController {

    private Button btnExport, btnRecalculate;
    private TableView<DailySummary> tableView;

    private final DashboardPanel dashboardPanel;
    private final MainWindow mainWindow;
    private final Database database;
    private final DailySummaryController dailySummaryController;
    private final CompositeDisposable disposables;

    private final SimpleObjectProperty<DailySummary> selectedItem = new SimpleObjectProperty<>();
    private FilteredList<DailySummary> filteredList;

    private DirectoryChooser directoryChooser;

    // Windows
    private RecalculateWindow recalculateWindow;

    public DailySummariesViewController(DashboardPanel dashboardPanel, MainWindow mainWindow, Database database,
                                        Button btnExport, Button btnRecalculate, TableView<DailySummary> tableView) {
        this.btnExport = btnExport;
        this.btnRecalculate = btnRecalculate;
        this.tableView = tableView;

        this.dashboardPanel = dashboardPanel;
        this.mainWindow = mainWindow;
        this.database = database;
        this.dailySummaryController = new DailySummaryController(database);
        this.disposables = new CompositeDisposable();
    }

    public void init() {
        btnExport.setGraphic(new UploadIcon(14));
        btnExport.setOnAction(evt -> exportList());

        btnRecalculate.setGraphic(new SettingsIcon(14));
        btnRecalculate.setOnAction(evt -> recalculate());

        MenuItem mRecalculate = new MenuItem("Recalculate");
        mRecalculate.setGraphic(new SettingsIcon(12));
        mRecalculate.getStyleClass().add("menu-item-dark");
        mRecalculate.setOnAction(evt -> recalculate());

        MenuItem mExport = new MenuItem("Export");
        mExport.setGraphic(new UploadIcon(12));
        mExport.getStyleClass().add("menu-item-dark");
        mExport.setOnAction(evt -> exportList());

        ContextMenu cm = new ContextMenu(mRecalculate, mExport);
        tableView.setContextMenu(cm);

        selectedItem.bind(tableView.getSelectionModel().selectedItemProperty());
    }

    public void refresh() {
        showProgress(-1, "Retrieving daily summary entries...");
        disposables.add(Single.fromCallable(dailySummaryController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    FXCollections.sort(list, Comparator.comparing(DailySummary::getDate));
                    filteredList = new FilteredList<>(list);
                    tableView.setItems(filteredList);
                    //dashboardPanel.refreshSummary(null);
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while retrieving daily summary entries.\n" + err);
                }));
    }

    private void recalculate() {
        if (recalculateWindow == null) recalculateWindow = new RecalculateWindow(database, mainWindow.getStage());
        recalculateWindow.showAndWait();
        dashboardPanel.onResume();
        refresh();
    }

    private void exportList() {
        if (filteredList == null) return;
        File dirFolder = getDirectoryChooser().showDialog(mainWindow.getStage());
        if (dirFolder != null) {
            String filename = String.format("summaries_%s.xls", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM-dd-yyyy_hh-mm-ss")));
            File outputFile = new File(dirFolder.getPath() + Utils.FILE_SEPARATOR + filename);
            showProgress(-1, "Exporting Summaries list...");
            disposables.add(Completable.fromAction(() -> {
                ExportUtils.exportSummaries(filteredList, outputFile);
            }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(this::hideProgress, err -> {
                hideProgress();
                mainWindow.showErrorDialog("IOException", "Error while exporting Summary list to file.\n" + err);
            }));
        }
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
        if (recalculateWindow != null) recalculateWindow.dispose();
        disposables.dispose();
    }
}
