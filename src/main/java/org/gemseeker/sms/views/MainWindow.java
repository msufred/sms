package org.gemseeker.sms.views;

import io.github.msufred.feathericons.*;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.gemseeker.sms.Settings;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.User;
import org.gemseeker.sms.data.controllers.UserController;
import org.gemseeker.sms.views.icons.PesoIcon;
import org.gemseeker.sms.views.panels.*;

import javax.xml.transform.TransformerException;
import java.sql.SQLException;
import java.util.Objects;

public class MainWindow extends AbstractWindow {

    @FXML private ToggleButton btnDashboard;
    @FXML private ToggleButton btnCustomers;
    @FXML private ToggleButton btnHotspots;
    @FXML private ToggleButton btnPayments;
    @FXML private ToggleButton btnInventory;
    @FXML private ToggleButton btnMaps;
    @FXML private ToggleButton btnTasks;
    @FXML private StackPane contentPane;
    @FXML private Label lblAuthority;
    @FXML private Button btnLogout;

    // Upcoming and Outdated Schedule Label
    @FXML private Label lblUpcoming;
    @FXML private Label lblOutdated;

    @FXML private ProgressBar progressBar;
    @FXML private Label progressLabel;

    private final Settings settings;
    private final Database database;
    private final UserController userController;
    private final CompositeDisposable disposables;

    private long userId = -1;
    private User user = null;

    // panels
    private DashboardPanel dashboardPanel;
    private PaymentsPanel paymentsPanel;
    private AccountsPanel accountsPanel;
    private WiFiHotspotsPanel wiFiHotspotsPanel;
    private InventoryPanel inventoryPanel;
    private MapsPanel mapsPanel;
    private TasksPanel tasksPanel;
    private AbstractPanel mCurrPanel = null;

    public MainWindow(Settings settings, Database database, Stage stage) {
        super("SMS", MainWindow.class.getResource("main.fxml"), stage, null);
        this.settings = settings;
        this.database = database;
        this.userController = new UserController(database);
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        // boolean maximized = Boolean.parseBoolean(settings.get("system", "maximized"));
        stage.setMaximized(true);
        stage.getIcons().add(new Image(Objects.requireNonNull(MainWindow.class.getResourceAsStream("logo_v3.png"))));
    }

    @Override
    protected void onFxmlLoaded() {
        setupIcons();
        setupToggles(btnDashboard, btnPayments, btnCustomers, btnHotspots, btnInventory, btnMaps, btnTasks);
    }

    public void setUserId(long id) {
        this.userId = id;
    }

    @Override
    protected void onShow() {
        if (userId != -1) {
            showProgress(-1, "Retrieving user info...");
            disposables.add(Single.fromCallable(() -> userController.get((int) userId))
                    .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(usr -> {
                        hideProgress();
                        this.user = usr;
                        if (user != null) {
                            lblAuthority.setText(String.format("%s [%s]", user.getUsername(), ViewUtils.capitalize(user.getRole())));
                        }
                        setupPanels();
                    }, err -> {
                        hideProgress();
                        showErrorDialog("Database Error", "No User found with id: " + userId);
                    }));
        } else {
            setupPanels();
        }
    }

    private void setupToggles(ToggleButton...toggleButtons) {
        for (ToggleButton toggle : toggleButtons) {
            toggle.addEventFilter(MouseDragEvent.MOUSE_PRESSED, event -> {
                if (toggle.isSelected()) event.consume();
                else {
                    switch (toggle.getText()) {
                        case "Payments":
                            changePanel(paymentsPanel);
                            break;
                        case "Accounts":
                            changePanel(accountsPanel);
                            break;
                        case "WiFi Hotspots":
                            changePanel(wiFiHotspotsPanel);
                            break;
                        case "Services & Products":
                            changePanel(inventoryPanel);
                            break;
                        case "Maps":
                            changePanel(mapsPanel);
                            break;
                        case "Tasks & Schedules":
                            changePanel(tasksPanel);
                            break;
                        default:
                            changePanel(dashboardPanel);
                    }
                }
            });
        }
    }

    private void setupPanels() {
        if (dashboardPanel == null) dashboardPanel = new DashboardPanel(this, database);
        if (paymentsPanel == null) paymentsPanel = new PaymentsPanel(this, settings, database);
        if (accountsPanel == null) accountsPanel = new AccountsPanel(this, database);
        if (wiFiHotspotsPanel == null) wiFiHotspotsPanel = new WiFiHotspotsPanel(this, database);
        if (inventoryPanel == null) inventoryPanel = new InventoryPanel(this, database);
        if (mapsPanel == null) mapsPanel = new MapsPanel(this, database);
        if (tasksPanel == null) tasksPanel = new TasksPanel(this, database);
        changePanel(dashboardPanel);
    }

    private void changePanel(AbstractPanel panel) {
        if (panel == null) return;
        if (mCurrPanel != null && mCurrPanel != panel) {
            mCurrPanel.onPause();
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(panel.getView());
        panel.onResume();
        mCurrPanel = panel;
    }

    private void setupIcons() {
        btnDashboard.setGraphic(new GridIcon(14));
        btnPayments.setGraphic(new PesoIcon(14));
        btnCustomers.setGraphic(new UsersIcon(14));
        btnHotspots.setGraphic(new WifiIcon(14));
        btnInventory.setGraphic(new BoxIcon(14));
        btnMaps.setGraphic(new MapIcon(14));
        btnTasks.setGraphic(new TagIcon(14));
    }

    public void showProgress(double progress, String text) {
        progressBar.setProgress(progress);
        progressBar.setVisible(true);
        progressLabel.setText(text);
        progressLabel.setVisible(true);
    }

    public void hideProgress() {
        progressBar.setVisible(false);
        progressBar.setProgress(0);
        progressLabel.setVisible(false);
        progressLabel.setText("");
    }

    @Override
    protected void onClose() {
        if (mCurrPanel != null) mCurrPanel.onPause();

        try {
            settings.save();
        } catch (TransformerException e) {
            System.err.println("failed to save settings.xml");
        }

        try {
            database.close();
        } catch (SQLException e) {
            System.err.println("failed to close database");
        }

        if (dashboardPanel != null) dashboardPanel.onDispose();
        if (paymentsPanel != null) paymentsPanel.onDispose();
        if (accountsPanel != null) accountsPanel.onDispose();
        if (wiFiHotspotsPanel != null) wiFiHotspotsPanel.onDispose();
        if (inventoryPanel != null) inventoryPanel.onDispose();
        if (mapsPanel != null) mapsPanel.onDispose();
        if (tasksPanel != null) tasksPanel.onDispose();
        disposables.dispose();
    }
}
