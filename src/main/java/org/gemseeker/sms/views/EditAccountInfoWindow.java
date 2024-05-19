package org.gemseeker.sms.views;

import io.github.msufred.feathericons.CheckCircleIcon;
import io.github.msufred.feathericons.PlusIcon;
import io.github.msufred.feathericons.XCircleIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.AccountController;
import org.gemseeker.sms.data.controllers.DataPlanController;
import org.gemseeker.sms.data.controllers.SubscriptionController;
import org.gemseeker.sms.data.controllers.TowerController;

public class EditAccountInfoWindow extends AbstractWindow {

    @FXML private TextField tfAccountNo;
    @FXML private TextField tfName;
    @FXML private TextField tfAddress;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPhone;
    @FXML private Label lblErrName;
    @FXML private Label lblErrAddress;

    @FXML private ProgressBar progressBar;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final AccountController accountController;
    private final CompositeDisposable disposables;

    private String mAccountNo;
    private Account mAccount;

    public EditAccountInfoWindow(Database database, Stage owner) {
        super("Account Info", EditAccountInfoWindow.class.getResource("edit_account.fxml"), null, owner);
        accountController = new AccountController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
        btnCancel.setOnAction(evt -> close());
    }

    public void showAndWait(String accountNo) {
        if (accountNo == null) {
            showWarningDialog("Invalid Action", "No selected Account entry. Try again.");
            return;
        }

        mAccountNo = accountNo;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> accountController.getByAccountNo(mAccountNo))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(account -> {
                    progressBar.setVisible(false);
                    mAccount = account;
                    fillupFields();
                }, err -> {
                    progressBar.setVisible(false);
                    showErrorDialog("Database Error", "Error or no Account found:\n" + err);
                }));
    }

    private void fillupFields() {
        clearFields();
        tfAccountNo.setText(mAccount.getAccountNo());
        tfName.setText(mAccount.getName());
        tfAddress.setText(mAccount.getAddress());
        tfEmail.setText(mAccount.getEmail());
        tfPhone.setText(mAccount.getPhone());
    }

    private boolean validated() {
        lblErrName.setVisible(false);
        lblErrAddress.setVisible(false);
        lblErrName.setVisible(tfName.getText().isBlank());
        lblErrAddress.setVisible(tfAddress.getText().isBlank());
        return !tfName.getText().isBlank() && !tfAddress.getText().isBlank();
    }

    private void saveAndClose() {
        progressBar.setVisible(true);
        disposables.add(Single.fromCallable(() -> {
            mAccount.setName(ViewUtils.normalize(tfName.getText()));
            mAccount.setAddress(ViewUtils.normalize(tfAddress.getText()));
            mAccount.setEmail(ViewUtils.normalize(tfEmail.getText()));
            mAccount.setPhone(ViewUtils.normalize(tfPhone.getText()));
            return accountController.update(mAccount);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            progressBar.setVisible(false);
            if (!success) showWarningDialog("Failed", "Failed to update Account info.");
            close();
        }, err -> {
            progressBar.setVisible(false);
            showErrorDialog("Database Error", "Error while updating Account info.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
        mAccountNo = null;
        mAccount = null;
    }

    private void setupIcons() {
        lblErrName.setGraphic(new XCircleIcon(14));
        lblErrAddress.setGraphic(new XCircleIcon(14));
    }

    private void clearFields() {
        tfName.clear();
        tfAccountNo.clear();
        tfAddress.clear();
        tfEmail.clear();
        tfPhone.clear();
        lblErrName.setVisible(false);
        lblErrAddress.setVisible(false);
    }

    public void dispose() {
        disposables.dispose();
    }
}
