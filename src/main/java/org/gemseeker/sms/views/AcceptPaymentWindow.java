package org.gemseeker.sms.views;

import io.reactivex.disposables.CompositeDisposable;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.PaymentController;

public class AcceptPaymentWindow extends AbstractWindow {

    private final Database database;
    private final PaymentController paymentController;
    private final CompositeDisposable disposables;

    public AcceptPaymentWindow(Database database) {
        super("Accept Payment", AcceptPaymentWindow.class.getResource("accept_payment.fxml"), null, null);
        this.database = database;
        this.paymentController = new PaymentController(database);
        this.disposables = new CompositeDisposable();
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
