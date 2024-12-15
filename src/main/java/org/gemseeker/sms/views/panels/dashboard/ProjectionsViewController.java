package org.gemseeker.sms.views.panels.dashboard;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.CashTransactionController;
import org.gemseeker.sms.data.controllers.DailySummaryController;
import org.gemseeker.sms.data.controllers.ExpenseController;
import org.gemseeker.sms.data.controllers.RevenueController;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.ViewUtils;
import org.gemseeker.sms.views.panels.DashboardPanel;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.HashMap;

public class ProjectionsViewController {

    private final PieChart expensesPieChart;
    private final BarChart<String, Number> operationalBarChart;
    private final BarChart<String, Number> nonOperationalBarChart;
    private final BarChart<String, Number> revenuesBarChart;
    private final PieChart expensesRevenuesPieChart;
    private final LineChart<String, Number> monthlyProjectionLineChart;
    private final LineChart<String, Number> dailyProjectionLineChart;

    private final DashboardPanel dashboardPanel;
    private final MainWindow mainWindow;
    private final Database database;
    private final CompositeDisposable disposables;

    private final ExpenseController expenseController;
    private final RevenueController revenueController;
    private final CashTransactionController cashTransactionController;
    private final DailySummaryController dailySummaryController;

    private ObservableList<Revenue> allRevenues;
    private ObservableList<Expense> allExpenses;
    private ObservableList<CashTransaction> allCashTransactions;
    private ObservableList<DailySummary> allDailySummaries;

    public ProjectionsViewController(DashboardPanel dashboardPanel, MainWindow mainWindow, Database database,
                                     PieChart expensesPieChart, BarChart<String, Number> operationalBarChart,
                                     BarChart<String, Number> nonOperationalBarChart,
                                     BarChart<String, Number> revenuesBarChart,
                                     PieChart expensesRevenuesPieChart,
                                     LineChart<String, Number> monthlyProjectionLineChart,
                                     LineChart<String, Number> dailyProjectionLineChart) {
        this.dashboardPanel = dashboardPanel;
        this.mainWindow = mainWindow;
        this.database = database;
        this.disposables = new CompositeDisposable();

        this.expensesPieChart = expensesPieChart;
        this.operationalBarChart = operationalBarChart;
        this.nonOperationalBarChart = nonOperationalBarChart;
        this.revenuesBarChart = revenuesBarChart;
        this.expensesRevenuesPieChart = expensesRevenuesPieChart;
        this.monthlyProjectionLineChart = monthlyProjectionLineChart;
        this.dailyProjectionLineChart = dailyProjectionLineChart;

        this.revenueController = new RevenueController(database);
        this.expenseController = new ExpenseController(database);
        this.cashTransactionController = new CashTransactionController(database);
        this.dailySummaryController = new DailySummaryController(database);
    }

    public void init() {
        // Operational Expenses Bar Chart
        CategoryAxis operationalBarChartXAxis = (CategoryAxis) operationalBarChart.getXAxis();
        if (operationalBarChartXAxis != null) {
            operationalBarChartXAxis.getCategories().setAll(Expense.operationalTypes);
        }

        // Non-Operational Expenses Bar Chart
        CategoryAxis nonOperationalBarChartXAxis = (CategoryAxis) nonOperationalBarChart.getXAxis();
        if (nonOperationalBarChartXAxis != null) {
            nonOperationalBarChartXAxis.getCategories().setAll(Expense.nonOperationalTypes);
        }

        // Revenues Bar Chart
        revenuesBarChart.getStyleClass().add("revenue-bar");
        CategoryAxis revenuesBarChartXAxis = (CategoryAxis) revenuesBarChart.getXAxis();
        if (revenuesBarChartXAxis != null) {
            revenuesBarChartXAxis.getCategories().setAll(Revenue.types);
        }

        // Monthly Line Chart
        CategoryAxis monthlyLineChartXAxis = (CategoryAxis) monthlyProjectionLineChart.getXAxis();
        if (monthlyLineChartXAxis != null) {
            monthlyLineChartXAxis.getCategories().setAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        }
    }

    public void refresh() {
        showProgress(-1, "Fetching data...");
        disposables.add(Single.fromCallable(revenueController::getAll).observeOn(JavaFxScheduler.platform())
                .flatMap(revenues -> {
                    allRevenues = revenues;
                    return Single.fromCallable(expenseController::getAll);
                }).flatMap(expenses -> {
                    allExpenses = expenses;
                    return Single.fromCallable(cashTransactionController::getAll);
                }).flatMap(cashTransactions -> {
                    allCashTransactions = cashTransactions;
                    return Single.fromCallable(dailySummaryController::getAll);
                }).subscribeOn(Schedulers.io()).subscribe(summaries -> {
                    hideProgress();
                    allDailySummaries = summaries;
                    displayProjections();
                }, err -> {
                    hideProgress();
                    mainWindow.showErrorDialog("Database Error", "Error while fetching data.\n" + err);
                }));
    }

    private void displayProjections() {
        LocalDate now = LocalDate.now();

        // Expenses Data
        HashMap<String, Double> operationalExpensesMap = new HashMap<>();
        HashMap<String, Double> nonOperationalExpensesMap = new HashMap<>();
        double operationalTotal = 0;
        double nonOperationalTotal = 0;

        for (Expense expense : allExpenses) {
            if (expense.getDate().getMonthValue() == now.getMonthValue() && expense.getDate().getYear() == now.getYear()) {
                if (expense.getCategory().equals(Expense.CAT_OPERATIONAL)) {
                    operationalTotal += expense.getAmount();

                    double value = operationalExpensesMap.get(expense.getType()) == null ? expense.getAmount() :
                            operationalExpensesMap.get(expense.getType()) + expense.getAmount();
                    operationalExpensesMap.put(expense.getType(), value);
                } else {
                    nonOperationalTotal += expense.getAmount();

                    double value = nonOperationalExpensesMap.get(expense.getType()) == null ? expense.getAmount() :
                            nonOperationalExpensesMap.get(expense.getType()) + expense.getAmount();
                    nonOperationalExpensesMap.put(expense.getType(), value);
                }
            }
        }

        // Expenses (Operational vs Non-Operational) Pie Chart
        expensesPieChart.setTitle("Expenses (" + ViewUtils.shortMonthStringValue(now.getMonthValue()) + " " + now.getYear() + ")");
        PieChart.Data operationalData = new PieChart.Data(Expense.CAT_OPERATIONAL, operationalTotal);
        PieChart.Data nonOperationalData = new PieChart.Data(Expense.CAT_NONOPERATIONAL, nonOperationalTotal);
        expensesPieChart.getData().clear();
        expensesPieChart.getData().addAll(operationalData, nonOperationalData);

        // Operational Expenses Bar Chart
        XYChart.Series<String, Number> operationalExpensesSeries = new XYChart.Series<>();
        operationalExpensesSeries.setName("Types");
        for (String type : Expense.operationalTypes) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(type,
                    operationalExpensesMap.get(type) == null ? 0 : operationalExpensesMap.get(type));
            operationalExpensesSeries.getData().add(data);
        }
        operationalBarChart.getData().clear();
        operationalBarChart.getData().add(operationalExpensesSeries);

        // Non-Operational Expenses Bar Chart
        XYChart.Series<String, Number> nonOperationalExpensesSeries = new XYChart.Series<>();
        nonOperationalExpensesSeries.setName("Types");
        for (String type : Expense.nonOperationalTypes) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(type,
                    nonOperationalExpensesMap.get(type) == null ? 0 : nonOperationalExpensesMap.get(type));
            nonOperationalExpensesSeries.getData().add(data);
        }
        nonOperationalBarChart.getData().clear();
        nonOperationalBarChart.getData().add(nonOperationalExpensesSeries);

        // Revenues Data
        HashMap<String, Double> revenuesMap = new HashMap<>();
        double totalRevenues = 0;
        for (Revenue revenue : allRevenues) {
            if (revenue.getDate().getMonthValue() == now.getMonthValue() && revenue.getDate().getYear() == now.getYear()) {
                totalRevenues += revenue.getAmount();

                double value = revenuesMap.get(revenue.getType()) == null ? revenue.getAmount() :
                        revenuesMap.get(revenue.getType()) + revenue.getAmount();
                revenuesMap.put(revenue.getType(), value);
            }
        }

        // Revenues Bar Chart
        revenuesBarChart.setTitle("Revenues (" + ViewUtils.shortMonthStringValue(now.getMonthValue()) + " " + now.getYear() + ")");
        XYChart.Series<String, Number> revenuesSeries = new XYChart.Series<>();
        revenuesSeries.setName("Types");
        for (String type : Revenue.types) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(type,
                    revenuesMap.get(type) == null ? 0 : revenuesMap.get(type));
            revenuesSeries.getData().add(data);
        }
        revenuesBarChart.getData().clear();
        revenuesBarChart.getData().add(revenuesSeries);

        // Expenses vs Revenues Pie Chart
        PieChart.Data totalRevenuesData = new PieChart.Data("Revenues", totalRevenues);
        PieChart.Data totalExpensesData = new PieChart.Data("Expenses", operationalTotal + nonOperationalTotal);
        expensesRevenuesPieChart.getData().clear();
        expensesRevenuesPieChart.getData().addAll(totalRevenuesData, totalExpensesData);

        // Monthly Projection (Revenues vs Expenses vs Cash Transactions vs Cash Balance)
        monthlyProjectionLineChart.setTitle("Monthly Projection (" + now.getYear() + ")");
        XYChart.Series<String, Number> monthlyRevenues = new XYChart.Series<>();
        monthlyRevenues.setName("Revenues");
        XYChart.Series<String, Number> monthlyExpenses = new XYChart.Series<>();
        monthlyExpenses.setName("Expenses");
        XYChart.Series<String, Number> monthlyCashIn = new XYChart.Series<>();
        monthlyCashIn.setName("Cash In");
        XYChart.Series<String, Number> monthlyCashOut = new XYChart.Series<>();
        monthlyCashOut.setName("Cash Out");
        XYChart.Series<String, Number> monthlyCashBalance = new XYChart.Series<>();
        monthlyCashBalance.setName("Cash Balance");

        FXCollections.sort(allDailySummaries, Comparator.comparing(DailySummary::getDate));

        for (int month = 1; month <= 12; month++) {
            double revenues = 0;
            double expenses = 0;
            double cash_in = 0;
            double cash_out = 0;
            double balance = 0;

            for (DailySummary summary : allDailySummaries) {
                if (summary.getDate().getMonthValue() == month && summary.getDate().getYear() == now.getYear()) {
                    revenues += summary.getRevenues();
                    expenses += summary.getExpenses();
                    cash_in += summary.getCashIn();
                    cash_out += summary.getCashOut();
                    balance = summary.getBalance();
                }
            }

            String monthStr = ViewUtils.shortMonthStringValue(month);
            monthlyRevenues.getData().add(new XYChart.Data<>(monthStr, revenues));
            monthlyExpenses.getData().add(new XYChart.Data<>(monthStr, expenses));
            monthlyCashIn.getData().add(new XYChart.Data<>(monthStr, cash_in));
            monthlyCashOut.getData().add(new XYChart.Data<>(monthStr, cash_out));
            monthlyCashBalance.getData().add(new XYChart.Data<>(monthStr, balance));
        }

        monthlyProjectionLineChart.getData().clear();
        monthlyProjectionLineChart.getData().add(monthlyRevenues);
        monthlyProjectionLineChart.getData().add(monthlyExpenses);
        monthlyProjectionLineChart.getData().add(monthlyCashIn);
        monthlyProjectionLineChart.getData().add(monthlyCashOut);
        monthlyProjectionLineChart.getData().add(monthlyCashBalance);

        for (XYChart.Data<String, Number> data : monthlyRevenues.getData()) {
            String text = String.format("%s\n%s", "Revenues", ViewUtils.toStringMoneyFormat(data.getYValue().doubleValue()));
            Tooltip.install(data.getNode(), new Tooltip(text));
        }

        for (XYChart.Data<String, Number> data : monthlyExpenses.getData()) {
            String text = String.format("%s\n%s", "Expenses", ViewUtils.toStringMoneyFormat(data.getYValue().doubleValue()));
            Tooltip.install(data.getNode(), new Tooltip(text));
        }

        for (XYChart.Data<String, Number> data : monthlyCashIn.getData()) {
            String text = String.format("%s\n%s", "Cash In", ViewUtils.toStringMoneyFormat(data.getYValue().doubleValue()));
            Tooltip.install(data.getNode(), new Tooltip(text));
        }

        for (XYChart.Data<String, Number> data : monthlyCashOut.getData()) {
            String text = String.format("%s\n%s", "Cash Out", ViewUtils.toStringMoneyFormat(data.getYValue().doubleValue()));
            Tooltip.install(data.getNode(), new Tooltip(text));
        }

        for (XYChart.Data<String, Number> data : monthlyCashBalance.getData()) {
            String text = String.format("%s\n%s", "Cash Balance", ViewUtils.toStringMoneyFormat(data.getYValue().doubleValue()));
            Tooltip.install(data.getNode(), new Tooltip(text));
        }

        // Daily Projection
        dailyProjectionLineChart.setTitle("Daily Projection (" + now.getMonth() + " " + now.getYear() + ")");

        XYChart.Series<String, Number> dailyRevenues = new XYChart.Series<>();
        dailyRevenues.setName("Revenues");
        XYChart.Series<String, Number> dailyExpenses = new XYChart.Series<>();
        dailyExpenses.setName("Expenses");
        XYChart.Series<String, Number> dailyCashIn = new XYChart.Series<>();
        dailyCashIn.setName("Cash In");
        XYChart.Series<String, Number> dailyCashOut = new XYChart.Series<>();
        dailyCashOut.setName("Cash Out");
        XYChart.Series<String, Number> dailyBalance = new XYChart.Series<>();
        dailyBalance.setName("Cash Balance");

        YearMonth ym = YearMonth.now();
        CategoryAxis dailyCategoryAxis = (CategoryAxis) dailyProjectionLineChart.getXAxis();
        if (dailyCategoryAxis != null) {
            dailyCategoryAxis.getCategories().clear();
        }
        for (int d = 1; d <= ym.atEndOfMonth().getDayOfMonth(); d++) {
            if (dailyCategoryAxis != null) dailyCategoryAxis.getCategories().add(String.valueOf(d));

            double revenues = 0;
            double expenses = 0;
            double cashIn = 0;
            double cashOut = 0;
            double balance = 0;

            for (DailySummary s : allDailySummaries) {
                if (s.getDate().getDayOfMonth() == d && s.getDate().getMonthValue() == now.getMonthValue() && s.getDate().getYear() == ym.getYear()) {
                    revenues += s.getRevenues();
                    expenses += s.getExpenses();
                    cashIn += s.getCashIn();
                    cashOut += s.getCashOut();
                    balance = s.getBalance();
                }
            }

            dailyRevenues.getData().add(new XYChart.Data<>(d + "", revenues));
            dailyExpenses.getData().add(new XYChart.Data<>(d + "", expenses));
            dailyCashIn.getData().add(new XYChart.Data<>(d + "", cashIn));
            dailyCashOut.getData().add(new XYChart.Data<>(d + "", cashOut));
            dailyBalance.getData().add(new XYChart.Data<>(d + "", balance));
        }

        dailyProjectionLineChart.getData().clear();
        dailyProjectionLineChart.getData().add(dailyRevenues);
        dailyProjectionLineChart.getData().add(dailyExpenses);
        dailyProjectionLineChart.getData().add(dailyCashIn);
        dailyProjectionLineChart.getData().add(dailyCashOut);
        dailyProjectionLineChart.getData().add(dailyBalance);

        for (XYChart.Data<String, Number> data : dailyRevenues.getData()) {
            String text = String.format("%s\n%s", "Revenues", ViewUtils.toStringMoneyFormat(data.getYValue().doubleValue()));
            Tooltip.install(data.getNode(), new Tooltip(text));
        }

        for (XYChart.Data<String, Number> data : dailyExpenses.getData()) {
            String text = String.format("%s\n%s", "Expenses", ViewUtils.toStringMoneyFormat(data.getYValue().doubleValue()));
            Tooltip.install(data.getNode(), new Tooltip(text));
        }

        for (XYChart.Data<String, Number> data : dailyCashIn.getData()) {
            String text = String.format("%s\n%s", "Cash In", ViewUtils.toStringMoneyFormat(data.getYValue().doubleValue()));
            Tooltip.install(data.getNode(), new Tooltip(text));
        }

        for (XYChart.Data<String, Number> data : dailyCashOut.getData()) {
            String text = String.format("%s\n%s", "Cash Out", ViewUtils.toStringMoneyFormat(data.getYValue().doubleValue()));
            Tooltip.install(data.getNode(), new Tooltip(text));
        }

        for (XYChart.Data<String, Number> data : dailyBalance.getData()) {
            String text = String.format("%s\n%s", "Balance", ViewUtils.toStringMoneyFormat(data.getYValue().doubleValue()));
            Tooltip.install(data.getNode(), new Tooltip(text));
        }

    }

    private void showProgress(double progress, String text) {
        mainWindow.showProgress(progress, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    public void dispose() {
        disposables.dispose();
    }
}
