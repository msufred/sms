package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.Utils;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.*;
import org.gemseeker.sms.views.forms.BillingReceiptEcopyForm;
import org.gemseeker.sms.views.forms.BillingReceiptForm;
import org.gemseeker.sms.views.forms.StatementEcopyForm;
import org.gemseeker.sms.views.panels.AbstractPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Window for saving forms as image.
 */
public class SaveImageWindow extends AbstractWindow {

    public enum Type { STATEMENT, RECEIPT }

    @FXML private Label lblZoom;
    @FXML private ScrollPane scrollPane;
    @FXML private StackPane contentPane;
    @FXML private ProgressIndicator progress;
    @FXML private Slider zoomSlider;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final Scale scale = new Scale(); // use for scaling contentPane

    // ModelControllers for accessing database entries
    private final AccountController accountController;
    private final SubscriptionController subscriptionController;
    private final BillingController billingController;
    private final BillingStatementController billingStatementController;
    private final PaymentController paymentController;
    private final CompositeDisposable disposables;

    // billing statement & receipt e-copy forms
    private StatementEcopyForm statementEcopyForm;
    private BillingReceiptEcopyForm billingReceiptEcopyForm;

    private Account account;
    private Subscription subscription;
    private Billing billing;
    private BillingStatement billingStatement;
    private Payment payment;

    private SaveImageWindow.Type mType;
    private String mBillingNo;
    private AbstractPanel mForm = null;
    private Node mContent = null;

    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;

    public SaveImageWindow(Database database, Stage owner) {
        super("Save As Image", SaveImageWindow.class.getResource("save_image_window.fxml"), null, owner);
        this.accountController = new AccountController(database);
        this.subscriptionController = new SubscriptionController(database);
        this.billingController = new BillingController(database);
        this.billingStatementController = new BillingStatementController(database);
        this.paymentController = new PaymentController(database);
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        scale.xProperty().bind(zoomSlider.valueProperty());
        scale.yProperty().bind(zoomSlider.valueProperty());
        scale.setPivotX(0);
        scale.setPivotY(0);
        contentPane.getTransforms().add(scale);

        btnSave.setOnAction(evt -> saveImage());
        btnCancel.setOnAction(evt -> close());
    }

    /**
     * Must be called instead of showAndWait().
     */
    public void showAndWait(SaveImageWindow.Type type, String billingNo) {
        if (type == null || billingNo == null) return;
        mType = type;
        mBillingNo = billingNo;
        showAndWait();
    }

    @Override
    protected void onShow() {
        if (mType == null && mBillingNo == null) {
            showWarningDialog("Invalid", "No selected Billing entry. Try again.");
            close();
        }

        if (mType == SaveImageWindow.Type.STATEMENT) {
            loadBillingStatementForm();
        } else {
            loadBillingReceiptForm();
        }
    }

    private void loadBillingStatementForm() {
        progress.setVisible(true);
        disposables.add(Single.fromCallable(() -> billingStatementController.getByBillingNo(mBillingNo))
                .flatMap(bStatement -> {
                    billingStatement = bStatement;
                    return Single.fromCallable(() -> billingController.getByBillingNo(mBillingNo));
                }).flatMap(b -> {
                    billing = b;
                    return Single.fromCallable(() -> accountController.getByAccountNo(b.getAccountNo()));
                }).flatMap(acct -> {
                    account = acct;
                    return Single.fromCallable(() -> subscriptionController.getByAccountNo(acct.getAccountNo()));
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(sub -> {
                    progress.setVisible(false);
                    subscription = sub;
                    if (statementEcopyForm == null) statementEcopyForm = new StatementEcopyForm();
                    mForm = statementEcopyForm;
                    fillStatementForm();
                }, err -> {
                    progress.setVisible(false);
                    showErrorDialog("Database Error", err.getMessage());
                }));
    }

    private void fillStatementForm() {
        mContent = statementEcopyForm.getView();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(mContent);
        statementEcopyForm.setData(account, subscription, billing, billingStatement);
        statementEcopyForm.onResume();
    }

    private void loadBillingReceiptForm() {
        progress.setVisible(true);
        disposables.add(Single.fromCallable(() -> billingStatementController.getByBillingNo(mBillingNo))
                .flatMap(bStatement -> {
                    billingStatement = bStatement;
                    return Single.fromCallable(() -> billingController.getByBillingNo(mBillingNo));
                }).flatMap(b -> {
                    billing = b;
                    return Single.fromCallable(() -> accountController.getByAccountNo(b.getAccountNo()));
                }).flatMap(acct -> {
                    account = acct;
                    return Single.fromCallable(() -> paymentController.getByExtraInfo(mBillingNo));
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(p -> {
                    progress.setVisible(false);
                    payment = p;
                    if (billingReceiptEcopyForm == null) billingReceiptEcopyForm = new BillingReceiptEcopyForm();
                    mForm = billingReceiptEcopyForm;
                    fillBillingReceiptForm();
                }));
    }

    private void fillBillingReceiptForm() {
        mContent = billingReceiptEcopyForm.getView();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(mContent);
        billingReceiptEcopyForm.setData(account, payment);
        billingReceiptEcopyForm.onResume();
    }


    private void saveImage() {
        if (mForm == null) return;

        if (directoryChooser == null) directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(getStage());
        if (dir != null) {
            String filename = String.format("%s_%s.png", mBillingNo, LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddyyy_hms")));
            File output = new File(dir.getPath() + Utils.FILE_SEPARATOR + filename);
            try {
                WritableImage snapshot = mForm.getView().snapshot(new SnapshotParameters(), null);
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);

                if (output.exists()) {
                    showInfoDialog("Image Saved", "File saved to " + output.getPath());
                    Desktop.getDesktop().open(dir); // open file folder
                } else {
                    showInfoDialog("Image Not Saved", "Failed to save image to " + output.getPath());
                }
                close();
            } catch (IOException e) {
                showErrorDialog("IOException", "Failed to save image.\n" + e);
            }
        }
    }

    @Override
    protected void onClose() {
        mType = null;
        mBillingNo = null;
        account = null;
        subscription = null;
        billingStatement = null;
        billing = null;
        payment = null;
        contentPane.getChildren().clear();
        mContent = null;
    }

    public void dispose() {
        if (statementEcopyForm != null) statementEcopyForm.onDispose();
        if (billingReceiptEcopyForm != null) billingReceiptEcopyForm.onDispose();
        disposables.dispose();
    }
}
