package org.gemseeker.sms.views;

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
import org.gemseeker.sms.data.CashTransaction;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.CashTransactionController;
import org.gemseeker.sms.views.icons.XCircleIcon;

import java.io.File;

public class AddCashTransactionWindow extends AbstractWindow {

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
    private File imageFile;

    public AddCashTransactionWindow(Database database, Stage owner) {
        super("Cash Transaction", AddCashTransactionWindow.class.getResource("add_transaction.fxml"), null, owner);
        cashTransactionController = new CashTransactionController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();

        cbTypes.setItems(CashTransaction.types);
        cbTypes.setValue(CashTransaction.TYPE_CASH_IN);

        cbModes.setItems(FXCollections.observableArrayList(
                CashTransaction.MODE_CASH, CashTransaction.MODE_BANK_CASH
        ));
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

    @Override
    protected void onShow() {
        clearFields();
        imageFile = null;
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
            CashTransaction transaction = new CashTransaction();
            transaction.setDate(datePicker.getValue());
            transaction.setType(cbTypes.getValue());
            transaction.setDescription(ViewUtils.normalize(taDescription.getText()));
            String amountStr = tfAmount.getText().trim();
            transaction.setAmount(amountStr.isBlank() ? 0.0 : Double.parseDouble(amountStr));
            transaction.setMode(cbModes.getValue());
            transaction.setReference(ViewUtils.normalize(tfRef.getText()));
            if (imageFile != null) {
                String filename = imageFile.getName();
                transaction.setAttachment(filename);
            }
            return cashTransactionController.insert(transaction);
        }).flatMap(success -> Single.fromCallable(() -> {
            if (imageFile != null) {
                String filename = imageFile.getName();
                File dest = new File(Utils.CASH_TRANSACTIONS_IMAGE_FOLDER + Utils.FILE_SEPARATOR + filename);
                FileUtils.copyFile(imageFile, dest);
            }
            return success;
        })).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to add new Cash Transaction entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while adding new Cash Transaction entry.\n" + err);
        }));
    }

    private void setupIcons() {
        lblErrDate.setGraphic(new XCircleIcon(14));
        lblErrType.setGraphic(new XCircleIcon(14));
        lblErrAmount.setGraphic(new XCircleIcon(14));
        lblErrMode.setGraphic(new XCircleIcon(14));
    }

    @Override
    protected void onClose() {
        clearFields();
        imageFile = null;
    }

    private void clearFields() {
        datePicker.setValue(null);
        cbTypes.setValue(null);
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
