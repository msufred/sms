package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.gemseeker.sms.Utils;
import org.gemseeker.sms.data.CashTransaction;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.CashTransactionController;
import org.gemseeker.sms.views.icons.UploadIcon;
import org.gemseeker.sms.views.icons.XCircleIcon;

import java.io.File;

public class EditCashTransactionWindow extends AbstractWindow {

    @FXML private Label lblTitle;
    @FXML private DatePicker datePicker;
    @FXML private Label lblErrDate;
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

    private final CashTransactionController cashTransactionController;
    private final CompositeDisposable disposables;

    private FileChooser fileChooser;
    private CashTransaction mCashTransaction;
    private File imageFile;

    public EditCashTransactionWindow(Database database, Stage owner) {
        super("Expense", EditCashTransactionWindow.class.getResource("add_transaction.fxml"), null, owner);
        this.cashTransactionController = new CashTransactionController(database);
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        lblTitle.setText("Edit Cash Transaction");
        btnSave.setText("Update Transaction");
        setupIcons();

        cbTypes.setItems(CashTransaction.types);

        cbModes.setItems(CashTransaction.modes);
        cbModes.valueProperty().addListener((o, oldVal, newVal) -> {
            refGroup.setDisable(newVal.equals(CashTransaction.MODE_CASH));
            if (newVal.equals(CashTransaction.MODE_BANK_CASH)) {
                lblRef.setText("Account No.");
            } else {
                lblRef.setText("Reference No.");
            }
        });
        cbModes.setValue(CashTransaction.MODE_CASH);

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

    public void showAndWait(CashTransaction cashTransaction) {
        if (cashTransaction == null) {
            showWarningDialog("Invalid Action", "No selected Cash Transaction entry. Try again.");
            return;
        }
        mCashTransaction = cashTransaction;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        datePicker.setValue(mCashTransaction.getDate());
        cbTypes.setValue(mCashTransaction.getType());
        taDescription.setText(mCashTransaction.getDescription());
        tfAmount.setText(mCashTransaction.getAmount() + "");
        cbModes.setValue(mCashTransaction.getMode());
        tfRef.setText(mCashTransaction.getReference());
        if (!mCashTransaction.getAttachment().isBlank()) {
            imageFile = new File(Utils.CASH_TRANSACTIONS_IMAGE_FOLDER + Utils.FILE_SEPARATOR + mCashTransaction.getAttachment());
            lblPath.setText(imageFile.getPath());
        }
    }

    private boolean validated() {
        lblErrDate.setVisible(false);
        lblErrType.setVisible(false);
        lblErrAmount.setVisible(false);
        lblErrMode.setVisible(false);

        lblErrDate.setVisible(datePicker.getValue() == null);
        lblErrType.setVisible(cbTypes.getValue() == null);
        lblErrAmount.setVisible(tfAmount.getText().isBlank());
        lblErrMode.setVisible(cbModes.getValue() == null);

        return datePicker.getValue() != null && cbTypes.getValue() != null && !tfAmount.getText().isBlank()
                && cbModes.getValue() != null;
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            mCashTransaction.setDate(datePicker.getValue());
            mCashTransaction.setType(cbTypes.getValue());
            mCashTransaction.setDescription(ViewUtils.normalize(taDescription.getText()));
            String amountStr = tfAmount.getText().trim();
            mCashTransaction.setAmount(amountStr.isBlank() ? 0.0 : Double.parseDouble(amountStr));
            mCashTransaction.setMode(cbModes.getValue());
            mCashTransaction.setReference(ViewUtils.normalize(tfRef.getText()));
            if (imageFile != null) {
                String filename = imageFile.getName();
                mCashTransaction.setAttachment(filename);
            }
            return cashTransactionController.update(mCashTransaction);
        }).flatMap(success -> Single.fromCallable(() -> {
            if (imageFile != null) {
                String filename = imageFile.getName();
                File dest = new File(Utils.CASH_TRANSACTIONS_IMAGE_FOLDER + Utils.FILE_SEPARATOR + filename);
                FileUtils.copyFile(imageFile, dest);
            }
            return success;
        })).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to update Cash Transaction entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while updating Cash Transaction entry.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
        mCashTransaction = null;
        imageFile = null;
    }

    private void setupIcons() {
        lblErrDate.setGraphic(new XCircleIcon(14));
        lblErrType.setGraphic(new XCircleIcon(14));
        lblErrAmount.setGraphic(new XCircleIcon(14));
        lblErrMode.setGraphic(new XCircleIcon(14));
        btnUpload.setGraphic(new UploadIcon(14));
    }

    private void clearFields() {
        datePicker.setValue(null);
        cbTypes.setValue(CashTransaction.TYPE_CASH_IN);
        taDescription.clear();
        tfAmount.setText("0.0");
        cbModes.setValue(CashTransaction.MODE_CASH);
        refGroup.setDisable(true);
        lblRef.setText("Reference No.");
        tfRef.clear();
        lblPath.setText("No Attached File");
        progressBar.setVisible(false);

        lblErrDate.setVisible(false);
        lblErrType.setVisible(false);
        lblErrAmount.setVisible(false);
        lblErrMode.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
