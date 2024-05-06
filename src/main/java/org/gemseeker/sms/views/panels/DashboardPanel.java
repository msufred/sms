package org.gemseeker.sms.views.panels;

import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.views.MainWindow;

public class DashboardPanel extends AbstractPanel {

    private final MainWindow mainWindow;

    public DashboardPanel(MainWindow mainWindow, Database database) {
        super(DashboardPanel.class.getResource("dashboard.fxml"));
        this.mainWindow = mainWindow;
    }

    @Override
    protected void onFxmlLoaded() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDispose() {

    }
}
