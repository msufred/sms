package org.gemseeker.sms.views;

import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.gemseeker.sms.data.DailySummary;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Expense;
import org.gemseeker.sms.data.Revenue;
import org.gemseeker.sms.data.controllers.DailySummaryController;
import org.gemseeker.sms.data.controllers.ExpenseController;
import org.gemseeker.sms.data.controllers.RevenueController;

import java.time.LocalDate;

public class RecalculateWindow extends AbstractWindow {

    @FXML private DatePicker datePicker;
    @FXML private Label lblErrDate;
    @FXML private Button btnRecalculate;

    private final DailySummaryController summaryController;
    private final RevenueController revenueController;
    private final ExpenseController expenseController;
    private final CompositeDisposable disposables;

    public RecalculateWindow(Database database, Stage owner) {
        super("Recalculate Daily Summaries", RecalculateWindow.class.getResource("select_start_date.fxml"), null, owner);
        summaryController = new DailySummaryController(database);
        revenueController = new RevenueController(database);
        expenseController = new ExpenseController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        lblErrDate.getStyleClass().add("label-error");
        lblErrDate.setGraphic(new XCircleIcon(14));
        lblErrDate.setVisible(false);

        btnRecalculate.setOnAction(evt -> {
            if (validated()) recalculate();
        });
    }

    @Override
    protected void onShow() {
        // empty
    }

    private boolean validated() {
        lblErrDate.setVisible(datePicker.getValue() == null);
        return datePicker.getValue() != null;
    }

    private void recalculate() {
        LocalDate startDate = datePicker.getValue();
        LocalDate now = LocalDate.now();
        disposables.add(Completable.fromAction(() -> {
            // clear daily summaries table
            summaryController.deleteAll();

            // get all expenses and revenues
            ObservableList<Revenue> revenues = revenueController.getAll();
            ObservableList<Expense> expenses = expenseController.getAll();

            LocalDate curDate = startDate;
            double lastBalance = 0;
            while (!curDate.isAfter(now)) {
                double totalRevenues = 0;
                double totalExpenses = 0;
                double balance = 0;

                for (Revenue r : revenues) {
                    if (r.getDate().isEqual(curDate)) totalRevenues += r.getAmount();
                }

                for (Expense e : expenses) {
                    if (e.getDate().isEqual(curDate)) totalExpenses += e.getAmount();
                }

                balance = lastBalance + totalRevenues - totalExpenses;

                DailySummary summary = new DailySummary();
                summary.setDate(curDate);
                summary.setForwarded(lastBalance);
                summary.setRevenues(totalRevenues);
                summary.setExpenses(totalExpenses);
                summary.setBalance(balance);
                summaryController.insert(summary);

                lastBalance = balance;
                curDate = curDate.plusDays(1);
            }
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(this::close, err -> {
            showErrorDialog("Database Error", "Error while recalculating Daily Summaries");
        }));
    }

    @Override
    protected void onClose() {
        datePicker.setValue(null);
        lblErrDate.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
