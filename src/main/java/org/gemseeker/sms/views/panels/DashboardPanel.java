package org.gemseeker.sms.views.panels;

import io.github.msufred.feathericons.*;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gemseeker.sms.data.DailySummary;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Expense;
import org.gemseeker.sms.data.Revenue;
import org.gemseeker.sms.data.controllers.DailySummaryController;
import org.gemseeker.sms.data.controllers.ExpenseController;
import org.gemseeker.sms.data.controllers.RevenueController;
import org.gemseeker.sms.views.*;
import org.gemseeker.sms.views.cells.DateTableCell;
import org.gemseeker.sms.views.cells.TagTableCell;
import org.gemseeker.sms.views.icons.PesoIcon;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.Optional;

public class DashboardPanel extends AbstractPanel {

    // Summary
    @FXML private Label lblForwarded;
    @FXML private Label lblRevenues;
    @FXML private Label lblExpenses;
    @FXML private Label lblBalances;

    @FXML private TabPane tabPane;
    @FXML private Tab tabRecentSales;
    @FXML private Tab tabProjections;
    @FXML private Tab tabRevenues;
    @FXML private Tab tabExpenses;
    @FXML private Tab tabSummaries;

    // Projections
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
    @FXML private TableView<Revenue> revenuesTable;
    @FXML private TableColumn<Revenue, String> colRevenueTag;
    @FXML private TableColumn<Revenue, String> colRevenueType;
    @FXML private TableColumn<Revenue, String> colRevenueDescription;
    @FXML private TableColumn<Revenue, Double> colRevenueAmount;
    @FXML private TableColumn<Revenue, LocalDate> colRevenueDate;

    // Expenses
    @FXML private Button btnAddExpense;
    @FXML private Button btnEditExpense;
    @FXML private Button btnDeleteExpense;
    @FXML private TableView<Expense> expensesTable;
    @FXML private TableColumn<Expense, String> colExpenseTag;
    @FXML private TableColumn<Expense, String> colExpenseType;
    @FXML private TableColumn<Expense, String> colExpenseDescription;
    @FXML private TableColumn<Expense, Double> colExpenseAmount;
    @FXML private TableColumn<Expense, LocalDate> colExpenseDate;

    // Summaries
    @FXML private TableView<DailySummary> summariesTable;
    @FXML private TableColumn<DailySummary, String> colSummaryTag;
    @FXML private TableColumn<DailySummary, LocalDate> colSummaryDate;
    @FXML private TableColumn<DailySummary, Double> colSummaryForwarded;
    @FXML private TableColumn<DailySummary, Double> colSummaryRevenues;
    @FXML private TableColumn<DailySummary, Double> colSummaryExpenses;
    @FXML private TableColumn<DailySummary, Double> colSummaryBalance;

    private FilteredList<Revenue> revenueList;
    private FilteredList<Expense> expenseList;
    private FilteredList<DailySummary> summariesList;
    private final SimpleObjectProperty<Revenue> selectedRevenueItem = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Expense> selectedExpenseItem = new SimpleObjectProperty<>();

    private final MainWindow mainWindow;
    private final Database database;
    private final CompositeDisposable disposables;

    // ModelControllers
    private final RevenueController revenueController;
    private final ExpenseController expenseController;
    private final DailySummaryController dailySummaryController;

    // Windows
    private AddRevenueWindow addRevenueWindow;
    private EditRevenueWindow editRevenueWindow;
    private AddExpenseWindow addExpenseWindow;
    private EditExpenseWindow editExpenseWindow;

    // Summary Values
    private DailySummary mSummary;
    private ObservableList<Revenue> prevRevenuesList;
    private ObservableList<Expense> prevExpensesList;
    private ObservableList<Revenue> revenuesToday;
    private ObservableList<Expense> expensesToday;
    private ObservableList<Revenue> allRevenues;
    private ObservableList<Expense> allExpenses;

    private double mCashForwarded = 0.0;
    private double mRevenues = 0.0;
    private double mExpenses = 0.0;
    private double mCashBalance = 0.0;

    public DashboardPanel(MainWindow mainWindow, Database database) {
        super(DashboardPanel.class.getResource("dashboard.fxml"));
        this.mainWindow = mainWindow;
        this.database = database;
        disposables = new CompositeDisposable();

        revenueController = new RevenueController(database);
        expenseController = new ExpenseController(database);
        dailySummaryController = new DailySummaryController(database);
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        setupRevenuesTab();
        setupExpensesTab();
        setupSummariesTab();

        tabPane.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
                switch (newVal.getText()) {
                    case "Sales Projections" -> refreshProjections();
                    case "Revenues" -> refreshRevenues();
                    case "Expenses" -> refreshExpenses();
                    case "Summaries" -> refreshDailySummaries();
                    default -> System.out.println("Recent Sales");
                }
            }
        });
    }

    private void setupIcons() {
        tabRecentSales.setGraphic(new ShoppingCartIcon(14));
        tabProjections.setGraphic(new TrendingUpIcon(14));
        tabRevenues.setGraphic(new PesoIcon(14));
        tabExpenses.setGraphic(new PesoIcon(14));
        tabSummaries.setGraphic(new FileTextIcon(14));

        btnAddRevenue.setGraphic(new PlusIcon(14));
        btnEditRevenue.setGraphic(new Edit2Icon(14));
        btnDeleteRevenue.setGraphic(new TrashIcon(14));

        btnAddExpense.setGraphic(new PlusIcon(14));
        btnEditExpense.setGraphic(new Edit2Icon(14));
        btnDeleteExpense.setGraphic(new TrashIcon(14));
    }

    private void setupRevenuesTab() {
        btnAddRevenue.setOnAction(evt -> addRevenue());
        btnEditRevenue.setOnAction(evt -> editRevenue());
        btnDeleteRevenue.setOnAction(evt -> deleteRevenue());

        colRevenueTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colRevenueTag.setCellFactory(col -> new TagTableCell<>());
        colRevenueType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colRevenueDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colRevenueAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colRevenueDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRevenueDate.setCellFactory(col -> new DateTableCell<>());

        MenuItem mAdd = new MenuItem("Add");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addRevenue());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editRevenue());

        Menu mTag = new Menu("Change Tag");
        mTag.setGraphic(new CircleIcon(12));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.setGraphic(icon);
            item.setOnAction(evt -> updateRevenueTag(tag));
            mTag.getItems().add(item);
        });

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteRevenue());

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mTag, new SeparatorMenuItem(), mDelete);
        revenuesTable.setContextMenu(cm);

        selectedRevenueItem.bind(revenuesTable.getSelectionModel().selectedItemProperty());
    }

    private void setupExpensesTab() {
        btnAddExpense.setOnAction(evt -> addExpense());
        btnEditExpense.setOnAction(evt -> editExpense());
        btnDeleteExpense.setOnAction(evt -> deleteExpense());

        colExpenseTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colExpenseTag.setCellFactory(col -> new TagTableCell<>());
        colExpenseType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colExpenseDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colExpenseAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colExpenseDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colExpenseDate.setCellFactory(col -> new DateTableCell<>());

        MenuItem mAdd = new MenuItem("Add");
        mAdd.setGraphic(new PlusIcon(12));
        mAdd.setOnAction(evt -> addExpense());

        MenuItem mEdit = new MenuItem("Edit");
        mEdit.setGraphic(new Edit2Icon(12));
        mEdit.setOnAction(evt -> editExpense());

        Menu mTag = new Menu("Change Tag");
        mTag.setGraphic(new CircleIcon(12));
        ViewUtils.getTags().forEach((tag, icon) -> {
            MenuItem item = new MenuItem(ViewUtils.capitalize(tag));
            item.setGraphic(icon);
            item.setOnAction(evt -> updateExpenseTag(tag));
            mTag.getItems().add(item);
        });

        MenuItem mDelete = new MenuItem("Delete");
        mDelete.setGraphic(new TrashIcon(12));
        mDelete.setOnAction(evt -> deleteExpense());

        ContextMenu cm = new ContextMenu(mAdd, mEdit, mTag, new SeparatorMenuItem(), mDelete);
        expensesTable.setContextMenu(cm);

        selectedExpenseItem.bind(expensesTable.getSelectionModel().selectedItemProperty());
    }

    private void setupSummariesTab() {
        colSummaryTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        colSummaryTag.setCellFactory(col -> new TagTableCell<>());
        colSummaryDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSummaryDate.setCellFactory(col -> new DateTableCell<>());
        colSummaryForwarded.setCellValueFactory(new PropertyValueFactory<>("forwarded"));
        colSummaryRevenues.setCellValueFactory(new PropertyValueFactory<>("revenues"));
        colSummaryExpenses.setCellValueFactory(new PropertyValueFactory<>("expenses"));
        colSummaryBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    @Override
    public void onResume() {
        // Check if there exist a DailySummary for today. If there is none, create and refresh summary.
        showProgress("Checking summary for today...");
        disposables.add(Single.fromCallable(() -> dailySummaryController.getByDate(LocalDate.now()))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(summary -> {
                    hideProgress();
                    mSummary = summary;
                    refreshSummary(this::refreshRevenues); // TODO change to recent sales
                }, err -> {
                    hideProgress();
                    if (err.toString().contains("NullPointer")) {
                        createSummary();
                    } else {
                        showErrorDialog("Database Error", "Error while checking summary.\n" + err);
                    }
                }));
    }

    /**
     * Create new DailySummary entry for today and reload Dashboard.
     */
    private void createSummary() {
        showProgress("Creating daily summary entry...");
        disposables.add(Single.fromCallable(revenueController::getPrevRevenues) // get last day revenues
                .flatMap(revenues -> {
                    prevRevenuesList = revenues;
                    return Single.fromCallable(expenseController::getPrevExpenses); // get last day expenses
                }).flatMap(expenses -> {
                    prevExpensesList = expenses;
                    return Single.fromCallable(revenueController::getRevenuesToday); // get today's revenues
                }).flatMap(revenues -> {
                    revenuesToday = revenues;
                    return Single.fromCallable(expenseController::getExpensesToday); // get today's expenses
                }).flatMap(expenses -> { // calculate and create DailySummary entry for today
                    expensesToday = expenses;

                    double prevRevenues = 0;
                    for (Revenue r : prevRevenuesList) prevRevenues += r.getAmount();
                    double prevExpenses = 0;
                    for (Expense e : prevExpensesList) prevExpenses += e.getAmount();
                    mCashForwarded = prevRevenues - prevExpenses;

                    mRevenues = 0;
                    for (Revenue r : revenuesToday) mRevenues += r.getAmount();
                    mExpenses = 0;
                    for (Expense e : expensesToday) mExpenses += e.getAmount();
                    mCashBalance = mCashForwarded + mRevenues - mExpenses;

                    DailySummary summary = new DailySummary();
                    summary.setDate(LocalDate.now());
                    summary.setForwarded(mCashForwarded);
                    summary.setRevenues(mRevenues);
                    summary.setExpenses(mExpenses);
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

    /**
     * Recalculate today's summary and save it to database.
     * @param onNext Task to execute after updating daily summary. Can be null.
     */
    private void refreshSummary(Runnable onNext) {
        showProgress("Fetching summary details...");
        disposables.add(Single.fromCallable(expenseController::getExpensesToday)
                .flatMap(expenses -> {
                    expensesToday = expenses;
                    return Single.fromCallable(revenueController::getRevenuesToday);
                }).flatMap(revenues -> {
                    revenuesToday = revenues;
                    mRevenues = 0;
                    for (Revenue r : revenuesToday) mRevenues += r.getAmount();
                    mExpenses = 0;
                    for (Expense e : expensesToday) mExpenses += e.getAmount();
                    mCashBalance = mSummary.getForwarded() + mRevenues - mExpenses;

                    mSummary.setRevenues(mRevenues);
                    mSummary.setExpenses(mExpenses);
                    mSummary.setBalance(mCashBalance);
                    return Single.fromCallable(() -> dailySummaryController.update(mSummary));
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to update daily summary.");
                    lblForwarded.setText(ViewUtils.toStringMoneyFormat(mSummary.getForwarded()));
                    lblRevenues.setText(ViewUtils.toStringMoneyFormat(mSummary.getRevenues()));
                    lblExpenses.setText(ViewUtils.toStringMoneyFormat(mSummary.getExpenses()));
                    lblBalances.setText(ViewUtils.toStringMoneyFormat(mSummary.getBalance()));
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while updating summary details.\n" + err);
                }));
    }

    private void refreshDailySummaries() {
        showProgress("Retrieving daily summary entries...");
        disposables.add(Single.fromCallable(dailySummaryController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    summariesList = new FilteredList<>(list);
                    summariesTable.setItems(summariesList);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving daily summary entries.\n" + err);
                }));
    }

    @Override
    public void onPause() {

    }

    private void refreshProjections() {
        showProgress("Retrieving data...");
        disposables.add(Single.fromCallable(revenueController::getAll)
                .flatMap(revenues -> {
                    allRevenues = revenues;
                    return Single.fromCallable(expenseController::getAll);
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(expenses -> {
                    hideProgress();
                    allExpenses = expenses;
                    displayProjections();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving data.\n" + err);
                }));
    }

    private void displayProjections() {
        if (allRevenues != null && allExpenses != null) {
            // MONTHLY PROJECTIONS

            monthlyXAxis.getCategories().addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
            LocalDate now = LocalDate.now();

            XYChart.Series<String, Number> monthlyRevenues = new XYChart.Series<>();
            XYChart.Series<String, Number> monthlyExpenses = new XYChart.Series<>();
            XYChart.Series<String, Number> monthlyBalance = new XYChart.Series<>();
            monthlyRevenues.setName("Revenues");
            monthlyExpenses.setName("Expenses");
            monthlyBalance.setName("Cash Balance");

            for (int month = 1; month <= 12; month++) {
                double revenues = 0;
                double expenses = 0;

                for (Revenue r : allRevenues) {
                    if (r.getDate().getMonthValue() == month && r.getDate().getYear() == now.getYear()) {
                        revenues += r.getAmount();
                    }
                }

                for (Expense e : allExpenses) {
                    if (e.getDate().getMonthValue() == month && e.getDate().getYear() == now.getYear()) {
                        expenses += e.getAmount();
                    }
                }

                double balance = revenues - expenses;
                monthlyRevenues.getData().add(new XYChart.Data<>(ViewUtils.shortMonthStringValue(month), revenues));
                monthlyExpenses.getData().add(new XYChart.Data<>(ViewUtils.shortMonthStringValue(month), expenses));
                monthlyBalance.getData().add(new XYChart.Data<>(ViewUtils.shortMonthStringValue(month), balance));
            }

            monthlyLineChart.getData().clear();
            monthlyLineChart.getData().addAll(monthlyRevenues, monthlyExpenses, monthlyBalance);

            // DAILY PROJECTIONS
            XYChart.Series<String, Number> dailyRevenues = new XYChart.Series<>();
            XYChart.Series<String, Number> dailyExpenses = new XYChart.Series<>();
            XYChart.Series<String, Number> dailyBalance = new XYChart.Series<>();
            dailyRevenues.setName("Revenues");
            dailyExpenses.setName("Expenses");
            dailyBalance.setName("Cash Balance");

            YearMonth ym = YearMonth.now();
            for (int d = 1; d <= ym.atEndOfMonth().getDayOfMonth(); d++) {
                dailyXAxis.getCategories().add(d + "");
                double revenues = 0;
                double expenses = 0;

                for (Revenue r : allRevenues) {
                    if (r.getDate().getDayOfMonth() == d && r.getDate().getYear() == ym.getYear()) {
                        revenues += r.getAmount();
                    }
                }

                for (Expense e : allExpenses) {
                    if (e.getDate().getDayOfMonth() == d && e.getDate().getYear() == ym.getYear()) {
                        expenses += e.getAmount();
                    }
                }

                double balance = revenues - expenses;
                dailyRevenues.getData().add(new XYChart.Data<>(d + "", revenues));
                dailyExpenses.getData().add(new XYChart.Data<>(d + "", expenses));
                dailyBalance.getData().add(new XYChart.Data<>(d + "", balance));
            }

            dailyLineChart.getData().clear();
            dailyLineChart.getData().addAll(dailyRevenues, dailyExpenses, dailyBalance);
        }
    }

    private void refreshRevenues() {
        showProgress("Retrieving Revenue entries...");
        disposables.add(Single.fromCallable(revenueController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    revenueList = new FilteredList<>(list);
                    revenuesTable.setItems(revenueList);
                    refreshSummary(null);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Revenue entries.\n" + err);
                }));
    }

    private void refreshExpenses() {
        showProgress("Retrieving Expense entries...");
        disposables.add(Single.fromCallable(expenseController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    expenseList = new FilteredList<>(list);
                    expensesTable.setItems(expenseList);
                    refreshSummary(null);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Expense entries.\n" + err);
                }));
    }

    // Revenue
    private void addRevenue() {
        if (addRevenueWindow == null) addRevenueWindow = new AddRevenueWindow(database, mainWindow.getStage());
        addRevenueWindow.showAndWait();
        refreshRevenues();
    }

    private void editRevenue() {
        if (selectedRevenueItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Revenue entry. Try again.");
        } else {
            if (editRevenueWindow == null) editRevenueWindow = new EditRevenueWindow(database, mainWindow.getStage());
            editRevenueWindow.showAndWait(selectedRevenueItem.get());
            refreshRevenues();
        }
    }

    private void deleteRevenue() {
        if (selectedRevenueItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Revenue entry. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete Revenue",
                    "Are you sure you want to delete this Revenue entry?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) {
                deleteRevenue(selectedRevenueItem.get().getId());
            }
        }
    }

    private void deleteRevenue(int id) {
        showProgress("Deleting Revenue entry...");
        disposables.add(Single.fromCallable(() -> revenueController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to delete Revenue entry.");
                    refreshRevenues();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting Revenue entry.\n" + err);
                }));
    }

    private void updateRevenueTag(String tag) {
        if (selectedRevenueItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Revenue entry. Try again.");
        } else {
            showProgress("Updating Revenue entry...");
            disposables.add(Single.fromCallable(() -> revenueController.update(selectedRevenueItem.get().getId(),
                    "tag", tag))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                        hideProgress();
                        if (!success) showWarningDialog("Failed", "Failed to update Revenue entry.");
                        refreshRevenues();
                    }, err -> {
                        hideProgress();
                        showErrorDialog("Database Error", "Error while updating Revenue entry.");
                    }));
        }
    }

    // Expense
    private void addExpense() {
        if (addExpenseWindow == null) addExpenseWindow = new AddExpenseWindow(database, mainWindow.getStage());
        addExpenseWindow.showAndWait();
        refreshExpenses();
    }

    private void editExpense() {
        if (selectedExpenseItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Expense entry. Try again.");
        } else {
            if (editExpenseWindow == null) editExpenseWindow = new EditExpenseWindow(database, mainWindow.getStage());
            editExpenseWindow.showAndWait(selectedExpenseItem.get());
            refreshExpenses();
        }
    }

    private void deleteExpense() {
        if (selectedExpenseItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Expense entry. Try again.");
        } else {
            Optional<ButtonType> result = showConfirmDialog("Delete Expense",
                    "Are you sure you want to delete this Expense entry?",
                    ButtonType.YES, ButtonType.NO);
            if (result.isPresent() && result.get() == ButtonType.YES) {
                deleteExpense(selectedExpenseItem.get().getId());
            }
        }
    }

    private void deleteExpense(int id) {
        showProgress("Deleting Expense entry...");
        disposables.add(Single.fromCallable(() -> expenseController.delete(id))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to delete Expense entry.");
                    refreshExpenses();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while deleting Expense entry.\n" + err);
                }));
    }

    private void updateExpenseTag(String tag) {
        if (selectedExpenseItem.get() == null) {
            showWarningDialog("Invalid Action", "No selected Expense entry. Try again.");
        } else {
            showProgress("Updating Expense entry...");
            disposables.add(Single.fromCallable(() -> expenseController.update(selectedExpenseItem.get().getId(),
                    "tag", tag))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                        hideProgress();
                        if (!success) showWarningDialog("Failed", "Failed to update Expense entry.");
                        refreshExpenses();
                    }, err -> {
                        hideProgress();
                        showErrorDialog("Database Error", "Error while updating Expense entry.");
                    }));
        }
    }

    private void showProgress(String text) {
        mainWindow.showProgress(-1, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    @Override
    public void onDispose() {
        if (addRevenueWindow != null) addRevenueWindow.dispose();
        if (editRevenueWindow != null) editRevenueWindow.dispose();
        if (addExpenseWindow != null) addExpenseWindow.dispose();
        if (editExpenseWindow != null) editExpenseWindow.dispose();
        disposables.dispose();
    }
}
