package org.gemseeker.sms.views.cells;

import javafx.scene.control.TableCell;
import org.gemseeker.sms.views.ViewUtils;

public class TagTableCell<T> extends TableCell<T, String> {

    @Override
    protected void updateItem(String tag, boolean empty) {
        super.updateItem(tag, empty);
        setText("");
        if (!empty && tag != null) {
            setGraphic(ViewUtils.createTag(tag));
        } else {
            setGraphic(null);
        }
    }
}

