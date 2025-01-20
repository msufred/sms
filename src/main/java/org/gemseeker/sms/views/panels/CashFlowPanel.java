package org.gemseeker.sms.views.panels;

import io.github.msufred.feathericons.PrinterIcon;
import io.github.msufred.feathericons.UploadIcon;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import org.gemseeker.sms.data.*;
import org.gemseeker.sms.data.controllers.CashTransactionController;
import org.gemseeker.sms.data.controllers.DailySummaryController;
import org.gemseeker.sms.data.controllers.ExpenseController;
import org.gemseeker.sms.data.controllers.RevenueController;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.ViewUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class CashFlowPanel extends AbstractPanel {

    @FXML private DatePicker datePicker;
    @FXML private Button btnPrint;
    @FXML private Label lblDate;
    @FXML private VBox contentView;

    // Debit Labels
    @FXML private Label d2; // Bank Transfer
    @FXML private Label d3; // Billing
    @FXML private Label d4; // WiFi Vendo
    @FXML private Label d5; // Products
    @FXML private Label d6; // Services
    @FXML private Label d7; // Cash Transfer

    // Credit Labels
    @FXML private Label c8; // Electricity
    @FXML private Label c9; // Water
    @FXML private Label c10; // Meals
    @FXML private Label c11; // Transportation
    @FXML private Label c12; // Rental
    @FXML private Label c13; // Ground Rental
    @FXML private Label c14; // Computer Peripherals
    @FXML private Label c15; // Maintenance
    @FXML private Label c16; // Repair
    @FXML private Label c17; // Internet
    @FXML private Label c18; // Payment
    @FXML private Label c19; // Business Loan
    @FXML private Label c20; // WiFi Vendo Essentials
    @FXML private Label c21; // Business License Registration
    @FXML private Label c22; // Salary
    // -- non operational
    @FXML private Label c23; // Electricity
    @FXML private Label c24; // Water
    @FXML private Label c25; // Meals
    @FXML private Label c26; // Transportation
    @FXML private Label c27; // Rental
    @FXML private Label c28; // Maintenance
    @FXML private Label c29; // Repair
    @FXML private Label c30; // Internet
    @FXML private Label c31; // Payment
    @FXML private Label c32; // Bank Loan
    @FXML private Label c33; // Personal Loan
    @FXML private Label c34; // Car Loan
    @FXML private Label c35; // Education
    @FXML private Label c36; // School Fees
    @FXML private Label c37; // Apartment Rental
    @FXML private Label c38; // Student Allowance
    @FXML private Label c39; // Others

    // Running Balance Labels
    @FXML private Label r1; // FORWARDED
    @FXML private Label r2; // Bank Transfer
    @FXML private Label r3; // Billing
    @FXML private Label r4; // WiFi Vendo
    @FXML private Label r5; // Products
    @FXML private Label r6; // Services
    @FXML private Label r7; // Cash Transfer
    @FXML private Label r8;
    @FXML private Label r9;
    @FXML private Label r10;
    @FXML private Label r11;
    @FXML private Label r12;
    @FXML private Label r13;
    @FXML private Label r14;
    @FXML private Label r15;
    @FXML private Label r16;
    @FXML private Label r17;
    @FXML private Label r18;
    @FXML private Label r19;
    @FXML private Label r20;
    @FXML private Label r21;
    @FXML private Label r22;
    @FXML private Label r23;
    @FXML private Label r24;
    @FXML private Label r25;
    @FXML private Label r26;
    @FXML private Label r27;
    @FXML private Label r28;
    @FXML private Label r29;
    @FXML private Label r30;
    @FXML private Label r31;
    @FXML private Label r32;
    @FXML private Label r33;
    @FXML private Label r34;
    @FXML private Label r35;
    @FXML private Label r36;
    @FXML private Label r37;
    @FXML private Label r38;
    @FXML private Label r39;
    @FXML private Label lblTotal;

    private final MainWindow mainWindow;
    private final Database database;
    private final CompositeDisposable disposables;

    private final RevenueController revenueController;
    private final ExpenseController expenseController;
    private final CashTransactionController cashTransactionController;
    private final DailySummaryController dailySummaryController;

    private ObservableList<Revenue> currRevenues;
    private ObservableList<Expense> currExpenses;
    private ObservableList<CashTransaction> currTransactions;
    private ObservableList<DailySummary> dailySummaries;
    private DailySummary currentSummary;

    public CashFlowPanel(MainWindow mainWindow, Database database) {
        super(CashFlowPanel.class.getResource("cash_flow.fxml"));

        this.mainWindow = mainWindow;
        this.database = database;
        this.disposables = new CompositeDisposable();

        this.revenueController = new RevenueController(database);
        this.expenseController = new ExpenseController(database);
        this.cashTransactionController = new CashTransactionController(database);
        this.dailySummaryController = new DailySummaryController(database);
    }

    @Override
    protected void onFxmlLoaded() {
        datePicker.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
                lblDate.setText(String.format("Date: %s", newVal.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))));
                refresh();
            }
        });

        btnPrint.setGraphic(new PrinterIcon(14));
        btnPrint.setOnAction(evt -> print());
    }

    @Override
    public void onResume() {
        datePicker.setValue(LocalDate.now());
    }

    private void refresh() {
        showProgress("Fetching data...");
        disposables.add(Single.fromCallable(() -> revenueController.getByDate(datePicker.getValue()))
                .flatMap(revenues -> {
                    currRevenues = revenues;
                    return Single.fromCallable(() -> expenseController.getByDate(datePicker.getValue()));
                }).flatMap(expenses -> {
                    currExpenses = expenses;
                    return Single.fromCallable(() -> cashTransactionController.getByDate(datePicker.getValue()));
                }).flatMap(cashTransactions -> {
                    currTransactions = cashTransactions;
                    return Single.fromCallable(dailySummaryController::getAll);
                }).flatMap(summaries -> {
                    dailySummaries = summaries;
                    return Single.fromCallable(() -> dailySummaryController.getByDate(datePicker.getValue()));
                }).observeOn(JavaFxScheduler.platform()).subscribeOn(Schedulers.io()).subscribe(summary -> {
                    currentSummary = summary;
                    hideProgress();
                    displayData();
                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while fetching data.\n" + err);
                }));
    }

    private void displayData() {
        if (currentSummary == null) return;

        // Forwarded
        r1.setText(ViewUtils.toStringMoneyFormat(currentSummary.getForwarded()));

        // Cash Transaction
        double bankTransfer = 0;
        double cashTransfer = 0;
        for (CashTransaction ct : currTransactions) {
            if (ct.getType().equals(CashTransaction.TYPE_CASH_IN)) {
                if (ct.getMode().equals(CashTransaction.MODE_BANK_CASH)) {
                    bankTransfer += ct.getAmount();
                }

                if (ct.getMode().equals(CashTransaction.MODE_CASH)) {
                    cashTransfer += ct.getAmount();
                }
            }
        }

        // Revenues
        double billings = 0;
        double wifi = 0;
        double products = 0;
        double services = 0;
        double others = 0;

        for (Revenue r : currRevenues) {
            switch (r.getType()) {
                case Revenue.TYPE_BILLING -> billings += r.getAmount();
                case Revenue.TYPE_WIFI_VENDO -> wifi += r.getAmount();
                case Revenue.TYPE_PURCHASE -> products += r.getAmount();
                case Revenue.TYPE_SERVICE -> services += r.getAmount();
                default -> others += r.getAmount();
            }
        }

        // Expenses
        // -- Operational
        double opElectricity = 0;
        double opWater = 0;
        double opMeals = 0;
        double opTransportation = 0;
        double opRental = 0;
        double opGroundRental = 0;
        double opComputerPeripherals = 0;
        double opMaintenance = 0;
        double opRepair = 0;
        double opInternet = 0;
        double opPayment = 0;
        double opBusinessLoan = 0;
        double opWiFiVendo = 0;
        double opBusinessLicense = 0;
        double opSalary = 0;
        // -- Non-Operational
        double nopElectricity = 0;
        double nopWater = 0;
        double nopMeals = 0;
        double nopTransportation = 0;
        double nopRental = 0;
        double nopMaintenance = 0;
        double nopRepair = 0;
        double nopInternet = 0;
        double nopPayment = 0;
        double nopBankLoan = 0;
        double nopPersonalLoan = 0;
        double nopCarLoan = 0;
        double nopEducation = 0;
        double nopSchoolFees = 0;
        double nopApartmentRental = 0;
        double nopStudentAllowance = 0;
        double nopOthers = 0;

        for (Expense exp : currExpenses) {
            double amount = exp.getAmount();
            if (exp.getCategory().equals(Expense.CAT_OPERATIONAL)) {
                switch (exp.getType()) {
                    case Expense.TYPE_ELECTRICITY -> opElectricity += amount;
                    case Expense.TYPE_WATER -> opWater += amount;
                    case Expense.TYPE_MEALS -> opMeals += amount;
                    case Expense.TYPE_TRANSPORTATION -> opTransportation += amount;
                    case Expense.TYPE_RENTAL -> opRental += amount;
                    case Expense.TYPE_GROUND_RENTAL -> opGroundRental += amount;
                    case Expense.TYPE_COMPUTER_PERIPHERALS -> opComputerPeripherals += amount;
                    case Expense.TYPE_MAINTENANCE -> opMaintenance += amount;
                    case Expense.TYPE_REPAIR -> opRepair += amount;
                    case Expense.TYPE_INTERNET -> opInternet += amount;
                    case Expense.TYPE_PAYMENT -> opPayment += amount;
                    case Expense.TYPE_BUSINESS_LOAN -> opBusinessLoan += amount;
                    case Expense.TYPE_WIFI_VENDO_ESSENTIALS -> opWiFiVendo += amount;
                    case Expense.TYPE_BUSINESS_LICENSE_REGISTRATION -> opBusinessLicense += amount;
                    default -> opSalary += amount;
                }
            } else {
                switch (exp.getType()) {
                    case Expense.TYPE_ELECTRICITY -> nopElectricity += amount;
                    case Expense.TYPE_WATER -> nopWater += amount;
                    case Expense.TYPE_MEALS -> nopMeals += amount;
                    case Expense.TYPE_TRANSPORTATION -> nopTransportation += amount;
                    case Expense.TYPE_RENTAL -> nopRental += amount;
                    case Expense.TYPE_MAINTENANCE -> nopMaintenance += amount;
                    case Expense.TYPE_REPAIR -> nopRepair += amount;
                    case Expense.TYPE_INTERNET -> nopInternet += amount;
                    case Expense.TYPE_PAYMENT -> nopPayment += amount;
                    case Expense.TYPE_BANK_LOAN -> nopBankLoan += amount;
                    case Expense.TYPE_PERSONAL_LOAN -> nopPersonalLoan += amount;
                    case Expense.TYPE_CAR_LOAN -> nopCarLoan += amount;
                    case Expense.TYPE_EDUCATION -> nopEducation += amount;
                    case Expense.TYPE_SCHOOL_FEES -> nopSchoolFees += amount;
                    case Expense.TYPE_APARTMENT_RENTAL -> nopApartmentRental += amount;
                    case Expense.TYPE_STUDENT_ALLOWANCE -> nopStudentAllowance += amount;
                    default -> nopOthers += amount;
                }
            }
        }

        double runningBalance = currentSummary.getForwarded() + bankTransfer;
        // --- DEBIT ---
        // Bank Transfer
        d2.setText(ViewUtils.toStringMoneyFormat(bankTransfer));
        r2.setText(ViewUtils.toStringMoneyFormat(runningBalance));

        // Cash Transfer
        runningBalance += cashTransfer;
        d7.setText(ViewUtils.toStringMoneyFormat(cashTransfer));
        r7.setText(ViewUtils.toStringMoneyFormat(runningBalance));

        // Subscription
        runningBalance += billings;
        d3.setText(ViewUtils.toStringMoneyFormat(billings));
        r3.setText(ViewUtils.toStringMoneyFormat(runningBalance));

        // WiFi Vendo
        runningBalance += wifi;
        d4.setText(ViewUtils.toStringMoneyFormat(wifi));
        r4.setText(ViewUtils.toStringMoneyFormat(runningBalance));

        // Merchandise
        runningBalance += products;
        d5.setText(ViewUtils.toStringMoneyFormat(products));
        r5.setText(ViewUtils.toStringMoneyFormat(runningBalance));

        // Services
        runningBalance += services;
        d6.setText(ViewUtils.toStringMoneyFormat(services));
        r6.setText(ViewUtils.toStringMoneyFormat(runningBalance));

        // --- OPERATIONAL  EXPENSES ---
        // Electricity
        runningBalance -= opElectricity;
        c8.setText(ViewUtils.toStringMoneyFormat(opElectricity));
        r8.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Water
        runningBalance -= opWater;
        c9.setText(ViewUtils.toStringMoneyFormat(opWater));
        r9.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Meals
        runningBalance -= opMeals;
        c10.setText(ViewUtils.toStringMoneyFormat(opMeals));
        r10.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Transportation
        runningBalance -= opTransportation;
        c11.setText(ViewUtils.toStringMoneyFormat(opTransportation));
        r11.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Rental
        runningBalance -= opRental;
        c12.setText(ViewUtils.toStringMoneyFormat(opRental));
        r12.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Ground Rental
        runningBalance -= opGroundRental;
        c13.setText(ViewUtils.toStringMoneyFormat(opGroundRental));
        r13.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Computer Peripherals
        runningBalance -= opComputerPeripherals;
        c14.setText(ViewUtils.toStringMoneyFormat(opComputerPeripherals));
        r14.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Maintenance
        runningBalance -= opMaintenance;
        c15.setText(ViewUtils.toStringMoneyFormat(opMaintenance));
        r15.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Repair
        runningBalance -= opRepair;
        c16.setText(ViewUtils.toStringMoneyFormat(opRepair));
        r16.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Internet
        runningBalance -= opInternet;
        c17.setText(ViewUtils.toStringMoneyFormat(opInternet));
        r17.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Payment
        runningBalance -= opPayment;
        c18.setText(ViewUtils.toStringMoneyFormat(opPayment));
        r18.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Business Loan
        runningBalance -= opBusinessLoan;
        c19.setText(ViewUtils.toStringMoneyFormat(opBusinessLoan));
        r19.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // WiFi Vendo Essentials
        runningBalance -= opWiFiVendo;
        c20.setText(ViewUtils.toStringMoneyFormat(opWiFiVendo));
        r20.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Business License Registration
        runningBalance -= opBusinessLicense;
        c21.setText(ViewUtils.toStringMoneyFormat(opBusinessLicense));
        r21.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Salary
        runningBalance -= opSalary;
        c22.setText(ViewUtils.toStringMoneyFormat(opSalary));
        r22.setText(ViewUtils.toStringMoneyFormat(runningBalance));

        // --- NON-OPERATIONAL EXPENSES ---
        // Electricity
        runningBalance -= nopElectricity;
        c23.setText(ViewUtils.toStringMoneyFormat(nopElectricity));
        r23.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Water
        runningBalance -= nopWater;
        c24.setText(ViewUtils.toStringMoneyFormat(nopWater));
        r24.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Meals
        runningBalance -= nopMeals;
        c25.setText(ViewUtils.toStringMoneyFormat(nopMeals));
        r25.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Transportation
        runningBalance -= nopTransportation;
        c26.setText(ViewUtils.toStringMoneyFormat(nopTransportation));
        r26.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Rental
        runningBalance -= nopRental;
        c27.setText(ViewUtils.toStringMoneyFormat(nopRental));
        r27.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Maintenance
        runningBalance -= nopMaintenance;
        c28.setText(ViewUtils.toStringMoneyFormat(nopMaintenance));
        r28.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Repair
        runningBalance -= nopRepair;
        c29.setText(ViewUtils.toStringMoneyFormat(nopRepair));
        r29.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Internet
        runningBalance -= nopInternet;
        c30.setText(ViewUtils.toStringMoneyFormat(nopInternet));
        r30.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Payment
        runningBalance -= nopPayment;
        c31.setText(ViewUtils.toStringMoneyFormat(nopPayment));
        r31.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Bank Loan
        runningBalance -= nopBankLoan;
        c32.setText(ViewUtils.toStringMoneyFormat(nopBankLoan));
        r32.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Personal Loan
        runningBalance -= nopPersonalLoan;
        c33.setText(ViewUtils.toStringMoneyFormat(nopPersonalLoan));
        r33.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Car Loan
        runningBalance -= nopCarLoan;
        c34.setText(ViewUtils.toStringMoneyFormat(nopCarLoan));
        r34.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Education
        runningBalance -= nopEducation;
        c35.setText(ViewUtils.toStringMoneyFormat(nopEducation));
        r35.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // School Fees
        runningBalance -= nopSchoolFees;
        c36.setText(ViewUtils.toStringMoneyFormat(nopSchoolFees));
        r36.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Apartment Rental
        runningBalance -= nopApartmentRental;
        c37.setText(ViewUtils.toStringMoneyFormat(nopApartmentRental));
        r37.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Student Allowance
        runningBalance -= nopStudentAllowance;
        c38.setText(ViewUtils.toStringMoneyFormat(nopStudentAllowance));
        r38.setText(ViewUtils.toStringMoneyFormat(runningBalance));
        // Others
        runningBalance -= nopOthers;
        c39.setText(ViewUtils.toStringMoneyFormat(nopOthers));
        r39.setText(ViewUtils.toStringMoneyFormat(runningBalance));

        lblTotal.setText(ViewUtils.toStringMoneyFormat(runningBalance));
    }

    private void showProgress(String text) {
        mainWindow.showProgress(-1, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    @Override
    public void onPause() {

    }

    private void print() {
        showProgress("Printing...");
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(mainWindow.getStage())) {
            Printer printer = printerJob.getPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);

            Scale scale = getScale(printerJob, pageLayout);
            contentView.getTransforms().add(scale);

            boolean success = printerJob.printPage(pageLayout, contentView);
            if (success) printerJob.endJob();
            contentView.getTransforms().remove(scale);
        }
        hideProgress();
    }

    private Scale getScale(PrinterJob printerJob, PageLayout pageLayout) {
        double width = contentView.getWidth();
        double height = contentView.getHeight();

        PrintResolution printResolution = printerJob.getJobSettings().getPrintResolution();
        width /= printResolution.getFeedResolution();
        height /= printResolution.getCrossFeedResolution();

        double scaleX = pageLayout.getPrintableWidth() / width / 600;
        double scaleY = pageLayout.getPrintableHeight() / height / 600;

        Scale scale = new Scale(scaleX, scaleY);
        return scale;
    }

    @Override
    public void onDispose() {
        disposables.dispose();
    }
}
