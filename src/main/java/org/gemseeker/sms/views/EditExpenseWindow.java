package org.gemseeker.sms.views;

import io.github.msufred.feathericons.UploadIcon;
import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.gemseeker.sms.Utils;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Expense;
import org.gemseeker.sms.data.Revenue;
import org.gemseeker.sms.data.controllers.ExpenseController;

import javax.swing.text.View;
import java.io.File;

public class EditExpenseWindow extends AbstractWindow {

    @FXML private Label lblTitle;
    @FXML private DatePicker datePicker;
    @FXML private Label lblErrDate;
    @FXML private ComboBox<String> cbCategories;
    @FXML private Label lblErrCategory;
    @FXML private ComboBox<String> cbTypes;
    @FXML private Label lblErrType;
    @FXML private TextArea taDescription;
    @FXML private TextField tfAmount;
    @FXML private Label lblErrAmount;
    @FXML private ComboBox<String> cbModes;
    @FXML private Label lblErrMode;
    @FXML private Label lblRef;
    @FXML private TextField tfRef;
    @FXML private HBox refGroup;
    @FXML private Button btnUpload;
    @FXML private Label lblPath;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;

    private final ExpenseController expenseController;
    private final CompositeDisposable disposables;
    private FileChooser fileChooser;

    private Expense mExpense;
    private File imageFile;

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

        cbCategories.setItems(Expense.categories);
        cbCategories.setValue(Expense.CAT_OPERATIONAL);
        cbTypes.setItems(Expense.operationalTypes);

        cbCategories.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal == null) return;
            cbTypes.setItems(newVal.equals(Expense.CAT_OPERATIONAL) ? Expense.operationalTypes : Expense.nonOperationalTypes);
        });

        cbModes.setItems(Expense.modes);
        cbModes.valueProperty().addListener((o, oldVal, newVal) -> {
            refGroup.setDisable(newVal.equals(Revenue.MODE_CASH));
            if (newVal.equals(Revenue.MODE_BANK_CASH)) {
                lblRef.setText("Account No.");
            } else {
                lblRef.setText("Reference No.");
            }
        });
        cbModes.setValue(Expense.MODE_CASH);

        btnUpload.setOnAction(evt -> {
            if (fileChooser == null) fileChooser = new FileChooser();
            imageFile = fileChooser.showOpenDialog(getStage().getOwner());
            if (imageFile != null) {
                lblPath.setText(imageFile.getPath());
            }
        });

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
        cbCategories.setValue(mExpense.getCategory());
        cbTypes.setValue(mExpense.getType());
        taDescription.setText(mExpense.getDescription());
        tfAmount.setText(mExpense.getAmount() + "");
        cbModes.setValue(mExpense.getMode());
        tfRef.setText(mExpense.getRef());
        if (!mExpense.getAttachment().isBlank()) {
            imageFile = new File(Utils.REVENUES_IMAGE_FOLDER + Utils.FILE_SEPARATOR + mExpense.getAttachment());
            lblPath.setText(imageFile.getPath());
        }
    }

    private boolean validated() {
        lblErrDate.setVisible(false);
        lblErrCategory.setVisible(false);
        lblErrType.setVisible(false);
        lblErrAmount.setVisible(false);
        lblErrMode.setVisible(false);

        lblErrDate.setVisible(datePicker.getValue() == null);
        lblErrCategory.setVisible(cbCategories.getValue() == null);
        lblErrType.setVisible(cbTypes.getValue() == null);
        lblErrAmount.setVisible(tfAmount.getText().isBlank());
        lblErrMode.setVisible(cbModes.getValue() == null);

        return datePicker.getValue() != null && cbCategories.getValue() != null && cbTypes.getValue() != null &&
                !tfAmount.getText().isBlank() && cbModes.getValue() != null;
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            mExpense.setDate(datePicker.getValue());
            mExpense.setCategory(cbCategories.getValue());
            mExpense.setType(cbTypes.getValue());
            mExpense.setDescription(ViewUtils.normalize(taDescription.getText()));
            String amountStr = tfAmount.getText().trim();
            mExpense.setAmount(amountStr.isBlank() ? 0.0 : Double.parseDouble(amountStr));
            mExpense.setMode(cbModes.getValue());
            mExpense.setRef(ViewUtils.normalize(tfRef.getText()));
            mExpense.setAttachment(imageFile != null ? imageFile.getName() : "");
            return expenseController.update(mExpense);
        }).flatMap(success -> Single.fromCallable(() -> {
            if (imageFile != null) {
                String filename = imageFile.getName();
                File dest = new File(Utils.EXPENSES_IMAGE_FOLDER + Utils.FILE_SEPARATOR + filename);
                FileUtils.copyFile(imageFile, dest);
            }
            return success;
        })).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
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
        imageFile = null;
    }

    private void setupIcons() {
        lblErrDate.setGraphic(new XCircleIcon(14));
        lblErrCategory.setGraphic(new XCircleIcon(14));
        lblErrType.setGraphic(new XCircleIcon(14));
        lblErrAmount.setGraphic(new XCircleIcon(14));
        lblErrMode.setGraphic(new XCircleIcon(14));
        btnUpload.setGraphic(new UploadIcon(14));
    }

    private void clearFields() {
        datePicker.setValue(null);
        cbCategories.setValue(Expense.CAT_OPERATIONAL);
        cbTypes.setValue(Expense.TYPE_ELECTRICITY);
        taDescription.clear();
        tfAmount.setText("0.0");
        cbModes.setValue(Expense.MODE_CASH);
        refGroup.setDisable(true);
        lblRef.setText("Reference No.");
        tfRef.clear();
        lblPath.setText("No Attached File");
        progressBar.setVisible(false);

        lblErrDate.setVisible(false);
        lblErrCategory.setVisible(false);
        lblErrType.setVisible(false);
        lblErrAmount.setVisible(false);
        lblErrMode.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
