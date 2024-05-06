package org.gemseeker.sms.views;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Product;
import org.gemseeker.sms.data.controllers.ProductController;

/**
 *
 * @author Gem
 */
public class EditProductWindow extends AbstractWindow {

    @FXML private TextField tfName;
    @FXML private TextField tfPrice;
    @FXML private TextField tfStocks;
    @FXML private TextField tfSerial;
    @FXML private Label lblError;
    @FXML private Button btnSave;

    private final ProductController productController;
    private final CompositeDisposable disposables;

    private Product mProduct;

    public EditProductWindow(Database database) {
        super("Edit Product", EditProductWindow.class.getResource("add_product.fxml"), null, null);
        productController = new ProductController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {
        btnSave.setText("Update Product");
        ViewUtils.setAsNumericalTextField(tfPrice);
        ViewUtils.setAsIntegerTextField(tfStocks);

        btnSave.setOnAction(evt -> {
            if (validated()) saveAndClose();
        });
    }

    public void showAndWait(Product product) {
        if (product == null) {
            showWarningDialog("Invalid", "No selected Product entry. Try again.");
            return;
        }
        mProduct = product;
        showAndWait();
    }

    @Override
    protected void onShow() {
        clearFields();
        tfName.setText(mProduct.getName());
        tfPrice.setText(String.format("%.2f", mProduct.getPrice()));
        tfStocks.setText(mProduct.getStock() + "");
        tfSerial.setText(mProduct.getSerial());
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
            mProduct.setName(ViewUtils.normalize(tfName.getText()));
            mProduct.setSerial(ViewUtils.normalize(tfSerial.getText()));
            String priceStr = tfPrice.getText();
            mProduct.setPrice(priceStr.isBlank() ? 0.0 : Double.parseDouble(priceStr.trim()));
            String stockStr = tfStocks.getText();
            mProduct.setStock(stockStr.isBlank() ? 0 : Integer.parseInt(stockStr.trim()));
            return productController.update(mProduct);
        }).subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(success -> {
            if (!success) showWarningDialog("Failed", "Failed to update Product entry.");
            close();
        }, err -> {
            showErrorDialog("Database Error", "Error while updating Product entry.\n" + err);
        }));
    }

    @Override
    protected void onClose() {
        clearFields();
        mProduct = null;
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
