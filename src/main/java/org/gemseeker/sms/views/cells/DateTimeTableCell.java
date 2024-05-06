package org.gemseeker.sms.views.cells;

import javafx.scene.control.TableCell;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeTableCell<T> extends TableCell<T, LocalDateTime> {
    @Override
    protected void updateItem(LocalDateTime item, boolean empty) {
        if (item == null || empty) {
            setText("");
            setGraphic(null);
        } else {
            setText(item.format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss")));
        }
    }
}
