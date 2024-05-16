package org.gemseeker.sms.views.panels;

import io.github.msufred.feathericons.*;
import io.reactivex.disposables.CompositeDisposable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Expense;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.icons.PesoIcon;

import java.time.LocalDate;

public class DashboardPanel extends AbstractPanel {

    @FXML private TabPane tabPane;
    @FXML private Tab tabRecentSales;
    @FXML private Tab tabProjections;
    @FXML private Tab tabRevenues;
    @FXML private Tab tabExpenses;

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

    private final MainWindow mainWindow;
    private final CompositeDisposable disposables;

    public DashboardPanel(MainWindow mainWindow, Database database) {
        super(DashboardPanel.class.getResource("dashboard.fxml"));
        this.mainWindow = mainWindow;

        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    private void setupIcons() {
        tabRecentSales.setGraphic(new ShoppingCartIcon(14));
        tabProjections.setGraphic(new TrendingUpIcon(14));
        tabRevenues.setGraphic(new PesoIcon(14));
        tabExpenses.setGraphic(new PesoIcon(14));

        btnAddExpense.setGraphic(new PlusIcon(14));
        btnEditExpense.setGraphic(new Edit2Icon(14));
        btnDeleteExpense.setGraphic(new TrashIcon(14));
    }

    @Override
    public void onDispose() {
        disposables.dispose();
    }
}
