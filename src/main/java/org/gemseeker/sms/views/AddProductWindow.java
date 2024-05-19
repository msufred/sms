package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Product;
import org.gemseeker.sms.data.controllers.ProductController;

/**
 *
 * @author Gem
 */
public class AddProductWindow extends AbstractWindow {

    @FXML private TextField tfName;
    @FXML private TextField tfPrice;
    @FXML private TextField tfStocks;
    @FXML private TextField tfSerial;
    @FXML private Label lblError;
    @FXML private Button btnSave;

    private final ProductController productController;
    private final CompositeDisposable disposables;

    public AddProductWindow(Database database, Stage owner) {
        super("Add Product", AddProductWindow.class.getResource("add_product.fxml"), null, owner);
        productController = new ProductController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void onFxmlLoaded() {
        ViewUtils.setAsNumericalTextField(tfPrice);
        ViewUtils.setAsIntegerTextField(tfStocks);

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
    }

    @Override
    protected void onShow() {
        clearFields();
    }

    private boolean validated() {
        lblError.setVisible(false);
        if (tfName.getText().isBlank() || tfPrice.getText().isBlank() || tfStocks.getText().isBlank()) {
            lblError.setVisible(true);
            lblError.setText("Please fill-up empty field(s).");
            return false;
        }
        return true;
    }

    private void saveAndClose() {
        disposables.add(Single.fromCallable(() -> {
            Product product = new Product();
            product.setName(ViewUtils.normalize(tfName.getText()));
            product.setSerial(ViewUtils.normalize(tfSerial.getText()));
            String priceStr = tfPrice.getText();
            product.setPrice(priceStr.isBlank() ? 0.0 : Double.parseDouble(priceStr.trim()));
            String stockStr = tfStocks.getText();
            product.setStock(stockStr.isBlank() ? 0 : Integer.parseInt(stockStr.trim()));
            return productController.insert(product);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            if (!success) showWarningDialog("Failed", "Failed to add new Product entry.");
            close();
        }, err -> {
            showErrorDialog("Database Error", "Error while adding new Product entry.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
    }

    private void clearFields() {
        lblError.setVisible(false);
        tfName.clear();
        tfPrice.clear();
        tfStocks.clear();
        tfSerial.clear();
    }

    public void dispose() {
        disposables.dispose();
    }
}
