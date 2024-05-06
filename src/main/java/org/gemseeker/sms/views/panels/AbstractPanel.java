package org.gemseeker.sms.views.panels;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Gem
 */
public abstract class AbstractPanel {

    private Parent root;
    private final URL fxmlUrl;

    private final Alert infoDialog = new Alert(Alert.AlertType.INFORMATION);
    private final Alert warningDialog = new Alert(Alert.AlertType.WARNING);
    private final Alert errorDialog = new Alert(Alert.AlertType.ERROR);
    private final Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);

    public AbstractPanel(URL fxmlUrl) {
        this.fxmlUrl = fxmlUrl;

        infoDialog.setTitle("Information");
        warningDialog.setTitle("Warning");
        errorDialog.setTitle("Error");
        confirmDialog.setTitle("Confirmation");
    }

    public Parent getView() {
        if (root == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxmlUrl);
            loader.setController(this);
            try {
                root = loader.load();
                onFxmlLoaded();
            } catch (IOException e) {
                System.err.println("Failed to load FXML file: " + fxmlUrl.getPath());
            }
        }
        return root;
    }

    protected abstract void onFxmlLoaded();
    public abstract void onResume();
    public abstract void onPause();
    public abstract void onDispose();

    public void showInfoDialog(String header, String content) {
        infoDialog.setHeaderText(header);
        infoDialog.setContentText(content);
        infoDialog.showAndWait();
    }

    public void showWarningDialog(String header, String content) {
        warningDialog.setHeaderText(header);
        warningDialog.setContentText(content);
        warningDialog.showAndWait();
    }

    public void showErrorDialog(String header, String content) {
        errorDialog.setHeaderText(header);
        errorDialog.setContentText(content);
        errorDialog.showAndWait();
    }

    public Optional<ButtonType> showConfirmDialog(String header, String content, ButtonType...buttons) {
        confirmDialog.setHeaderText(header);
        confirmDialog.setContentText(content);
        if (buttons.length > 0) confirmDialog.getButtonTypes().setAll(buttons);
        return confirmDialog.showAndWait();
    }
}
