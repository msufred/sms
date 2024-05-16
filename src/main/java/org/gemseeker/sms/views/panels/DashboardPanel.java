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

    private FilteredList<Revenue> revenueList;
    private FilteredList<Expense> expenseList;
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
    private DailySummary prevSummary;
    private ObservableList<Revenue> prevRevenues;
    private ObservableList<Expense> prevExpenses;
    private ObservableList<Revenue> revenuesToday;
    private ObservableList<Expense> expensesToday;

    private double mPrevCashForwared = 0.0;
    private double mPrevRevenues = 0.0;
    private double mPrevExpenses = 0.0;
    private double mPrevCashBalance = 0.0;
    private double mCashForwared = 0.0;
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

        tabPane.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
                switch (newVal.getText()) {
                    case "Sales Projections" -> System.out.println("Sales Projections");
                    case "Revenues" -> refreshRevenues();
                    case "Expenses" -> refreshExpenses();
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

    @Override
    public void onResume() {
        if (prevSummary == null) {
            fetchOrCreatePrevSummary();
        } else {
            refreshSummary(this::refreshExpenses); // TODO change to Recent Sales
        }
    }

    private void fetchOrCreatePrevSummary() {
        showProgress("Fetching previous summary details...");
        LocalDate prev = LocalDate.now().minusDays(1);
        disposables.add(Single.fromCallable(() -> dailySummaryController.getByDate(prev))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(summary -> {
                    hideProgress();
                    prevSummary = summary;
                    refreshSummary(null);
                }, err -> {
                    if (err.toString().contains("NullPointer")) {
                        createPrevSummaryEntry();
                    } else {
                        showErrorDialog("Database Error", "Error while retrieving previous summary.\n" + err);
                    }
                }));
    }

    private void createPrevSummaryEntry() {
        showProgress("Fetching previous summary details...");
        disposables.add(Single.fromCallable(expenseController::getPrevExpenses)
                .flatMap(expenses -> {
                    prevExpenses = expenses;
                    return Single.fromCallable(revenueController::getPrevRevenues);
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(revenues -> {
                    hideProgress();
                    prevRevenues = revenues;
                    savePrevSummary();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while fetching previous summary.\n" + err);
                }));
    }

    private void savePrevSummary() {
        if (prevRevenues != null) {
            mPrevRevenues = 0;
            for (Revenue r : prevRevenues) mPrevRevenues += r.getAmount();
        }
        if (prevExpenses != null) {
            mPrevExpenses = 0;
            for (Expense e : prevExpenses) mPrevExpenses += e.getAmount();
        }

        mPrevCashBalance = mPrevRevenues - mPrevExpenses;
        mCashForwared = mPrevCashBalance;

        prevSummary = new DailySummary();
        prevSummary.setDate(LocalDate.now().minusDays(1));
        prevSummary.setForwarded(0);
        prevSummary.setRevenues(mPrevRevenues);
        prevSummary.setExpenses(mPrevExpenses);
        prevSummary.setBalance(mPrevCashBalance);

        showProgress("Saving previous summary...");
        disposables.add(Single.fromCallable(() -> dailySummaryController.insert(prevSummary))
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
                    hideProgress();
                    if (!success) showWarningDialog("Failed", "Failed to save previous summary.");
                    refreshSummary(null);
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while saving previous summary.\n" + err);
                }));
    }

    private void refreshSummary(Runnable onNext) {
        showProgress("Fetching summary details...");
        disposables.add(Single.fromCallable(expenseController::getExpensesToday)
                .flatMap(expenses -> {
                    expensesToday = expenses;
                    return Single.fromCallable(revenueController::getRevenuesToday);
                }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(revenues -> {
                    hideProgress();
                    revenuesToday = revenues;
                    displaySummary();
                    if (onNext != null) onNext.run();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving summary details.\n" + err);
                }));
    }

    private void displaySummary() {
        // Total Revenues
        if (revenuesToday != null) {
            mRevenues = 0;
            for (Revenue r : revenuesToday) mRevenues += r.getAmount();
        }
        lblRevenues.setText(ViewUtils.toStringMoneyFormat(mRevenues));

        // Total Expenses
        if (expensesToday != null) {
            mExpenses = 0;
            for (Expense e : expensesToday) mExpenses += e.getAmount();
        }
        lblExpenses.setText(ViewUtils.toStringMoneyFormat(mExpenses));

        // Cash Balance
        mCashBalance = mCashForwared + mRevenues - mExpenses;
        lblBalances.setText(ViewUtils.toStringMoneyFormat(mCashBalance));
    }

    @Override
    public void onPause() {

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
