package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.User;
import org.gemseeker.sms.data.controllers.UserController;

import java.util.Objects;

/**
 *
 * @author Gem
 */
public class EditUserWindow extends AbstractWindow {

    @FXML private TextField tfUsername;
    @FXML private TextField tfFullname;
    @FXML private TextField tfDesignation;
    @FXML private TextField tfPassword;
    @FXML private TextField tfRePassword;
    @FXML private Label lblError;
    @FXML private Button btnRegister;

    private final MainWindow mainWindow;
    private final UserController userController;
    private final CompositeDisposable disposables;

    private User mUser;

    public EditUserWindow(MainWindow mainWindow, Database database) {
        super("Edit User Information", EditUserWindow.class.getResource("register_user.fxml"), null, null);
        this.mainWindow = mainWindow;
        this.userController = new UserController(database);
        this.disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(MainWindow.class.getResourceAsStream("logo_v3.png"))));
    }

    @Override
    protected void onFxmlLoaded() {
        btnRegister.setText("Update");
        btnRegister.setOnAction(evt -> {
            if (validated()) updateAndClose();
        });
    }

    private boolean validated() {
        lblError.setText("");
        boolean valid = false;

        if (tfUsername.getText().isBlank() || tfFullname.getText().isBlank() || tfDesignation.getText().isBlank() ||
                tfPassword.getText().isBlank() || tfRePassword.getText().isBlank()) {
            lblError.setText("Empty Username, Full Name, Designation, and/or Password field.");
        } else if (!tfPassword.getText().equals(tfRePassword.getText())) {
            lblError.setText("Password doesn't match.");
        } else {
            valid = true;
        }
        return valid;
    }

    private void updateAndClose() {
        disposables.add(Single.fromCallable(() -> {
            mUser.setUsername(ViewUtils.normalize(tfUsername.getText()));
            mUser.setFullname(ViewUtils.normalize(tfFullname.getText()));
            mUser.setDesignation(ViewUtils.normalize(tfDesignation.getText()));
            mUser.setPassword(ViewUtils.normalize(tfPassword.getText()));
            if (!userController.hasUsers()) {
                mUser.setRole("admin");
            }
            return userController.update(mUser);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            if (success) {
                showInfoDialog("Success", "User information updated successfully.");
            }
            close();
            mainWindow.updateUser(mUser.getId());
        }, err -> {
            showErrorDialog("Database Error", "Error while updating user's information.\n" + err);
        }));
    }

    public void show(User user) {
        if (user == null) {
            showWarningDialog("Invalid Action", "User is null!");
            return;
        }
        mUser = user;
        super.show();
    }

    @Override
    protected void onShow() {
        clearFields();
        tfFullname.setText(mUser.getFullname());
        tfDesignation.setText(mUser.getDesignation());
        tfUsername.setText(mUser.getUsername());
        tfPassword.setText(mUser.getPassword());
        tfRePassword.setText(mUser.getPassword());
    }

    @Override
    protected void onClose() {
        clearFields();
        mUser = null;
    }

    private void clearFields() {
        tfUsername.clear();
        tfFullname.clear();
        tfDesignation.clear();
        tfPassword.clear();
        tfRePassword.clear();
        lblError.setText("");
    }

    public void dispose() {
        disposables.dispose();
    }
}
