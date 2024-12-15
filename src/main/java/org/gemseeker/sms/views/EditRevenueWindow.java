package org.gemseeker.sms.views;

import io.github.msufred.feathericons.XCircleIcon;
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
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Revenue;
import org.gemseeker.sms.data.controllers.RevenueController;

import java.io.File;

public class EditRevenueWindow extends AbstractWindow {

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

    private final RevenueController revenueController;
    private final CompositeDisposable disposables;

    private FileChooser fileChooser;
    private File imageFile;
    private Revenue mRevenue;

    public EditRevenueWindow(Database database, Stage owner) {
        super("Revenue", EditRevenueWindow.class.getResource("add_revenue.fxml"), null, owner);
        revenueController = new RevenueController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        lblTitle.setText("Edit Revenue");
        btnSave.setText("Update Revenue");

        setupIcons();

        cbTypes.setItems(Revenue.types);

        cbModes.setItems(Revenue.modes);
        cbModes.valueProperty().addListener((o, oldVal, newVal) -> {
            refGroup.setDisable(newVal.equals(Revenue.MODE_CASH));
            if (newVal.equals(Revenue.MODE_BANK_CASH)) {
                lblRef.setText("Account No.");
            } else {
                lblRef.setText("Reference No.");
            }
        });
        cbModes.setValue(Revenue.MODE_CASH);

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

    public void showAndWait(Revenue revenue) {
        if (revenue == null) {
            showWarningDialog("Invalid Action", "No selected Revenue entry. Try again.");
            return;
        }
        mRevenue = revenue;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        datePicker.setValue(mRevenue.getDate());
        cbTypes.setValue(mRevenue.getType());
        taDescription.setText(mRevenue.getDescription());
        tfAmount.setText(mRevenue.getAmount() + "");

        cbModes.setValue(mRevenue.getMode());
        tfRef.setText(mRevenue.getReference());

        if (!mRevenue.getAttachment().isBlank()) {
            imageFile = new File(Utils.REVENUES_IMAGE_FOLDER + Utils.FILE_SEPARATOR + mRevenue.getAttachment());
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

        return datePicker.getValue() != null && cbTypes.getValue() != null && !tfAmount.getText().isBlank() && cbModes.getValue() != null;
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            mRevenue.setDate(datePicker.getValue());
            mRevenue.setType(cbTypes.getValue());
            mRevenue.setDescription(ViewUtils.normalize(taDescription.getText()));
            String amountStr = tfAmount.getText().trim();
            mRevenue.setAmount(amountStr.isBlank() ? 0.0 : Double.parseDouble(amountStr));
            mRevenue.setMode(cbModes.getValue());
            mRevenue.setReference(ViewUtils.normalize(tfRef.getText()));
            mRevenue.setAttachment(imageFile != null ? imageFile.getName() : "");
            return revenueController.update(mRevenue);
        }).flatMap(success -> Single.fromCallable(() -> {
            if (imageFile != null) {
                String filename = imageFile.getName();
                File dest = new File(Utils.REVENUES_IMAGE_FOLDER + Utils.FILE_SEPARATOR + filename);
                FileUtils.copyFile(imageFile, dest);
            }
            return success;
        })).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to update Revenue entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while updating Revenue entry.\n" + err);
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
        mRevenue = null;
        imageFile = null;
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
        lblErrMode.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
