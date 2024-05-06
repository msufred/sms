package org.gemseeker.sms.views.cells;

import io.github.msufred.feathericons.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;

public class StatusTableCell<T> extends TableCell<T, String> {

    @Override
    protected void updateItem(String status, boolean empty) {
        if (status == null || empty) {
            setText("");
            setGraphic(null);
        } else {
            SVGIcon icon = null;
            String styleClass;
            switch (status.toLowerCase()) {
                case "active":
                    icon = new SmileIcon(12);
                    styleClass = "tag-emerald";
                    break;
                case "inactive":
                    icon = new FrownIcon(12);
                    styleClass = "tag-normal";
                    break;
                case "deactivated":
                    icon = new XOctagonIcon(12);
                    styleClass = "tag-alizarin";
                    break;
                case "maintenance":
                    icon = new ToolIcon(12);
                    styleClass = "tag-pumpkin";
                    break;
                case "out of order":
                    icon = new AlertOctagonIcon(12);
                    styleClass = "tag-orange";
                    break;
                default:
                    styleClass = "tag-normal";
                    icon = null;
            }
            if (icon != null) icon.getStyleClass().add(styleClass);
            Label label = new Label(status);
            label.setGraphic(icon);
            setText("");
            setGraphic(label);
        }
    }
}
