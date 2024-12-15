package org.gemseeker.sms;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.UserController;
import org.gemseeker.sms.views.LoginUserWindow;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.RegisterUserWindow;
import org.gemseeker.sms.views.SplashWindow;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;

public class AppMain extends Application {

    private Settings settings;
    private Database database;
    private MainWindow mainWindow;
    private UserController userController;
    private LoginUserWindow loginUserWindow;
    private RegisterUserWindow registerUserWindow;

    private Alert errorDialog;

    @Override
    public void start(Stage primaryStage) throws Exception {
        SplashWindow splashWindow = new SplashWindow();

        final Service<Void> startUp = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        createFolders();
                        copyFiles();
                        Thread.sleep(4000);
                        return null;
                    }
                };
            }
        };

        startUp.setOnFailed(evt -> {
            System.err.println("Startup failed!");
            Platform.exit();
            System.exit(1);
        });

        startUp.setOnSucceeded(evt -> {
            System.out.println("Startup succeeded!");
            splashWindow.close();
            try {
                settings = new Settings(Utils.SETTINGS_PATH);

                // TODO show dialog/window instead of hard coding
                if (settings.getDatabaseSetting("url").isBlank()) {
                    settings.setDatabaseSetting("type", "H2");
                    settings.setDatabaseSetting("user", "admin");
                    settings.setDatabaseSetting("password", "admin");
                    String url = Utils.H2_PREFIX + Utils.H2_DB_PATH;
                    settings.setDatabaseSetting("url", url);
                    settings.save();
                }

                database = new Database(settings);
                mainWindow = new MainWindow(this, settings, database, primaryStage);

                userController = new UserController(database);
                if (!userController.hasUsers()) {
                    showRegistrationWindow();
                } else {
                    showLoginWindow();
                }
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException | ClassNotFoundException | SQLException e) {
                showErrorDialog("Startup Error", "Error occurred during startup:\n" + e);
            }
        });

        splashWindow.show();
        startUp.start();
    }

    public void showRegistrationWindow() {
        if (registerUserWindow == null) {
            registerUserWindow = new RegisterUserWindow(mainWindow, database);
        }
        registerUserWindow.show();
    }

    public void showLoginWindow() {
        if (loginUserWindow == null) {
            loginUserWindow = new LoginUserWindow(mainWindow, database);
        }
        loginUserWindow.show();
    }

    private void createFolders() {
        System.out.println("creating folders:");
        createFolder(Utils.APP_FOLDER);
        createFolder(Utils.LOG_FOLDER);
        createFolder(Utils.TEMP_FOLDER);
        createFolder(Utils.DATA_FOLDER);
        createFolder(Utils.IMAGE_FOLDER);
        createFolder(Utils.EXPENSES_IMAGE_FOLDER);
        createFolder(Utils.REVENUES_IMAGE_FOLDER);
        createFolder(Utils.CASH_TRANSACTIONS_IMAGE_FOLDER);
        System.out.println("------ ok ------\n");
    }

    private void createFolder(String path) {
        System.out.print(path + "...");
        File dir = new File(path);
        boolean success = false;
        if (!dir.exists()) {
            success = dir.mkdirs();
            System.out.println(success ? "ok" : "failed");
        } else {
            System.out.println("already exists");
        }
    }

    private void copyFiles() {
        System.out.println("copying files:");
        copyFile("settings.xml", Utils.SETTINGS_PATH);
        copyFile("splash.mp4", Utils.SPLASH_MEDIA_PATH);
        System.out.println("------ ok ------\n");
    }

    private void copyFile(String srcPath, String destPath) {
        System.out.print(srcPath + "...");
        File dest = new File(destPath);
        if (!dest.exists()) {
            try {
                InputStream ins = AppMain.class.getResourceAsStream(srcPath);
                FileUtils.copyInputStreamToFile(ins, dest);
                System.out.println("ok");
            } catch (IOException e) {
                System.out.println("failed [" + e + "]");
            }
        } else {
            System.out.println("already exists");
        }
    }

    private void showErrorDialog(String header, String content) {
        if (errorDialog == null) {
            errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Error");
        }
        errorDialog.setHeaderText(header);
        errorDialog.setContentText(content);
        errorDialog.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
