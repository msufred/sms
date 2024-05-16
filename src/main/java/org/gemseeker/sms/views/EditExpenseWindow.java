package org.gemseeker.sms.views;

import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Expense;
import org.gemseeker.sms.data.controllers.ExpenseController;

public class EditExpenseWindow extends AbstractWindow {

    @FXML private Label lblTitle;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> cbTypes;
    @FXML private TextArea taDescription;
    @FXML private TextField tfAmount;
    @FXML private Label lblErrDate;
    @FXML private Label lblErrType;
    @FXML private Label lblErrAmount;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;

    private final ExpenseController expenseController;
    private final CompositeDisposable disposables;

    private Expense mExpense;

    public EditExpenseWindow(Database database, Stage owner) {
        super("Expense", EditExpenseWindow.class.getResource("add_expense.fxml"), null, owner);
        this.expenseController = new ExpenseController(database);
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        lblTitle.setText("Edit Expense");
        btnSave.setText("Update Expense");
        setupIcons();

        cbTypes.setItems(FXCollections.observableArrayList(
                Expense.TYPE_ELECTRICITY,
                Expense.TYPE_WATER,
                Expense.TYPE_EDUCATION,
                Expense.TYPE_FOOD_GROCERY,
                Expense.TYPE_TRANSPORTATION,
                Expense.TYPE_RENTAL,
                Expense.TYPE_REPAIR,
                Expense.TYPE_MAINTENANCE,
                Expense.TYPE_PERIPHERALS,
                Expense.TYPE_INTERNET,
                Expense.TYPE_PAYMENT,
                Expense.TYPE_OTHERS
        ));

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
    }

    public void showAndWait(Expense expense) {
        if (expense == null) {
            showWarningDialog("Invalid Action", "No selected Expense entry. Try again.");
            return;
        }
        mExpense = expense;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        datePicker.setValue(mExpense.getDate());
        cbTypes.setValue(mExpense.getType());
        taDescription.setText(mExpense.getDescription());
        tfAmount.setText(mExpense.getAmount() + "");
    }

    private boolean validated() {
        lblErrDate.setVisible(false);
        lblErrType.setVisible(false);
        lblErrAmount.setVisible(false);

        lblErrDate.setVisible(datePicker.getValue() == null);
        lblErrType.setVisible(cbTypes.getValue() == null);
        lblErrAmount.setVisible(tfAmount.getText().isBlank());

        return datePicker.getValue() != null && cbTypes.getValue() != null && !tfAmount.getText().isBlank();
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            mExpense.setDate(datePicker.getValue());
            mExpense.setType(cbTypes.getValue());
            mExpense.setDescription(ViewUtils.normalize(taDescription.getText()));
            String amountStr = tfAmount.getText().trim();
            mExpense.setAmount(amountStr.isBlank() ? 0.0 : Double.parseDouble(amountStr));
            return expenseController.update(mExpense);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to update Expense entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while updating Expense entry.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
        mExpense = null;
    }

    private void setupIcons() {
        lblErrDate.setGraphic(new XCircleIcon(14));
        lblErrType.setGraphic(new XCircleIcon(14));
        lblErrAmount.setGraphic(new XCircleIcon(14));
    }

    private void clearFields() {
        datePicker.setValue(null);
        cbTypes.setValue(null);
        taDescription.clear();
        tfAmount.setText("0.0");
        progressBar.setVisible(false);

        lblErrDate.setVisible(false);
        lblErrType.setVisible(false);
        lblErrAmount.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
