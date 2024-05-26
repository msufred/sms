package org.gemseeker.sms.views;

import io.reactivex.disposables.CompositeDisposable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.gemseeker.sms.data.Account;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Product;
import org.gemseeker.sms.data.controllers.PurchaseBillingController;

public class AddPurchaseWindow extends AbstractWindow {

    @FXML RadioButton rbAccount;
    @FXML RadioButton rbWalkIn;
    @FXML private ComboBox<Account> cbAccounts;
    @FXML private TextField tfClientName;
    @FXML private ComboBox<Product> cbItems;
    @FXML private TextField tfQty;
    @FXML private Button btnAddItem;
    @FXML private Button btnRemoveItem;
    @FXML private ListView<Product> productsList;

    private final PurchaseBillingController purchaseBillingController;
    private final CompositeDisposable disposables;

    public AddPurchaseWindow(Database database, Stage owner) {
        super("Purchase Order", AddPurchaseWindow.class.getResource("add_purchase.fxml"), null, owner);
        purchaseBillingController = new PurchaseBillingController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {

    }

    @Override
    protected void onShow() {

    }

    @Override
    protected void onClose() {

    }
}
