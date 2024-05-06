package org.gemseeker.sms.views.cells;

import javafx.scene.control.TableCell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTableCell<T> extends TableCell<T, LocalDate> {
    @Override
    protected void updateItem(LocalDate item, boolean empty) {
        if (item == null || empty) {
            setText("");
            setGraphic(null);
        } else {
            setText(item.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
        }
    }
}
