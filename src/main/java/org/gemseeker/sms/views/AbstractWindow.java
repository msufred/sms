package org.gemseeker.sms.views;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author Gem
 */
public abstract class AbstractWindow {

    private final String title;
    private final URL fxmlUrl;
    private final Stage owner;
    private Stage stage;
    private Scene scene;

    private final Alert infoDialog = new Alert(Alert.AlertType.INFORMATION);
    private final Alert warningDialog = new Alert(Alert.AlertType.WARNING);
    private final Alert errorDialog = new Alert(Alert.AlertType.ERROR);
    private final Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);

    private boolean mInitialized = false;

    public AbstractWindow(String title, URL fxmlUrl, Stage stage, Stage owner) {
        this.title = title;
        this.fxmlUrl = fxmlUrl;
        this.stage = stage;
        this.owner = owner;

        infoDialog.setTitle("Information");
        warningDialog.setTitle("Warning");
        errorDialog.setTitle("Error");
        confirmDialog.setTitle("Confirmation");
    }

    protected Stage getStage() {
        if (stage == null) stage = new Stage();
        if (!mInitialized) {
            stage.setTitle(title);
            stage.setOnShown(evt -> onShow());
            stage.setOnHidden(evt -> onClose());
            if (owner != null) {
                stage.initOwner(owner);
                stage.getIcons().setAll(owner.getIcons());
            }
            initWindow(stage);
            stage.setScene(getScene());
            mInitialized = true;
        }
        return stage;
    }

    protected Scene getScene() {
        if (scene == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxmlUrl);
            loader.setController(this);
            try {
                Parent root = loader.load();
                scene = new Scene(root);
                onFxmlLoaded();
            } catch (IOException e) {
                System.err.println("Failed to load FXML file: " + fxmlUrl.getPath());
            }
        }
        return scene;
    }

    protected void initWindow(Stage stage) {
        // left empty
    }

    protected abstract void onFxmlLoaded();

    public void show() {
        getStage().show();
    }

    protected abstract void onShow();

    public void showAndWait() {
        getStage().showAndWait();
    }

    public void close() {
        getStage().close();
    }

    protected abstract void onClose();

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
