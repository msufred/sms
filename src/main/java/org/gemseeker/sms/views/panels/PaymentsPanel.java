package org.gemseeker.sms.views.panels;

import io.github.msufred.feathericons.*;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gemseeker.sms.Settings;
import org.gemseeker.sms.data.Account;
import org.gemseeker.sms.data.BillingStatement;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Payment;
import org.gemseeker.sms.data.controllers.AccountController;
import org.gemseeker.sms.data.controllers.BillingController;
import org.gemseeker.sms.data.controllers.BillingStatementController;
import org.gemseeker.sms.data.controllers.PaymentController;
import org.gemseeker.sms.data.controllers.models.BillingPayment;
import org.gemseeker.sms.views.*;
import org.gemseeker.sms.views.cells.DateTableCell;
import org.gemseeker.sms.views.cells.DateTimeTableCell;
import org.gemseeker.sms.views.cells.StatusTableCell;
import org.gemseeker.sms.views.cells.TagTableCell;
import org.gemseeker.sms.views.icons.PesoIcon;

import javax.naming.Context;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.concurrent.Callable;

public class PaymentsPanel extends AbstractPanel {

    // <editor-fold default-state="collapsed" desc="FXML Components">
    @FXML private TabPane tabPane;
    @FXML private Tab tabBillings;
    @FXML private Tab tabOtherPayments;
    @FXML private Tab tabBillingStatements;
    @FXML private Tab tabReceipts;

    // billing payments group
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnRefresh;
    @FXML private Button btnAutomate;
    @FXML private Label lblAccounts;
    @FXML private ComboBox<Account> cbAccounts;
    @FXML private Label lblStatus;
    @FXML private ComboBox<String> cbStatus;
    @FXML private Label lblMonth;
    @FXML private ComboBox<String> cbMonths;
    @FXML private Label lblYear;
    @FXML private ComboBox<String> cbYears;
    @FXML private TableView<BillingPayment> billingsTable;
    @FXML private TableColumn<BillingPayment, String> colStatus;
    @FXML private TableColumn<BillingPayment, String> colOrNo;
    @FXML private TableColumn<BillingPayment, String> colBillingNo;
    @FXML private TableColumn<BillingPayment, String> colName;
    @FXML private TableColumn<BillingPayment, LocalDate> colFrom;
    @FXML private TableColumn<BillingPayment, LocalDate> colTo;
    @FXML private TableColumn<BillingPayment, Double> colAmountDue;
    @FXML private TableColumn<BillingPayment, Double> colAmountPaid;
    @FXML private TableColumn<BillingPayment, LocalDate> colDueDate;
    @FXML private TableColumn<BillingPayment, Double> colBalance;

    // Billing Statement Group
    @FXML private TableView<BillingStatement> billingStatementsTable;
    @FXML private TableColumn<BillingStatement, String> colStatementTag;
    @FXML private TableColumn<BillingStatement, String> colStatementBillingNo;
    @FXML private TableColumn<BillingStatement, Boolean> colStatementIncludeBalance;
    @FXML private TableColumn<BillingStatement, Double> colStatementBalance;
    @FXML private TableColumn<BillingStatement, Double> colStatementMonthlyFee;
    @FXML private TableColumn<BillingStatement, Double> colStatementDiscount;
    @FXML private TableColumn<BillingStatement, Double> colStatementPenalty;
    @FXML private TableColumn<BillingStatement, Double> colStatementVat;
    @FXML private TableColumn<BillingStatement, Double> colStatementTotal;
    @FXML private TableColumn<BillingStatement, LocalDateTime> colStatementDatePrinted;
    @FXML private TableColumn<BillingStatement, LocalDateTime> colStatementCreatedAt;
    @FXML private TableColumn<BillingStatement, LocalDateTime> colStatementUpdatedAt;
    @FXML private TableColumn<BillingStatement, String> colPreparedBy;
    @FXML private TableColumn<BillingStatement, String> colDesignation;
    @FXML private TableColumn<BillingStatement, String> colReceivedBy;

    // Payments Group
    @FXML private TableView<Payment> paymentsTable;
    @FXML private TableColumn<Payment, String> colPaymentTag;
    @FXML private TableColumn<Payment, String> colPaymentNo;
    @FXML private TableColumn<Payment, String> colPaymentStatus;
    @FXML private TableColumn<Payment, LocalDate> colPaymentDate;
    @FXML private TableColumn<Payment, String> colPaymentName;
    @FXML private TableColumn<Payment, String> colPaymentFor;
    @FXML private TableColumn<Payment, String> colPaymentToPay;
    @FXML private TableColumn<Payment, String> colPaymentDiscount;
    @FXML private TableColumn<Payment, String> colPaymentVat;
    @FXML private TableColumn<Payment, String> colPaymentSurcharges;
    @FXML private TableColumn<Payment, String> colPaymentTotal;
    @FXML private TableColumn<Payment, String> colPaymentPaid;
    @FXML private TableColumn<Payment, String> colPaymentBalance;
    @FXML private TableColumn<Payment, String> colPaymentPreparedBy;

    // </editor-fold>

    private FilteredList<BillingPayment> billingsList;
    private final SimpleObjectProperty<BillingPayment> selectedBilling = new SimpleObjectProperty<>();

    private FilteredList<BillingStatement> billingStatementList;
    private final SimpleObjectProperty<BillingStatement> selectedBillingStatement = new SimpleObjectProperty<>();

    private FilteredList<Payment> paymentList;
    private final SimpleObjectProperty<Payment> selectedPayment = new SimpleObjectProperty<>();

    private final MainWindow mainWindow;
    private final Settings settings;
    private final Database database;
    private final AccountController accountController;
    private final BillingController billingController;
    private final BillingStatementController billingStatementController;
    private final PaymentController paymentController;
    private final CompositeDisposable disposables;

    // Windows
    private AddBillingWindow addBillingWindow;
    private EditBillingWindow editBillingWindow;
    private PrepareBillingStatementWindow prepareBillingStatementWindow;
    private PrintWindow printWindow;
    private SaveImageWindow saveImageWindow;
    private AcceptPaymentWindow acceptPaymentWindow;
    private EditPaymentWindow editPaymentWindow;

    public PaymentsPanel(MainWindow mainWindow, Settings settings, Database database) {
        super(PaymentsPanel.class.getResource("payments.fxml"));
        this.mainWindow = mainWindow;
        this.settings = settings;
        this.database = database;
        this.accountController = new AccountController(database);
        this.billingController = new BillingController(database);
        this.billingStatementController = new BillingStatementController(database);
        this.paymentController = new PaymentController(database);
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        setupBillingsTable();
        setupBillingStatementsTable();
        setupPaymentsTable();

        btnAdd.setOnAction(evt -> addBilling());
        btnEdit.setOnAction(evt -> editSelectedBilling());
        btnDelete.setOnAction(evt -> deleteSelectedBilling());
        btnRefresh.setOnAction(evt -> refreshBillings());
        btnAutomate.setOnAction(evt -> {
            // TODO automate billings
        });

        cbAccounts.valueProperty().addListener((o, oldVal, newVal) -> updateFilters());

        cbStatus.setItems(FXCollections.observableArrayList("All", "For Payment", "Paid", "Overdue"));
        cbStatus.valueProperty().addListener((o, oldVal, newVal) -> updateFilters());
        cbStatus.getSelectionModel().select(0);

        cbMonths.setItems(FXCollections.observableArrayList(
                "All", Month.JANUARY.toString(), Month.FEBRUARY.toString(), Month.MARCH.toString(), Month.APRIL.toString(),
                Month.MAY.toString(), Month.JUNE.toString(), Month.JULY.toString(), Month.AUGUST.toString(),
                Month.SEPTEMBER.toString(), Month.OCTOBER.toString(), Month.NOVEMBER.toString(), Month.DECEMBER.toString()
        ));
        cbMonths.valueProperty().addListener((o, oldVal, newVal) -> updateFilters());
        cbMonths.getSelectionModel().select(0);

        cbYears.setItems(FXCollections.observableArrayList("All", "2022", "2023", "2024", "2025", "2026", "2027", "2028"));
        cbYears.valueProperty().addListener((o, oldVal, newVal) -> updateFilters());
        cbYears.getSelectionModel().select(0);

        tabPane.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, index) -> {
            switch (index.intValue()) {
                case 1 -> refreshOtherBillings();
                case 2 -> refreshBillingStatements();
                case 3 -> refreshReceipts();
                default -> refreshBillings();
            }
        });
    }

    @Override
    public void onResume() {
        // create PrintWindow and SaveImageWindow
        if (printWindow == null) printWindow = new PrintWindow(database, mainWindow.getStage());
        if (saveImageWindow == null) saveImageWindow = new SaveImageWindow(database, mainWindow.getStage());

        showProgress("Retrieving Account entries...");
        disposables.add(Single.fromCallable(accountController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(accounts -> {
                    hideProgress();
                    Account all = new Account();
                    all.setName("All Accounts");
                    accounts.add(0, all);
                    cbAccounts.setItems(accounts);
                    cbAccounts.getSelectionModel().select(0);
                    switch (tabPane.getSelectionModel().getSelectedIndex()) {
                        case 1: refreshBillingStatements(); break;
                        case 2: refreshOtherBillings(); break;
                        default: refreshBillings();
                    }
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Account entries.\n" + err);
                }));
    }

    private void refreshBillings() {
        showProgress("Retrieving Billing entries...");
        disposables.add(Single.fromCallable(billingController::getAllBillingWithPayment)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    billingsList = new FilteredList<>(list);
                    billingsTable.setItems(billingsList);

                    // clear account & status filters
                    cbAccounts.getSelectionModel().select(0);
                    cbStatus.getSelectionModel().select(0);

                    // set month & year filters to current month and year
                    LocalDate now = LocalDate.now();
                    cbMonths.setValue(now.getMonth().toString());
                    cbYears.setValue(now.getYear() + "");
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Billing entries.\n" + err);
                }));
    }

    private void refreshBillingStatements() {
        showProgress("Retrieving Billing Statement entries...");
        disposables.add(Single.fromCallable(billingStatementController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    billingStatementList = new FilteredList<>(list);
                    billingStatementsTable.setItems(billingStatementList);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Billing Statement entries.\n" + err);
                }));
    }

    private void refreshOtherBillings() {

    }

    private void refreshReceipts() {
        showProgress("Retrieving receipt entries...");
        disposables.add(Single.fromCallable(paymentController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    paymentList = new FilteredList<>(list);
                    paymentsTable.setItems(paymentList);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Payment entries.\n" + err);
                }));
    }

    @Override
    public void onPause() {
        // empty for now
    }

    private void addBilling() {
        if (addBillingWindow == null) addBillingWindow = new AddBillingWindow(database, printWindow, saveImageWindow, mainWindow.getStage());
        addBillingWindow.showAndWait();
        refreshBillings();
    }

    private void editSelectedBilling() {
        if (selectedBilling.get() == null) {
            showWarningDialog("Invalid", "No selected Billing entry. Try again.");
        } else {
            if (editBillingWindow == null) editBillingWindow = new EditBillingWindow(database, mainWindow.getStage());
            editBillingWindow.showAndWait(selectedBilling.get().getBillingNo());
            refreshBillings();
        }
    }

    private void acceptBillingPayment() {
        BillingPayment billing = selectedBilling.get();
        if (billing == null) {
            showWarningDialog("Invalid", "No selected Billing entry. Try again.");
        } else if (billing.getPaymentNo() != null && !billing.getPaymentNo().equalsIgnoreCase("null")) {
            showWarningDialog("Invalid", "Billing already paid.");
        } else {
            checkBillingStatementExists(billing.getBillingNo(), () -> {
                if (acceptPaymentWindow == null) acceptPaymentWindow = new AcceptPaymentWindow(
                        database, printWindow, saveImageWindow, mainWindow.getStage());
                acceptPaymentWindow.showAndWait(selectedBilling.get().getBillingNo());
                refreshBillings();
                refreshReceipts();
            }, () -> {
                showInfoDialog("Invalid", "No Billing Statement issued for " +
                        "this Billing entry.");
            });
        }
    }

    private void addBillingStatement() {
        if (selectedBilling.get() == null) {
            showWarningDialog("Invalid", "No selected Billing entry. Try again.");
        } else {
            checkBillingStatementExists(selectedBilling.get().getBillingNo(), () -> {
                showWarningDialog("Billing Statement Exists", "A Billing Statement already exists " +
                        "for this Billing entry.");
            }, () -> {
                if (prepareBillingStatementWindow == null) prepareBillingStatementWindow =
                        new PrepareBillingStatementWindow(database, mainWindow.getStage());
                prepareBillingStatementWindow.showAndWait(selectedBilling.get().getBillingNo());
                refreshBillings();
                refreshBillingStatements();
            });
        }
    }

    private void saveBillingAsImage() {
        if (selectedBilling.get() == null) {
            showWarningDialog("Invalid", "No selected Billing entry. Try again.");
        } else {
            checkBillingStatementExists(selectedBilling.get().getBillingNo(), () -> {
                // save billing as image
                if (saveImageWindow == null) saveImageWindow = new SaveImageWindow(database, mainWindow.getStage());
                saveImageWindow.showAndWait(SaveImageWindow.Type.STATEMENT, selectedBilling.get().getBillingNo());
            }, () -> {
                showWarningDialog("Invalid Action", "Create Billing Statement first.");
            });
        }
    }

    private void printBilling() {
        if (selectedBilling.get() == null) {
            showWarningDialog("Invalid", "No selected Billing entry. Try again.");
        } else {
            checkBillingStatementExists(selectedBilling.get().getBillingNo(), () -> {
                // print directly
                if (printWindow != null) {
                    printWindow.showAndWait(PrintWindow.Type.STATEMENT, selectedBilling.get().getBillingNo());
                }
            }, () -> {
                showWarningDialog("Invalid Action", "Create Billing Statement first.");
            });
        }
    }

    private void saveReceiptAsImage() {
        if (selectedBilling.get() == null) {
            showWarningDialog("Invalid", "No selected Billing entry. Try again.");
        } else {
            checkPaymentExists(selectedBilling.get().getBillingNo(), () -> {
                if (saveImageWindow != null) {
                    saveImageWindow.showAndWait(SaveImageWindow.Type.RECEIPT, selectedBilling.get().getBillingNo());
                }
            }, () -> {
                showWarningDialog("Invalid Action", "The Payment for this Billing does not exist.");
            });
        }
    }

    private void printReceipt() {
        if (selectedBilling.get() == null) {
            showWarningDialog("Invalid", "No selected Billing entry. Try again.");
        } else {
            checkPaymentExists(selectedBilling.get().getBillingNo(), () -> {
                if (printWindow != null) {
                    printWindow.showAndWait(PrintWindow.Type.RECEIPT, selectedBilling.get().getBillingNo());
                }
            }, () -> {
                showWarningDialog("Invalid Action", "The Payment for this Billing does not exist.");
            });
        }
    }

    private void deleteSelectedBilling() {
        if (selectedBilling.get() == null) {
            showWarningDialog("Invalid", "No selected Billing entry. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete Billing",
                    "Are you sure you want to delete this Billing entry?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) {
                deleteBilling(selectedBilling.get().getBillingId());
            }
        }
    }

    private void deleteBilling(int id) {
        showProgress("Deleting Billing entry...");
        disposables.add(Single.fromCallable(() -> billingController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to delete Billing entry.");
                    refreshBillings();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting Billing entry.\n" + err);
                }));
    }


    /**
     * Checks if BillingStatement entry for the given billing number exists.
     * Executes Runnable (by calling Callable.call()) objects according to the
     * query result.
     * @param billingNo String - billing number
     * @param onExists  Runnable - called if exists
     * @param onNotExists Runnable - called if not exists
     */
    private void checkBillingStatementExists(String billingNo, Runnable onExists,Runnable onNotExists) {
        showProgress("Checking if Billing Statement exists...");
        disposables.add(Single.fromCallable(() -> billingStatementController.hasBillingStatement(billingNo))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(exists -> {
                    hideProgress();
                    if (exists) onExists.run();
                    else onNotExists.run();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while checking Billing Statement entry.\n" + err);
                }));
    }

    private void checkPaymentExists(String billingNo, Runnable onExists, Runnable onNoExists) {
        showProgress("Checking if Payment for this Billing exists...");
        disposables.add(Single.fromCallable(() -> paymentController.hasPaymentForBilling(billingNo))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(exists -> {
                    hideProgress();
                    if (exists) onExists.run();
                    else onNoExists.run();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while querying database.\n" + err);
                }));
    }

    private void editSelectedBillingStatement() {
        if (selectedBillingStatement.get() == null) {
            showWarningDialog("Invalid", "No selected Billing Statement. Try again.");
        } else {
            if (prepareBillingStatementWindow == null) prepareBillingStatementWindow = new PrepareBillingStatementWindow(database, mainWindow.getStage());
            prepareBillingStatementWindow.showAndWait(selectedBillingStatement.get().getBillingNo());
            refreshBillingStatements();
        }
    }

    private void deleteSelectedBillingStatement() {
        if (selectedBillingStatement.get() == null) {
            showWarningDialog("Invalid", "No selected Billing Statement. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete Billing Statement",
                    "Are you sure you want to delete this Billing Statement?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) {
                deleteBillingStatement(selectedBillingStatement.get().getId());
            }
        }
    }

    private void deleteBillingStatement(int id) {
        showProgress("Deleting Billing Statement...");
        disposables.add(Single.fromCallable(() -> billingStatementController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to delete Billing Statement.");
                    refreshBillingStatements();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting Billing Statement.\n" + err);
                }));
    }

    private void editSelectedPayment() {
        if (selectedPayment.get() == null) {
            showWarningDialog("Invalid", "No selected Payment entry. Try again.");
        } else {
            if (editPaymentWindow == null) editPaymentWindow = new EditPaymentWindow(database, mainWindow.getStage());
            editPaymentWindow.showAndWait(selectedPayment.get().getId());
            refreshReceipts();
        }
    }

    private void deleteSelectedPayment() {
        if (selectedPayment.get() == null) {
            showWarningDialog("Invalid", "No selected Payment entry. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete Payment",
                    "Are you sure you want to delete this Payment entry?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) {
                deletePayment(selectedPayment.get().getId());
            }
        }
    }

    private void deletePayment(int id) {
        showProgress("Deleting Payment entry...");
        disposables.add(Single.fromCallable(() -> paymentController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to delete Payment entry.");
                    refreshReceipts();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting Payment entry.\n" + err);
                }));
    }

    private void updateFilters() {
        if (billingsList == null || cbAccounts.getValue() == null || cbStatus.getValue() == null ||
                cbMonths.getValue() == null || cbYears.getValue() == null) {
            return;
        }

        Account account = cbAccounts.getValue();
        String status = cbStatus.getValue();
        String monthStr = cbMonths.getValue();
        String yearStr = cbYears.getValue();

        if (account.getName().equals("All Accounts") && monthStr.equals("All") && yearStr.equals("All") && status.equals("All")) {
            billingsList.setPredicate(b -> true);
        }
        // filter by status
        else if (account.getName().equals("All Accounts") && monthStr.equals("All") && yearStr.equals("All")) {
            billingsList.setPredicate(b -> b.getStatus().equals(status));
        }

        // filter by year
        else if (account.getName().equals("All Accounts") && monthStr.equals("All") && status.equals("All")) {
            billingsList.setPredicate(b -> b.getDueDate().getYear() == Integer.parseInt(yearStr));
        }

        // filter by month
        else if (account.getName().equals("All Accounts") && yearStr.equals("All") && status.equals("All")) {
            billingsList.setPredicate(b -> b.getDueDate().getMonth() == Month.valueOf(monthStr));
        }

        // filter by account
        else if (monthStr.equals("All") && yearStr.equals("All") && status.equals("All")) {
            billingsList.setPredicate(b -> b.getAccountNo().equals(account.getAccountNo()));
        }

        // filter by year & status
        else if (account.getName().equals("All Accounts") && monthStr.equals("All")) {
            billingsList.setPredicate(b -> b.getDueDate().getYear() == Integer.parseInt(yearStr) && b.getStatus().equals(status));
        }

        // filter by month & year
        else if (account.getName().equals("All Accounts") && status.equals("All")) {
            billingsList.setPredicate(b -> b.getDueDate().getMonth() == Month.valueOf(monthStr) &&
                    b.getDueDate().getYear() == Integer.parseInt(yearStr));
        }

        // filter by account & month
        else if (yearStr.equals("All") && status.equals("All")) {
            billingsList.setPredicate(b -> b.getAccountNo().equals(account.getAccountNo()) && b.getDueDate().getMonth() == Month.valueOf(monthStr));
        }

        // filter by account & status
        else if (monthStr.equals("All") && yearStr.equals("All")) {
            billingsList.setPredicate(b -> b.getAccountNo().equals(account.getAccountNo()) && b.getStatus().equals(status));
        }

        // filter by month, year, & status
        else if (account.getName().equals("All Accounts")) {
            billingsList.setPredicate(b -> b.getDueDate().getMonth() == Month.valueOf(monthStr) &&
                    b.getDueDate().getYear() == Integer.parseInt(yearStr) &&
                    b.getStatus().equals(status));
        }

        // filter by account, year, & status
        else if (monthStr.equals("All")) {
            billingsList.setPredicate(b -> b.getAccountNo().equals(account.getAccountNo()) &&
                    b.getDueDate().getYear() == Integer.parseInt(yearStr) &&
                    b.getStatus().equals(status));
        }

        // filter by account, month, & status
        else if (yearStr.equals("All")) {
            billingsList.setPredicate(b -> b.getAccountNo().equals(account.getAccountNo()) &&
                    b.getDueDate().getMonth() == Month.valueOf(monthStr) &&
                    b.getStatus().equals(status));
        }

        // filter by account, month, & year
        else if (status.equals("All")) {
            billingsList.setPredicate(b -> b.getAccountNo().equals(account.getAccountNo()) &&
                    b.getDueDate().getMonth() == Month.valueOf(monthStr) &&
                    b.getDueDate().getYear() == Integer.parseInt(yearStr));
        }

        // filter by account, month, year, & status
        else {
            billingsList.setPredicate(b -> {
                return b.getAccountNo().equals(account.getAccountNo()) && b.getDueDate().getMonth() == Month.valueOf(monthStr) &&
                        b.getDueDate().getYear() == Integer.parseInt(yearStr) && b.getStatus().equals(status);
            });
        }
    }

    private void setupIcons() {
        tabBillings.setGraphic(new PesoIcon(14));
        tabBillingStatements.setGraphic(new FileTextIcon(14));
        tabOtherPayments.setGraphic(new FileTextIcon(14));
        tabReceipts.setGraphic(new FileTextIcon(14));

        btnAdd.setGraphic(new PlusIcon(14));
        btnEdit.setGraphic(new Edit2Icon(14));
        btnDelete.setGraphic(new TrashIcon(14));
        btnRefresh.setGraphic(new RefreshCwIcon(14));
        btnAutomate.setGraphic(new SettingsIcon(14));
        lblAccounts.setGraphic(new UserIcon(14));
        lblStatus.setGraphic(new SmileIcon(14));
        lblMonth.setGraphic(new CalendarIcon(14));
        lblYear.setGraphic(new CalendarIcon(14));
    }

    private void setupBillingsTable() {
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellFactory(col -> new StatusTableCell<>());
        colOrNo.setCellValueFactory(new PropertyValueFactory<>("paymentNo"));
        colOrNo.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String s, boolean empty) {
                if (empty || s == null || s.equalsIgnoreCase("null")) {
                    setText("");
                    setGraphic(null);
                } else {
                    setText(s);
                }
            }
        });
        colBillingNo.setCellValueFactory(new PropertyValueFactory<>("billingNo"));
        colName.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        colFrom.setCellValueFactory(new PropertyValueFactory<>("fromDate"));
        colFrom.setCellFactory(col -> new DateTableCell<>());
        colTo.setCellValueFactory(new PropertyValueFactory<>("toDate"));
        colTo.setCellFactory(col -> new DateTableCell<>());
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colDueDate.setCellFactory(col -> new DateTableCell<>());
        colAmountDue.setCellValueFactory(new PropertyValueFactory<>("amountTotal"));
        colAmountPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        MenuItem mAdd = new MenuItem("New Billing");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addBilling());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editSelectedBilling());

        MenuItem mAcceptPayment = new MenuItem("Accept Payment");
        mAcceptPayment.setGraphic(new PesoIcon(12));
        mAcceptPayment.setOnAction(evt -> acceptBillingPayment());

        MenuItem mAddStatement = new MenuItem("Create");
        mAddStatement.setGraphic(new PlusIcon(12));
        mAddStatement.setOnAction(evt -> addBillingStatement());

        MenuItem mSaveBilling = new MenuItem("Save As Image");
        mSaveBilling.setGraphic(new ImageIcon(12));
        mSaveBilling.setOnAction(evt -> saveBillingAsImage());

        MenuItem mPrintBilling = new MenuItem("Print Billing");
        mPrintBilling.setGraphic(new PrinterIcon(12));
        mPrintBilling.setOnAction(evt -> printBilling());

        Menu mBilling = new Menu("Billing Statement");
        mBilling.setGraphic(new FileIcon(12));
        mBilling.getItems().addAll(mAddStatement, mSaveBilling, mPrintBilling);

        MenuItem mSaveReceipt = new MenuItem("Save As Image");
        mSaveReceipt.setGraphic(new ImageIcon(12));
        mSaveReceipt.setOnAction(evt -> saveReceiptAsImage());

        MenuItem mPrintReceipt = new MenuItem("Print Receipt");
        mPrintReceipt.setGraphic(new PrinterIcon(12));
        mPrintReceipt.setOnAction(evt -> printReceipt());

        Menu mReceipt = new Menu("Receipt");
        mReceipt.setGraphic(new FileIcon(12));
        mReceipt.getItems().addAll(mSaveReceipt, mPrintReceipt);

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteSelectedBilling());

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mAcceptPayment, mBilling, mReceipt, new SeparatorMenuItem(), mDelete);
        billingsTable.setContextMenu(cm);
        selectedBilling.bind(billingsTable.getSelectionModel().selectedItemProperty());
    }

    private void setupBillingStatementsTable() {
        colStatementTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colStatementTag.setCellFactory(col -> new TagTableCell<>());
        colStatementBillingNo.setCellValueFactory(new PropertyValueFactory<>("billingNo"));
        colStatementIncludeBalance.setCellValueFactory(new PropertyValueFactory<>("includeBalance"));
        colStatementIncludeBalance.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean aBoolean, boolean isEmpty) {
                if (isEmpty) {
                    setText("");
                    setGraphic(null);
                } else {
                    setText(aBoolean ? "Yes" : "No");
                }
            }
        });
        colStatementBalance.setCellValueFactory(new PropertyValueFactory<>("prevBalance"));
        colStatementMonthlyFee.setCellValueFactory(new PropertyValueFactory<>("monthlyFee"));
        colStatementDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colStatementPenalty.setCellValueFactory(new PropertyValueFactory<>("penalty"));
        colStatementVat.setCellValueFactory(new PropertyValueFactory<>("vat"));
        colStatementTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colStatementCreatedAt.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        colStatementCreatedAt.setCellFactory(col -> new DateTimeTableCell<>());
        colStatementUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("dateUpdated"));
        colStatementUpdatedAt.setCellFactory(col -> new DateTimeTableCell<>());
        colStatementDatePrinted.setCellValueFactory(new PropertyValueFactory<>("datePrinted"));
        colStatementDatePrinted.setCellFactory(col -> new DateTimeTableCell<>());
        colPreparedBy.setCellValueFactory(new PropertyValueFactory<>("preparedBy"));
        colDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colReceivedBy.setCellValueFactory(new PropertyValueFactory<>("receivedBy"));

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(14));
        mEdit.setOnAction(evt -> editSelectedBillingStatement());

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(14));
        mDelete.setOnAction(evt -> deleteSelectedBillingStatement());

        ContextMenu cm = new ContextMenu(mEdit, mDelete);
        billingStatementsTable.setContextMenu(cm);

        selectedBillingStatement.bind(billingStatementsTable.getSelectionModel().selectedItemProperty());
    }

    private void setupPaymentsTable() {
        colPaymentTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colPaymentTag.setCellFactory(col -> new TagTableCell<>());
        colPaymentNo.setCellValueFactory(new PropertyValueFactory<>("paymentNo"));
        colPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colPaymentDate.setCellFactory(col -> new DateTableCell<>());
        colPaymentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPaymentFor.setCellValueFactory(new PropertyValueFactory<>("paymentFor"));
        colPaymentToPay.setCellValueFactory(new PropertyValueFactory<>("amountToPay"));
        colPaymentDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colPaymentVat.setCellValueFactory(new PropertyValueFactory<>("vat"));
        colPaymentSurcharges.setCellValueFactory(new PropertyValueFactory<>("surcharges"));
        colPaymentTotal.setCellValueFactory(new PropertyValueFactory<>("amountTotal"));
        colPaymentPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        colPaymentBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colPaymentPreparedBy.setCellValueFactory(new PropertyValueFactory<>("preparedBy"));

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editSelectedPayment());

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteSelectedPayment());

        ContextMenu cm = new ContextMenu(mEdit, mDelete);
        paymentsTable.setContextMenu(cm);

        selectedPayment.bind(paymentsTable.getSelectionModel().selectedItemProperty());
    }

    private void showProgress(String text) {
        mainWindow.showProgress(-1, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    @Override
    public void onDispose() {
        if (addBillingWindow != null) addBillingWindow.dispose();
        if (editBillingWindow != null) editBillingWindow.dispose();
        if (prepareBillingStatementWindow != null) prepareBillingStatementWindow.dispose();
        if (editPaymentWindow != null) editPaymentWindow.dispose();
        if (printWindow != null) printWindow.dispose();
        if (saveImageWindow != null) saveImageWindow.dispose();
        disposables.dispose();
    }
}
