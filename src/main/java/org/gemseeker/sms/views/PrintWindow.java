package org.gemseeker.sms.views;

import io.github.msufred.feathericons.FilePlusIcon;
import io.github.msufred.feathericons.PrinterIcon;
import io.github.msufred.feathericons.ZoomInIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.*;
import org.gemseeker.sms.views.forms.BillingReceiptForm;
import org.gemseeker.sms.views.forms.StatementForm;
import org.gemseeker.sms.views.panels.AbstractPanel;

import java.util.Objects;

/**
 * Window for viewing forms before printing.
 */
public class PrintWindow extends AbstractWindow {

    public enum Type { STATEMENT, RECEIPT }

    @FXML private Label lblPrinter;
    @FXML private Label lblCopies;
    @FXML private Label lblZoom;
    @FXML private ScrollPane scrollPane;
    @FXML private StackPane contentPane;
    @FXML private ProgressIndicator progress;
    @FXML private ComboBox<Printer> cbPrinters;
    @FXML private Slider zoomSlider;
    @FXML private TextField tfCopies;
    @FXML private Button btnPrint;

    private final Scale scale = new Scale();

    private final AccountController accountController;
    private final SubscriptionController subscriptionController;
    private final BillingController billingController;
    private final BillingStatementController billingStatementController;
    private final PaymentController paymentController;
    private final CompositeDisposable disposables;

    private final ObservableList<Printer> printers = FXCollections.observableArrayList();

    private StatementForm statementForm;
    private BillingReceiptForm billingReceiptForm;

    private Type mType;
    private String mBillingNo;

    private Account account;
    private Subscription subscription;
    private Billing billing;
    private BillingStatement billingStatement;
    private Payment payment;

    private AbstractPanel mForm = null;
    private Node mContent = null;

    public PrintWindow(Database database, Stage owner) {
        super("Print", PrintWindow.class.getResource("print_window.fxml"), null, owner);
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
        lblPrinter.setGraphic(new PrinterIcon(14));
        lblCopies.setGraphic(new FilePlusIcon(14));
        lblZoom.setGraphic(new ZoomInIcon(14));
        btnPrint.setGraphic(new PrinterIcon(12));

        scale.xProperty().bind(zoomSlider.valueProperty());
        scale.yProperty().bind(zoomSlider.valueProperty());
        scale.setPivotX(0);
        scale.setPivotY(0);
        contentPane.getTransforms().add(scale);

        cbPrinters.setItems(printers);

        btnPrint.setOnAction(evt -> print());
    }

    public void showAndWait(Type type, String billingNo) {
        if (type == null || billingNo == null) return;
        mType = type;
        mBillingNo = billingNo;
        showAndWait();
    }

    @Override
    protected void onShow() {
        // refresh printers
        printers.clear();
        printers.addAll(Printer.getAllPrinters());
        cbPrinters.setValue(Printer.getDefaultPrinter());

        if (Objects.requireNonNull(mType) == Type.STATEMENT) {
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
                    if (statementForm == null) statementForm = new StatementForm();
                    mForm = statementForm;
                    fillStatementForm();
                }, err -> {
                    progress.setVisible(false);
                    showErrorDialog("Database Error", err.getMessage());
                }));
    }

    private void fillStatementForm() {
        mContent = statementForm.getView();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(mContent);
        statementForm.setData(account, subscription, billing, billingStatement);
        statementForm.onResume();
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
                    if (billingReceiptForm == null) billingReceiptForm = new BillingReceiptForm();
                    mForm = billingReceiptForm;
                    fillBillingReceiptForm();
                }));
    }

    private void fillBillingReceiptForm() {
        mContent = billingReceiptForm.getView();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(mContent);
        billingReceiptForm.setData(account, payment);
        billingReceiptForm.onResume();
    }

    private void print() {
        progress.setVisible(true);
        mContent.getTransforms().clear();
        contentPane.getTransforms().clear();

        if (mForm != null && mForm == statementForm) ((StatementForm) mForm).showTempBg(false);
        if (mForm != null && mForm == billingReceiptForm) ((BillingReceiptForm) mForm).showTempBg(false);

        Printer printer = cbPrinters.getValue() == null ? Printer.getDefaultPrinter() : cbPrinters.getValue();
        PrinterJob printerJob = PrinterJob.createPrinterJob(printer);

        PageLayout pageLayout = printer.createPageLayout(Paper.A4,
                PageOrientation.PORTRAIT, 0, 0, 0, 0);

        // set settings
        printerJob.getJobSettings().setPrintQuality(PrintQuality.NORMAL);
        printerJob.getJobSettings().setPageLayout(pageLayout);

        PrinterAttributes attr = printer.getPrinterAttributes();

        double scaleX = pageLayout.getPrintableWidth() / mContent.getBoundsInParent().getWidth();
        Scale printScale = new Scale(scaleX, scaleX);
        mContent.getTransforms().add(printScale);

        boolean success = printerJob.printPage(mContent);
        if (success) {
            printerJob.endJob();
        } else {
            showWarningDialog("Print Failed", "");
        }
        mContent.getTransforms().clear();
        contentPane.getTransforms().add(scale);
        progress.setVisible(false);
        close();
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
        if (statementForm != null) statementForm.onDispose();
        disposables.dispose();
    }
}
