package org.gemseeker.sms.views.cells;

import javafx.scene.control.TableCell;
import org.gemseeker.sms.views.ViewUtils;
import org.gemseeker.sms.views.icons.PesoIcon;

public class AmountTableCell<T> extends TableCell<T, Double> {
    @Override
    protected void updateItem(Double amount, boolean empty) {
        if (empty || amount == 0) {
            setText("");
            setGraphic(null);
        } else {
            setText(ViewUtils.toStringMoneyFormat(amount));
            PesoIcon pesoIcon = new PesoIcon(12);
            pesoIcon.getStyleClass().add("tag-normal");
            setGraphic(pesoIcon);
        }
    }
}
