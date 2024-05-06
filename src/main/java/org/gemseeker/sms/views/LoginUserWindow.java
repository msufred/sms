package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.UserController;

/**
 *
 * @author Gem
 */
public class LoginUserWindow extends AbstractWindow {

    @FXML private TextField tfUsername;
    @FXML private TextField tfPassword;
    @FXML private Label lblError;
    @FXML private Button btnLogin;

    private final MainWindow mainWindow;
    private final UserController userController;
    private final CompositeDisposable disposables;

    public LoginUserWindow(MainWindow mainWindow, Database database) {
        super("Login", LoginUserWindow.class.getResource("login_user.fxml"), null, null);
        this.mainWindow = mainWindow;
        this.userController = new UserController(database);
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.setResizable(false);
    }

    @Override
    protected void onFxmlLoaded() {
        btnLogin.setOnAction(evt -> {
            if (validated()) loginAndClose();
        });
    }

    private boolean validated() {
        if (tfUsername.getText().isBlank() || tfPassword.getText().isBlank()) {
            lblError.setText("Empty Username and/or Password field.");
            return false;
        } else {
            return true;
        }
    }

    private void loginAndClose() {
        disposables.add(Single.fromCallable(() -> {
            String username = ViewUtils.normalize(tfUsername.getText());
            String password = ViewUtils.normalize(tfPassword.getText());
            return userController.getUser(username, password);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(user -> {
            // always assumes user is not null, open MainWindow
            mainWindow.setUserId(user.getId());
            close();
            mainWindow.show();
        }, err -> {
            // Error is thrown if User is null. Inform user.
            lblError.setText("Invalid Username and/or Password. Try again.");
        }));
    }

    @Override
    protected void onShow() {
        // empty
    }

    @Override
    protected void onClose() {
        tfUsername.clear();
        tfPassword.clear();
        lblError.setText("");
    }

    public void dispose() {
        disposables.dispose();
    }
}
