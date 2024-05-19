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
import org.gemseeker.sms.data.Revenue;
import org.gemseeker.sms.data.controllers.RevenueController;

public class AddRevenueWindow extends AbstractWindow {

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> cbTypes;
    @FXML private TextArea taDescription;
    @FXML private TextField tfAmount;
    @FXML private Label lblErrDate;
    @FXML private Label lblErrType;
    @FXML private Label lblErrAmount;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;

    private final RevenueController revenueController;
    private final CompositeDisposable disposables;

    public AddRevenueWindow(Database database, Stage owner) {
        super("Revenue", AddRevenueWindow.class.getResource("add_revenue.fxml"), null, owner);
        revenueController = new RevenueController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();

        cbTypes.setItems(FXCollections.observableArrayList(
                Revenue.TYPE_BILLING,
                Revenue.TYPE_PURCHASE,
                Revenue.TYPE_SERVICE,
                Revenue.TYPE_WIFI_VENDO,
                Revenue.TYPE_OTHERS
        ));

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
            Revenue revenue = new Revenue();
            revenue.setDate(datePicker.getValue());
            revenue.setType(cbTypes.getValue());
            revenue.setDescription(ViewUtils.normalize(taDescription.getText()));
            String amountStr = tfAmount.getText().trim();
            revenue.setAmount(amountStr.isBlank() ? 0.0 : Double.parseDouble(amountStr));
            return revenueController.insert(revenue);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to add new Revenue entry.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while adding new Revenue entry.\n" + err);
        }));
    }

    private void setupIcons() {
        lblErrDate.setGraphic(new XCircleIcon(14));
        lblErrType.setGraphic(new XCircleIcon(14));
        lblErrAmount.setGraphic(new XCircleIcon(14));
    }

    @Override
    protected void onClose() {
        clearFields();
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
