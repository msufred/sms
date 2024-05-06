package org.gemseeker.sms.views.panels;

import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.views.MainWindow;

public class TasksPanel extends AbstractPanel {

    private final MainWindow mainWindow;

    public TasksPanel(MainWindow mainWindow, Database database) {
        super(TasksPanel.class.getResource("tasks.fxml"));
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
