package org.gemseeker.sms.views.panels;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.*;
import org.gemseeker.sms.views.*;
import org.gemseeker.sms.views.cells.AmountTableCell;
import org.gemseeker.sms.views.cells.DateTableCell;
import org.gemseeker.sms.views.cells.TagTableCell;
import org.gemseeker.sms.views.icons.*;
import org.gemseeker.sms.views.panels.dashboard.*;

import java.time.LocalDate;
import java.util.*;

public class DashboardPanel extends AbstractPanel {

    // Summary
    @FXML private Label lblForwarded;
    @FXML private Label lblRevenues;
    @FXML private Label lblExpenses;
    @FXML private Label lblCashIn;
    @FXML private Label lblBalances;
    @FXML private Label lblReceivables;

    // Cash Breakdown
    @FXML private Label lblCash;
    @FXML private Label lblGCash;
    @FXML private Label lblBank;
    @FXML private Label lblCheque;
    @FXML private Label lblPalawan;

    // Tabs
    @FXML private TabPane tabPane;
    @FXML private Tab tabProjections;
    @FXML private Tab tabRevenues;
    @FXML private Tab tabExpenses;
    @FXML private Tab tabTransactions;
    @FXML private Tab tabSummaries;

    // Projections
    @FXML private PieChart expensesPieChart; // Operational vs Non-Operational
    @FXML private BarChart<String, Number> operationalBarChart;
    @FXML private BarChart<String, Number> nonOperationalBarChart;
    @FXML private BarChart<String, Number> revenuesBarChart;
    @FXML private PieChart expensesRevenuesPieChart; // Expenses vs Revenues
    @FXML private LineChart<String, Number> monthlyLineChart;
    @FXML private CategoryAxis monthlyXAxis;
    @FXML private NumberAxis monthlyYAxis;
    @FXML private LineChart<String, Number> dailyLineChart;
    @FXML private CategoryAxis dailyXAxis;
    @FXML private NumberAxis dailyYAxis;

    // Revenues
    @FXML private Button btnAddRevenue;
    @FXML private Button btnEditRevenue;
    @FXML private Button btnDeleteRevenue;
    @FXML private Button btnExportRevenues;
    @FXML private ComboBox<String> cbRevenueModes;

    @FXML private Label lblRevCash;
    @FXML private Label lblRevGCash;
    @FXML private Label lblRevBank;
    @FXML private Label lblRevCheque;
    @FXML private Label lblRevPalawan;

    @FXML private TableView<Revenue> revenuesTable;
    @FXML private TableColumn<Revenue, String> colRevenueTag;
    @FXML private TableColumn<Revenue, LocalDate> colRevenueDate;
    @FXML private TableColumn<Revenue, Double> colRevenueAmount;
    @FXML private TableColumn<Revenue, String> colRevenueType;
    @FXML private TableColumn<Revenue, String> colRevenueDescription;
    @FXML private TableColumn<Revenue, String> colRevenueMode;
    @FXML private TableColumn<Revenue, String> colRevenueRef;
    @FXML private TableColumn<Revenue, String> colRevenueAttachment;

    // Expenses
    @FXML private Button btnAddExpense;
    @FXML private Button btnEditExpense;
    @FXML private Button btnDeleteExpense;
    @FXML private Button btnExportExpenses;
    @FXML private ComboBox<String> cbExpensesModes;

    @FXML private Label lblExpCash;
    @FXML private Label lblExpGCash;
    @FXML private Label lblExpBank;
    @FXML private Label lblExpCheque;
    @FXML private Label lblExpPalawan;

    @FXML private TableView<Expense> expensesTable;
    @FXML private TableColumn<Expense, String> colExpenseTag;
    @FXML private TableColumn<Expense, LocalDate> colExpenseDate;
    @FXML private TableColumn<Expense, Double> colExpenseAmount;
    @FXML private TableColumn<Expense, String> colExpenseCategory;
    @FXML private TableColumn<Expense, String> colExpenseType;
    @FXML private TableColumn<Expense, String> colExpenseDescription;
    @FXML private TableColumn<Expense, String> colExpenseMode;
    @FXML private TableColumn<Expense, String> colExpenseReference;
    @FXML private TableColumn<Expense, String> colExpenseAttachment;

    // Transactions
    @FXML private Button btnAddTransaction;
    @FXML private Button btnEditTransaction;
    @FXML private Button btnDeleteTransaction;
    @FXML private Button btnTransfer;
    @FXML private Button btnExportTransactions;
    @FXML private ComboBox<String> cbTransactionsMode;

    @FXML private Label lblCtCash;
    @FXML private Label lblCtGCash;
    @FXML private Label lblCtBank;
    @FXML private Label lblCtCheque;
    @FXML private Label lblCtPalawan;

    @FXML private TableView<CashTransaction> transactionsTable;
    @FXML private TableColumn<CashTransaction, String> colTransactionsTag;
    @FXML private TableColumn<CashTransaction, LocalDate> colTransactionsDate;
    @FXML private TableColumn<CashTransaction, String> colTransactionsType;
    @FXML private TableColumn<CashTransaction, Double> colTransactionsAmount;
    @FXML private TableColumn<CashTransaction, String> colTransactionsDescription;
    @FXML private TableColumn<CashTransaction, String> colTransactionsMode;
    @FXML private TableColumn<CashTransaction, String> colTransactionsReference;
    @FXML private TableColumn<CashTransaction, String> colTransactionsAttachment;

    // Summaries
    @FXML private Button btnExportSummaries;
    @FXML private Button btnRecalculate;
    @FXML private TableView<DailySummary> summariesTable;
    @FXML private TableColumn<DailySummary, String> colSummaryTag;
    @FXML private TableColumn<DailySummary, LocalDate> colSummaryDate;
    @FXML private TableColumn<DailySummary, Double> colSummaryForwarded;
    @FXML private TableColumn<DailySummary, Double> colSummaryRevenues;
    @FXML private TableColumn<DailySummary, Double> colSummaryExpenses;
    @FXML private TableColumn<DailySummary, Double> colSummaryCashIn;
    @FXML private TableColumn<DailySummary, Double> colSummaryCashOut;
    @FXML private TableColumn<DailySummary, Double> colSummaryBalance;

    private final MainWindow mainWindow;
    private final Database database;
    private final CompositeDisposable disposables;

    // ModelControllers
    private final BillingController billingController;
    private final RevenueController revenueController;
    private final ExpenseController expenseController;
    private final CashTransactionController cashTransactionController;
    private final DailySummaryController dailySummaryController;

    // Summary Values
    private DailySummary mPrevSummary, mSummary;
    private ObservableList<Revenue> revenuesToday;
    private ObservableList<Expense> expensesToday;
    private ObservableList<CashTransaction> cashTransactionsToday;

    private ObservableList<Billing> billings;
    private ObservableList<Revenue> revenues;
    private ObservableList<Expense> expenses;
    private ObservableList<CashTransaction> transactions;

    // View Controllers
    private ProjectionsViewController projectionsViewController;
    private RevenuesViewController revenuesViewController;
    private ExpensesViewController expensesViewController;
    private CashTransactionsViewController cashTransactionsViewController;
    private DailySummariesViewController dailySummariesViewController;

    private double mCashForwarded = 0.0;
    private double mRevenues = 0.0;
    private double mExpenses = 0.0;
    private double mCashIn = 0.0;
    private double mCashOut = 0.0;
    private double mCashBalance = 0.0;
    private double mReceivables = 0.0; // current month

    public DashboardPanel(MainWindow mainWindow, Database database) {
        super(DashboardPanel.class.getResource("dashboard.fxml"));
        this.mainWindow = mainWindow;
        this.database = database;
        disposables = new CompositeDisposable();

        billingController = new BillingController(database);
        revenueController = new RevenueController(database);
        expenseController = new ExpenseController(database);
        cashTransactionController = new CashTransactionController(database);
        dailySummaryController = new DailySummaryController(database);
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        setupProjectionsTab();
        setupRevenuesTab();
        setupExpensesTab();
        setupSummariesTab();
        setupTransactionsTab();

        tabPane.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
            onTabSelected(newVal.intValue());
        });
    }

    @Override
    public void onResume() {
        refresh(() -> onTabSelected(tabPane.getSelectionModel().getSelectedIndex()));
    }

    public void refresh(Runnable onNext) {
        showProgress("Fetching data...");
        disposables.add(Single.fromCallable(revenueController::getAll)
                .flatMap(revenuesList -> {
                    revenues = revenuesList;
                    return Single.fromCallable(expenseController::getAll);
                }).flatMap(expensesList -> {
                    expenses = expensesList;
                    return Single.fromCallable(cashTransactionController::getAll);
                }).observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(transactionsList -> {
                    transactions = transactionsList;
                    tally();
                    fetchDailySummary(onNext);
                }));
    }

    private void tally() {
        double cash = 0;
        double gcash = 0;
        double bank = 0;
        double cheque = 0;
        double palawan = 0;

        for (Revenue rev : revenues) {
            switch (rev.getMode()) {
                case Revenue.MODE_CASH -> cash += rev.getAmount();
                case Revenue.MODE_GCASH -> gcash += rev.getAmount();
                case Revenue.MODE_BANK_CASH -> bank += rev.getAmount();
                case Revenue.MODE_BANK_CHEQUE -> cheque += rev.getAmount();
                default -> palawan += rev.getAmount();
            }
        }

        double expCash = 0;
        double expGcash = 0;
        double expBank = 0;
        double expCheque = 0;
        double expPalawan = 0;

        for (Expense exp : expenses) {
            switch (exp.getMode()) {
                case Expense.MODE_CASH -> expCash += exp.getAmount();
                case Expense.MODE_GCASH -> expGcash += exp.getAmount();
                case Expense.MODE_BANK_CASH -> expBank += exp.getAmount();
                case Expense.MODE_BANK_CHEQUE -> expCheque += exp.getAmount();
                default ->  expPalawan += exp.getAmount();
            }
        }

        double ctCash = 0;
        double ctGcash = 0;
        double ctBank = 0;
        double ctCheque = 0;
        double ctPalawan = 0;

        for (CashTransaction ct : transactions) {
            double amount = ct.getAmount();
            boolean cashIn = ct.getType().equals(CashTransaction.TYPE_CASH_IN);
            switch (ct.getMode()) {
                case CashTransaction.MODE_CASH -> {
                    if (cashIn) ctCash += amount;
                    else ctCash -= amount;
                }
                case CashTransaction.MODE_GCASH -> {
                    if (cashIn) ctGcash += amount;
                    else ctGcash -= amount;
                }
                case CashTransaction.MODE_BANK_CASH -> {
                    if (cashIn) ctBank += amount;
                    else ctBank -= amount;
                }
                case CashTransaction.MODE_BANK_CHEQUE -> {
                    if (cashIn) ctCheque += amount;
                    else ctCheque -= amount;
                }
                default -> {
                    if (cashIn) ctPalawan += amount;
                    else ctPalawan -= amount;
                }
            }
        }

        lblCash.setText(ViewUtils.toStringMoneyFormat(cash + ctCash - expCash));
        lblGCash.setText(ViewUtils.toStringMoneyFormat(gcash + ctGcash - expGcash));
        lblBank.setText(ViewUtils.toStringMoneyFormat(bank + ctBank - expBank));
        lblCheque.setText(ViewUtils.toStringMoneyFormat(cheque + ctCheque - expCheque));
        lblPalawan.setText(ViewUtils.toStringMoneyFormat(palawan + ctPalawan - expPalawan));
    }

    private void fetchDailySummary(Runnable onNext) {
        // Check if there exist a DailySummary for today. If there is none, create and refresh summary.
        showProgress("Checking summary for today...");
        disposables.add(Single.fromCallable(() -> dailySummaryController.getByDate(LocalDate.now()))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(summary -> {
                    hideProgress();
                    mSummary = summary;
                    refreshSummary(onNext);
                }, err -> {
                    hideProgress();
                    if (err.toString().contains("NullPointer")) {
                        createSummary();
                    } else {
                        showErrorDialog("Database Error", "Error while checking summary.\n" + err);
                    }
                }));
    }

    private void onTabSelected(int index) {
        switch (index) {
            case 1 -> {
                if (revenuesViewController != null) {
                    revenuesViewController.refresh();
                }
            }
            case 2 -> {
                if (expensesViewController != null) {
                    expensesViewController.refresh();
                }
            }
            case 3 -> {
                if (cashTransactionsViewController != null) {
                    cashTransactionsViewController.refresh();
                }
            }
            case 4 -> {
                if (dailySummariesViewController != null) {
                    dailySummariesViewController.refresh();
                }
            }
            default -> {
                if (projectionsViewController != null) {
                    projectionsViewController.refresh();
                }
            }
        }
    }

    /**
     * Recalculate today's summary and save it to database.
     * @param onNext Task to execute after updating daily summary. Can be null.
     */
    private void refreshSummary(Runnable onNext) {
        showProgress("Fetching summary details...");
        disposables.add(Single.fromCallable(billingController::getAll)
                .flatMap(billingsList -> {
                    billings = billingsList;
                    return Single.fromCallable(expenseController::getAll);
                }).flatMap(expenses -> {
                    expensesToday = expenses;
                    return Single.fromCallable(revenueController::getRevenuesToday);
                }).flatMap(revenues -> {
                    revenuesToday = revenues;
                    return Single.fromCallable(cashTransactionController::getCashTransactionsToday);
                }).flatMap(cashTransactions -> {
                    cashTransactionsToday = cashTransactions;
                    return Single.fromCallable(dailySummaryController::getAll);
                }).flatMap(summaries -> {
                    // receivable billings this month
                    LocalDate now = LocalDate.now();
                    mReceivables = 0.0;
                    for (Billing billing : billings) {
                        if (billing.getStatus().equalsIgnoreCase("for payment")) {
                            LocalDate date = billing.getDueDate();
                            if (date.getMonthValue() == now.getMonthValue()) {
                                mReceivables += billing.getToPay();
                            }
                        }
                    }


                    // sort summaries
                    FXCollections.sort(summaries, Comparator.comparing(DailySummary::getDate));
                    if (!summaries.isEmpty() && summaries.size() > 1) {
                        mPrevSummary = summaries.get(summaries.size() - 2);
                    } else {
                        mPrevSummary = null;
                    }

                    mRevenues = 0;
                    for (Revenue r : revenuesToday) mRevenues += r.getAmount();

                    mExpenses = 0;
                    for (Expense e : expensesToday) mExpenses += e.getAmount();

                    mCashIn = 0;
                    mCashOut = 0;
                    for (CashTransaction ct : cashTransactionsToday) {
                        if (ct.getType().equals(CashTransaction.TYPE_CASH_IN)) mCashIn += ct.getAmount();
                        else mCashOut += ct.getAmount();
                    }

                    // mCashBalance = mSummary.getForwarded() + mRevenues - mExpenses + mCashIn - mCashOut;
                    mCashBalance = mSummary.getForwarded() + mRevenues + mCashIn - mExpenses;

                    mSummary.setRevenues(mRevenues);
                    mSummary.setExpenses(mExpenses);
                    mSummary.setCashIn(mCashIn);
                    mSummary.setCashOut(mCashOut);
                    mSummary.setBalance(mCashBalance);
                    return Single.fromCallable(() -> dailySummaryController.update(mSummary));
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to update daily summary.");
                    displaySummary();
                    if (onNext != null) onNext.run();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while updating summary details.\n" + err);
                }));
    }

    /**
     * Create new DailySummary entry for today and reload Dashboard.
     */
    private void createSummary() {
        showProgress("Creating daily summary entry...");
        disposables.add(Single.fromCallable(revenueController::getRevenuesToday)
                .flatMap(revenues -> {
                    revenuesToday = revenues;
                    return Single.fromCallable(expenseController::getExpensesToday);
                }).flatMap(expenses -> {
                    expensesToday = expenses;
                    return Single.fromCallable(cashTransactionController::getCashTransactionsToday);
                }).flatMap(cashTransactions -> {
                    cashTransactionsToday = cashTransactions;
                    return Single.fromCallable((dailySummaryController::getAll));
                }).flatMap(summaries -> {
                    // sort summaries
                    FXCollections.sort(summaries, Comparator.comparing(DailySummary::getDate));
                    mPrevSummary = summaries.isEmpty() ? null : summaries.getLast();
                    mCashForwarded = mPrevSummary == null ? 0 : mPrevSummary.getBalance();

                    mRevenues = 0;
                    for (Revenue r : revenuesToday) mRevenues += r.getAmount();

                    mExpenses = 0;
                    for (Expense e : expensesToday) mExpenses += e.getAmount();

                    mCashIn = 0;
                    for (CashTransaction ct : cashTransactionsToday) {
                        if (ct.getType().equals(CashTransaction.TYPE_CASH_IN)) mCashIn += ct.getAmount();
                        else mCashOut += ct.getAmount();
                    }

                    mCashBalance = (mCashForwarded + mRevenues + mCashIn) - mExpenses;
                    // mCashBalance = mCashForwarded + mRevenues - mExpenses + mCashIn - mCashOut;

                    DailySummary summary = new DailySummary();
                    summary.setDate(LocalDate.now());
                    summary.setForwarded(mCashForwarded);
                    summary.setRevenues(mRevenues);
                    summary.setExpenses(mExpenses);
                    summary.setCashIn(mCashIn);
                    summary.setCashOut(mCashOut);
                    summary.setBalance(mCashBalance);
                    return Single.fromCallable(() -> dailySummaryController.insert(summary));
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to create daily summary entry.");
                    onResume();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while creating daily summary entry.\n" + err);
                }));
    }

    @Override
    public void onPause() {

    }

    private void displaySummary() {
        if (mSummary != null) {
            lblForwarded.setText(ViewUtils.toStringMoneyFormat(mSummary.getForwarded()));
            lblRevenues.setText(ViewUtils.toStringMoneyFormat(mSummary.getRevenues()));
            lblExpenses.setText(ViewUtils.toStringMoneyFormat(mSummary.getExpenses()));
            lblCashIn.setText(ViewUtils.toStringMoneyFormat(mSummary.getCashIn()));
            lblBalances.setText(ViewUtils.toStringMoneyFormat(mSummary.getBalance()));
            lblReceivables.setText(ViewUtils.toStringMoneyFormat(mReceivables));

            // clear styles first
            lblRevenues.getStyleClass().removeAll("positive-up", "negative-down");
            lblExpenses.getStyleClass().removeAll("negative-up", "positive-down");
            lblCashIn.getStyleClass().removeAll("negative-up", "positive-down");
            lblBalances.getStyleClass().removeAll("positive-up", "negative-down");

            if (mPrevSummary != null) {
                int iconSize = 16;
                if (mPrevSummary.getRevenues() > mSummary.getRevenues()) {
                    lblRevenues.setGraphic(new ArrowDownIcon(iconSize));
                    lblRevenues.getStyleClass().add("negative-down");
                } else if (mPrevSummary.getRevenues() < mSummary.getRevenues()) {
                    lblRevenues.setGraphic(new ArrowUpIcon(iconSize));
                    lblRevenues.getStyleClass().add("positive-up");
                } else {
                    lblRevenues.setGraphic(null);
                }

                if (mPrevSummary.getExpenses() > mSummary.getExpenses()) {
                    lblExpenses.setGraphic(new ArrowDownIcon(iconSize));
                    lblExpenses.getStyleClass().add("positive-down");
                } else if (mPrevSummary.getExpenses() < mSummary.getExpenses()) {
                    lblExpenses.setGraphic(new ArrowUpIcon(iconSize));
                    lblExpenses.getStyleClass().add("negative-up");
                } else {
                    lblExpenses.setGraphic(null);
                }

                if (mPrevSummary.getCashIn() > mSummary.getCashIn()) {
                    lblCashIn.setGraphic(new ArrowDownIcon(iconSize));
                    lblCashIn.getStyleClass().add("negative-down");
                } else if (mPrevSummary.getCashIn() < mSummary.getCashIn()) {
                    lblCashIn.setGraphic(new ArrowUpIcon(iconSize));
                    lblCashIn.getStyleClass().add("positive-up");
                } else {
                    lblCashIn.setGraphic(null);
                }

                if (mPrevSummary.getBalance() > mSummary.getBalance()) {
                    lblBalances.setGraphic(new ArrowDownIcon(iconSize));
                    lblBalances.getStyleClass().add("negative-down");
                } else if (mPrevSummary.getBalance() < mSummary.getBalance()) {
                    lblBalances.setGraphic(new ArrowUpIcon(iconSize));
                    lblBalances.getStyleClass().add("positive-up");
                } else {
                    lblBalances.setGraphic(null);
                }
            }
        }
    }

    private void setupIcons() {
        lblCash.setGraphic(new PesoIcon(18));
        lblGCash.setGraphic(new PesoIcon(18));
        lblBank.setGraphic(new PesoIcon(18));
        lblCheque.setGraphic(new PesoIcon(18));
        lblPalawan.setGraphic(new PesoIcon(18));

        tabProjections.setGraphic(new TrendingUpIcon(14));
        tabRevenues.setGraphic(new PesoIcon(14));
        tabExpenses.setGraphic(new PesoIcon(14));
        tabTransactions.setGraphic(new PesoIcon(14));
        tabSummaries.setGraphic(new FileTextIcon(14));
    }

    private void setupProjectionsTab() {
        projectionsViewController = new ProjectionsViewController(this, mainWindow, database,
                expensesPieChart, operationalBarChart, nonOperationalBarChart, revenuesBarChart, expensesRevenuesPieChart,
                monthlyLineChart, dailyLineChart);
        projectionsViewController.init();
    }

    private void setupRevenuesTab() {
        colRevenueTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colRevenueDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRevenueAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colRevenueType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colRevenueDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colRevenueMode.setCellValueFactory(new PropertyValueFactory<>("mode"));
        colRevenueRef.setCellValueFactory(new PropertyValueFactory<>("reference"));
        colRevenueAttachment.setCellValueFactory(new PropertyValueFactory<>("attachment"));

        colRevenueTag.setCellFactory(col -> new TagTableCell<>());
        colRevenueAmount.setCellFactory(col -> new AmountTableCell<>());
        colRevenueDate.setCellFactory(col -> new DateTableCell<>());
        colRevenueAttachment.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) {
                    setText("");
                    setGraphic(null);
                } else {
                    if (s.isBlank()) setText("No");
                    else setText("Yes");
                }
            }
        });

        revenuesViewController = new RevenuesViewController(this, mainWindow, database, btnAddRevenue,
                btnEditRevenue, btnDeleteRevenue, btnExportRevenues, cbRevenueModes, lblRevCash, lblRevGCash,
                lblRevBank, lblRevCheque, lblRevPalawan, revenuesTable);
        revenuesViewController.init();
    }

    private void setupExpensesTab() {
        colExpenseTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colExpenseDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colExpenseAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colExpenseCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colExpenseType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colExpenseDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colExpenseMode.setCellValueFactory(new PropertyValueFactory<>("mode"));
        colExpenseReference.setCellValueFactory(new PropertyValueFactory<>("ref"));
        colExpenseAttachment.setCellValueFactory(new PropertyValueFactory<>("attachment"));

        colExpenseTag.setCellFactory(col -> new TagTableCell<>());
        colExpenseDate.setCellFactory(col -> new DateTableCell<>());
        colExpenseAmount.setCellFactory(col -> new AmountTableCell<>());
        colExpenseAttachment.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) {
                    setText("");
                    setGraphic(null);
                } else {
                    if (s.isBlank()) setText("No");
                    else setText("Yes");
                }
            }
        });

        expensesViewController = new ExpensesViewController(this, mainWindow, database,
                btnAddExpense, btnEditExpense, btnDeleteExpense, btnExportExpenses, cbExpensesModes, lblExpCash,
                lblExpGCash, lblExpBank, lblExpCheque, lblExpPalawan, expensesTable);
        expensesViewController.init();
    }

    private void setupTransactionsTab() {
        colTransactionsTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colTransactionsDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTransactionsType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colTransactionsAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colTransactionsDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colTransactionsMode.setCellValueFactory(new PropertyValueFactory<>("mode"));
        colTransactionsReference.setCellValueFactory(new PropertyValueFactory<>("reference"));
        colTransactionsAttachment.setCellValueFactory(new PropertyValueFactory<>("attachment"));

        colTransactionsTag.setCellFactory(col -> new TagTableCell<>());
        colTransactionsDate.setCellFactory(col -> new DateTableCell<>());
        colTransactionsAmount.setCellFactory(col -> new AmountTableCell<>());
        colTransactionsAttachment.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) {
                    setText("");
                    setGraphic(null);
                } else {
                    setText(s.isBlank() ? "No" : "Yes");
                }
            }
        });

        cashTransactionsViewController = new CashTransactionsViewController(this, mainWindow, database,
                btnAddTransaction, btnEditTransaction, btnDeleteTransaction, btnTransfer, btnExportTransactions, cbTransactionsMode,
                lblCtCash, lblCtGCash, lblCtBank, lblCtCheque, lblCtPalawan, transactionsTable);
        cashTransactionsViewController.init();
    }

    private void setupSummariesTab() {
        colSummaryTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colSummaryDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSummaryForwarded.setCellValueFactory(new PropertyValueFactory<>("forwarded"));
        colSummaryRevenues.setCellValueFactory(new PropertyValueFactory<>("revenues"));
        colSummaryExpenses.setCellValueFactory(new PropertyValueFactory<>("expenses"));
        colSummaryCashIn.setCellValueFactory(new PropertyValueFactory<>("cashIn"));
        colSummaryCashOut.setCellValueFactory(new PropertyValueFactory<>("cashOut"));
        colSummaryBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        colSummaryTag.setCellFactory(col -> new TagTableCell<>());
        colSummaryDate.setCellFactory(col -> new DateTableCell<>());
        colSummaryForwarded.setCellFactory(col -> new AmountTableCell<>());
        colSummaryRevenues.setCellFactory(col -> new AmountTableCell<>());
        colSummaryExpenses.setCellFactory(col -> new AmountTableCell<>());
        colSummaryCashIn.setCellFactory(col -> new AmountTableCell<>());
        colSummaryCashOut.setCellFactory(col -> new AmountTableCell<>());
        colSummaryBalance.setCellFactory(col -> new AmountTableCell<>());

        dailySummariesViewController = new DailySummariesViewController(this, mainWindow, database,
                btnExportSummaries, btnRecalculate, summariesTable);
        dailySummariesViewController.init();
    }

    private void showProgress(String text) {
        mainWindow.showProgress(-1, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    @Override
    public void onDispose() {
        if (revenuesViewController != null) revenuesViewController.dispose();
        if (expensesViewController != null) expensesViewController.dispose();
        if (cashTransactionsViewController != null) cashTransactionsViewController.dispose();
        if (dailySummariesViewController != null) dailySummariesViewController.dispose();
        disposables.dispose();
    }
}
