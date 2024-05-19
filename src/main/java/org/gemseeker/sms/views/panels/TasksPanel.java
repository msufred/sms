package org.gemseeker.sms.views.panels;

import io.github.msufred.feathericons.*;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Schedule;
import org.gemseeker.sms.data.controllers.ScheduleController;
import org.gemseeker.sms.views.AddScheduleWindow;
import org.gemseeker.sms.views.EditScheduleWindow;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.ViewUtils;
import org.gemseeker.sms.views.cells.DateTableCell;
import org.gemseeker.sms.views.cells.StatusTableCell;
import org.gemseeker.sms.views.cells.TagTableCell;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

public class TasksPanel extends AbstractPanel {

    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Label lblStatus;
    @FXML private ComboBox<String> cbStatus;
    @FXML private Label lblMonth;
    @FXML private ComboBox<String> cbMonths;
    @FXML private TableView<Schedule> schedulesTable;
    @FXML private TableColumn<Schedule, String> colTag;
    @FXML private TableColumn<Schedule, String> colStatus;
    @FXML private TableColumn<Schedule, LocalDate> colDate;
    @FXML private TableColumn<Schedule, String> colTitle;
    @FXML private TableColumn<Schedule, String> colDescription;

    private FilteredList<Schedule> filteredList;
    private final SimpleObjectProperty<Schedule> selectedItem = new SimpleObjectProperty<>();

    private final MainWindow mainWindow;
    private final Database database;
    private final ScheduleController scheduleController;
    private final CompositeDisposable disposables;

    private AddScheduleWindow addScheduleWindow;
    private EditScheduleWindow editScheduleWindow;

    public TasksPanel(MainWindow mainWindow, Database database) {
        super(TasksPanel.class.getResource("tasks.fxml"));
        this.mainWindow = mainWindow;
        this.database = database;
        this.scheduleController = new ScheduleController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        setupTable();

        cbStatus.setItems(FXCollections.observableArrayList(
                "All",
                Schedule.STATUS_PENDING.toUpperCase(),
                Schedule.STATUS_OUTDATED.toUpperCase(),
                Schedule.STATUS_DONE.toUpperCase()
        ));
        cbStatus.setValue("All");
        cbStatus.valueProperty().addListener((o, oldVal, newVal) -> updateFilters());

        cbMonths.setItems(FXCollections.observableArrayList(
                "All",
                Month.JANUARY.toString(),
                Month.FEBRUARY.toString(),
                Month.MARCH.toString(),
                Month.APRIL.toString(),
                Month.MAY.toString(),
                Month.JUNE.toString(),
                Month.JULY.toString(),
                Month.AUGUST.toString(),
                Month.SEPTEMBER.toString(),
                Month.OCTOBER.toString(),
                Month.NOVEMBER.toString(),
                Month.DECEMBER.toString()
        ));
        cbMonths.setValue("All");
        cbMonths.valueProperty().addListener((o, oldVal, newVal) -> updateFilters());

        btnAdd.setOnAction(evt -> addItem());
        btnEdit.setOnAction(evt -> editSelected());
        btnDelete.setOnAction(evt -> deleteSelected());
    }

    @Override
    public void onResume() {
        refresh();
    }

    // NOTE! refresh will autoupdate all Schedule entries
    private void refresh() {
        showProgress("Retrieving Schedule entries...");
        disposables.add(Single.fromCallable(scheduleController::getAll)
                .map(list -> {
                    // still in background thread
                    LocalDate now = LocalDate.now();
                    for (Schedule schedule : list) {
                        if (schedule.getDate().isBefore(now)) {
                            schedule.setStatus(Schedule.STATUS_OUTDATED);
                            scheduleController.update(schedule.getId(), "status", Schedule.STATUS_OUTDATED);
                        }
                    }
                    return list;
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    filteredList = new FilteredList<>(list);
                    schedulesTable.setItems(filteredList);

                    int outdated = 0;
                    int pending = 0;
                    for (Schedule s : list) {
                        if (s.getStatus().equals("pending")) pending += 1;
                        if (s.getStatus().equals("outdated")) outdated += 1;
                    }
                    mainWindow.setOutdateCount(outdated);
                    mainWindow.setUpcomingCount(pending);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Schedule entries.\n" + err);
                }));
    }

    private void addItem() {
        if (addScheduleWindow == null) addScheduleWindow = new AddScheduleWindow(database, mainWindow.getStage());
        addScheduleWindow.showAndWait();
        refresh();
    }

    private void editSelected() {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Schedule entry. Try again.");
        } else {
            if (editScheduleWindow == null) editScheduleWindow = new EditScheduleWindow(database, mainWindow.getStage());
            editScheduleWindow.showAndWait(selectedItem.get());
            refresh();
        }
    }

    private void deleteSelected() {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Schedule entry. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete Schedule",
                    "Are you sure you want to delete this Schedule entry?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) {
                delete(selectedItem.get().getId());
            }
        }
    }

    private void delete(int id) {
        showProgress("Deleting Schedule entry...");
        disposables.add(Single.fromCallable(() -> scheduleController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to delete Schedule entry.");
                    refresh();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting Schedule entry.\n" + err);
                }));
    }

    private void updateSelectedStatus(String status) {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Schedule entry. Try again.");
        } else {
            showProgress("Updating Schedule entry...");
            disposables.add(Single.fromCallable(() -> scheduleController.update(selectedItem.get().getId(), "status", status))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                        hideProgress();
                        if (!success) showWarningDialog("Failed", "Failed to update Schedule entry.");
                        refresh();
                    }, err -> {
                        hideProgress();
                        showErrorDialog("Database Error", "Error while updating Schedule entry.\n" + err);
                    }));
        }
    }

    private void updateSelectedTag(String tag) {
        if (selectedItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Schedule entry. Try again.");
        } else {
            showProgress("Updating Schedule entry...");
            disposables.add(Single.fromCallable(() -> scheduleController.update(selectedItem.get().getId(), "tag", tag))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                        hideProgress();
                        if (!success) showWarningDialog("Failed", "Failed to update Schedule entry.");
                        refresh();
                    }, err -> {
                        hideProgress();
                        showErrorDialog("Database Error", "Error while updating Schedule entry.\n" + err);
                    }));
        }
    }

    private void updateFilters() {
        if (cbStatus.getValue().equals("All") && cbMonths.getValue().equals("All")) {
            filteredList.setPredicate(p -> true);
        } else if (cbStatus.getValue().equals("All")) {
            filteredList.setPredicate(schedule -> schedule.getDate().getMonth() == Month.valueOf(cbMonths.getValue()));
        } else if (cbMonths.getValue().equals("All")) {
            filteredList.setPredicate(schedule -> schedule.getStatus().equals(cbStatus.getValue().toUpperCase()));
        } else {
            filteredList.setPredicate(schedule -> schedule.getDate().getMonth() == Month.valueOf(cbMonths.getValue()) &&
                    schedule.getStatus().equals(cbStatus.getValue().toUpperCase()));
        }
    }

    @Override
    public void onPause() {

    }

    private void setupIcons() {
        btnAdd.setGraphic(new PlusIcon(14));
        btnEdit.setGraphic(new Edit2Icon(14));
        btnDelete.setGraphic(new TrashIcon(14));
        lblStatus.setGraphic(new SmileIcon(14));
        lblMonth.setGraphic(new CalendarIcon(14));
    }

    private void setupTable() {
        colTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colTag.setCellFactory(col -> new TagTableCell<>());
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellFactory(col -> new StatusTableCell<>());
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDate.setCellFactory(col -> new DateTableCell<>());
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        MenuItem mAdd = new MenuItem("Add");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addItem());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editSelected());

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteSelected());

        Menu mStatus = new Menu("Change Status");
        mStatus.setGraphic(new ClockIcon(12));

        MenuItem mPending = new MenuItem("Pending");
        mPending.setGraphic(new ClockIcon(12));
        mPending.setOnAction(evt -> updateSelectedStatus(Schedule.STATUS_PENDING));

        MenuItem mOutdated = new MenuItem("Outdated");
        mOutdated.setGraphic(new AlertCircleIcon(12));
        mOutdated.setOnAction(evt -> updateSelectedStatus(Schedule.STATUS_OUTDATED));

        MenuItem mDone = new MenuItem("Done");
        mDone.setGraphic(new CheckIcon(12));
        mDone.setOnAction(evt -> updateSelectedStatus(Schedule.STATUS_DONE));

        mStatus.getItems().addAll(mPending, mOutdated, mDone);

        Menu mTag = new Menu("Change Tag");
        mTag.setGraphic(new CircleIcon(12));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.setGraphic(icon);
            item.setOnAction(evt -> updateSelectedTag(tag));
            mTag.getItems().add(item);
        });

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mDelete, mStatus, mTag);
        schedulesTable.setContextMenu(cm);
        selectedItem.bind(schedulesTable.getSelectionModel().selectedItemProperty());
    }

    private void showProgress(String text) {
        mainWindow.showProgress(-1, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    @Override
    public void onDispose() {
        if (addScheduleWindow != null) addScheduleWindow.dispose();
        disposables.dispose();
    }
}
