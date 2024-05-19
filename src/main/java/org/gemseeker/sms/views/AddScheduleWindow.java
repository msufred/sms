package org.gemseeker.sms.views;

import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Schedule;
import org.gemseeker.sms.data.controllers.ScheduleController;

public class AddScheduleWindow extends AbstractWindow {

    @FXML private DatePicker datePicker;
    @FXML private TextField tfTitle;
    @FXML private TextArea taDescription;
    @FXML private Label lblErrDate;
    @FXML private Label lblErrTitle;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;

    private final ScheduleController scheduleController;
    private final CompositeDisposable disposables;

    public AddScheduleWindow(Database database, Stage owner) {
        super("Schedule", AddScheduleWindow.class.getResource("add_schedule.fxml"), null, owner);
        this.scheduleController = new ScheduleController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        lblErrDate.setGraphic(new XCircleIcon(14));
        lblErrTitle.setGraphic(new XCircleIcon(14));

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
    }

    @Override
    protected void onShow() {
        clearFields();
    }

    private boolean validated() {
        lblErrDate.setVisible(false);
        lblErrTitle.setVisible(false);
        lblErrDate.setVisible(datePicker.getValue() == null);
        lblErrTitle.setVisible(tfTitle.getText().isBlank());
        return datePicker.getValue() != null && !tfTitle.getText().isBlank();
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            Schedule schedule = new Schedule();
            schedule.setTitle(ViewUtils.normalize(tfTitle.getText()));
            schedule.setDescription(ViewUtils.normalize(taDescription.getText()));
            schedule.setDate(datePicker.getValue());
            return scheduleController.insert(schedule);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to add new Schedule entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while adding new Schedule entry.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
    }

    private void clearFields() {
        datePicker.setValue(null);
        tfTitle.clear();
        taDescription.clear();
        lblErrDate.setVisible(false);
        lblErrTitle.setVisible(false);
        progressBar.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
