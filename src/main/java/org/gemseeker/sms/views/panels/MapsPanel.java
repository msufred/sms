package org.gemseeker.sms.views.panels;

import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.views.MainWindow;

public class MapsPanel extends AbstractPanel {

    private final MainWindow mainWindow;

    public MapsPanel(MainWindow mainWindow, Database database) {
        super(MapsPanel.class.getResource("maps.fxml"));
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
