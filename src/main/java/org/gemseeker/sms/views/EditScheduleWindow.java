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

public class EditScheduleWindow extends AbstractWindow {

    @FXML private Label lblTitle;
    @FXML private DatePicker datePicker;
    @FXML private TextField tfTitle;
    @FXML private TextArea taDescription;
    @FXML private Label lblErrDate;
    @FXML private Label lblErrTitle;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;

    private final ScheduleController scheduleController;
    private final CompositeDisposable disposables;

    private Schedule mSchedule;

    public EditScheduleWindow(Database database, Stage owner) {
        super("Schedule", EditScheduleWindow.class.getResource("add_schedule.fxml"), null, owner);
        this.scheduleController = new ScheduleController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        lblTitle.setText("Update Schedule");
        btnSave.setText("Update");
        lblErrDate.setGraphic(new XCircleIcon(14));
        lblErrTitle.setGraphic(new XCircleIcon(14));

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
    }

    public void showAndWait(Schedule schedule) {
        if (schedule == null) {
            showWarningDialog("Invalid Action", "No selected Schedule entry. Try again.");
            return;
        }

        mSchedule = schedule;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        datePicker.setValue(mSchedule.getDate());
        tfTitle.setText(mSchedule.getTitle());
        taDescription.setText(mSchedule.getDescription());
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
             mSchedule.setTitle(ViewUtils.normalize(tfTitle.getText()));
             mSchedule.setDescription(ViewUtils.normalize(taDescription.getText()));
             mSchedule.setDate(datePicker.getValue());
            return scheduleController.update(mSchedule);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to update Schedule entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while updating Schedule entry.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
        mSchedule = null;
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
