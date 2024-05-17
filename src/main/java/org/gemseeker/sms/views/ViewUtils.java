package org.gemseeker.sms.views;

import io.github.msufred.feathericons.SVGIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.gemseeker.sms.views.icons.CircleFilledIcon;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedHashMap;

public class ViewUtils {

    // Tags & Icons
    public static final String TAG_NORMAL = "normal";
    public static final String TAG_TURQUOISE = "turquoise";
    public static final String TAG_GREEN_SEA = "greensea";
    public static final String TAG_ORANGE = "orange";
    public static final String TAG_NEPHRITIS = "nephritis";
    public static final String TAG_PUMPKIN = "pumpkin";
    public static final String TAG_BELIZE_HOLE = "belizehole";
    public static final String TAG_POMEGRANTE = "pomegranate";
    public static final String TAG_WISTERIA = "wisteria";
    public static final String TAG_SILVER = "silver";
    public static final String TAG_ALIZARIN = "alizarin";
    public static final String TAG_CARROT = "carrot";
    public static final String TAG_SUNFLOWER = "sunflower";
    public static final String TAG_EMERALD = "emerald";
    public static final String TAG_PETER_RIVER = "peterriver";
    public static final String TAG_AMETHYST = "amethyst";
    public static final String TAG_MIDNIGHT = "midnight";

    public static LinkedHashMap<String, SVGIcon> getTags() {
        LinkedHashMap<String, SVGIcon> tags = new LinkedHashMap<>();
        tags.put(TAG_NORMAL, createTag(TAG_NORMAL));
        tags.put(TAG_TURQUOISE, createTag(TAG_TURQUOISE));
        tags.put(TAG_GREEN_SEA, createTag(TAG_GREEN_SEA));
        tags.put(TAG_SUNFLOWER, createTag(TAG_SUNFLOWER));
        tags.put(TAG_ORANGE, createTag(TAG_ORANGE));
        tags.put(TAG_EMERALD, createTag(TAG_EMERALD));
        tags.put(TAG_NEPHRITIS, createTag(TAG_NEPHRITIS));
        tags.put(TAG_CARROT, createTag(TAG_CARROT));
        tags.put(TAG_PUMPKIN, createTag(TAG_PUMPKIN));
        tags.put(TAG_PETER_RIVER, createTag(TAG_PETER_RIVER));
        tags.put(TAG_BELIZE_HOLE, createTag(TAG_BELIZE_HOLE));
        tags.put(TAG_ALIZARIN, createTag(TAG_ALIZARIN));
        tags.put(TAG_POMEGRANTE, createTag(TAG_POMEGRANTE));
        tags.put(TAG_AMETHYST, createTag(TAG_AMETHYST));
        tags.put(TAG_WISTERIA, createTag(TAG_WISTERIA));
        tags.put(TAG_MIDNIGHT, createTag(TAG_MIDNIGHT));
        tags.put(TAG_SILVER, createTag(TAG_SILVER));
        return tags;
    }

    public static SVGIcon createTag(String tag) {
        return createTag(tag, 10);
    }

    public static SVGIcon createTag(String tag, double size) {
        CircleFilledIcon circle = new CircleFilledIcon(size);
        String style;
        switch (tag) {
            case TAG_TURQUOISE: style = "tag-turquoise"; break;
            case TAG_GREEN_SEA: style = "tag-greensea"; break;
            case TAG_SUNFLOWER: style = "tag-sunflower"; break;
            case TAG_ORANGE: style = "tag-orange"; break;
            case TAG_EMERALD: style = "tag-emerald"; break;
            case TAG_NEPHRITIS: style = "tag-nephritis"; break;
            case TAG_CARROT: style = "tag-carrot"; break;
            case TAG_PUMPKIN: style = "tag-pumpkin"; break;
            case TAG_PETER_RIVER: style = "tag-peterriver"; break;
            case TAG_BELIZE_HOLE: style = "tag-belizehole"; break;
            case TAG_ALIZARIN: style = "tag-alizarin"; break;
            case TAG_POMEGRANTE: style = "tag-pomegranate"; break;
            case TAG_AMETHYST: style = "tag-amethyst"; break;
            case TAG_WISTERIA: style = "tag-wisteria"; break;
            case TAG_MIDNIGHT: style = "tag-midnight"; break;
            case TAG_SILVER: style = "tag-silver"; break;
            default: style = "tag-normal";
        }
        circle.getStyleClass().add(style);
        return circle;
    }

    // List items

    public static final ObservableList<String> vendoStatusList = FXCollections.observableArrayList(
            "Active", "Inactive", "Maintenance", "Out of Order"
    );


    // TextField utility methods

    public static String normalize(String str) {
        return str.replace("'", "`");
    }

    public static String capitalize(String str) {
        char firstChar = Character.toUpperCase(str.charAt(0));
        return firstChar + str.substring(1).toLowerCase();
    }

    public static void setAsNumericalTextField(TextField...textFields) {
        for (TextField tf : textFields) {
            tf.addEventFilter(KeyEvent.KEY_TYPED, evt -> {
                if (!"0123456789.".contains(evt.getCharacter())) evt.consume();
                String text = tf.getText();
                String chr = evt.getCharacter();
                if (chr.equals(".") && text.contains(".")) {
                    evt.consume();
                }
            });
        }
    }

    public static void setAsIntegerTextField(TextField textField) {
        if (textField != null) {
            textField.addEventFilter(KeyEvent.KEY_TYPED, evt -> {
                if (!"0123456789".contains(evt.getCharacter())) evt.consume();
            });
        }
    }

    public static String getFileExtension(File file) {
        String path = file.getName();
        System.out.println(path);
        return path.substring(path.lastIndexOf(".") + 1);
    }

    public static String getDateDuration(LocalDate from, LocalDate to) {
        int duration;
        String str;
        if (from.getYear() == to.getYear()){
            duration = to.getMonthValue() - from.getMonthValue() + 1;
            str = "Month(s)";
        } else {
            duration = to.getYear() - from.getYear() + 1;
            str = "Year(s)";
        }
        return String.format("%d %s", duration, str);
    }

    public static String toStringMoneyFormat(double amount) {
        String str = String.format("%.2f", amount);
        StringBuilder sb = new StringBuilder();
        int startIndex = str.indexOf('.');
        if (startIndex < 0) startIndex = 0;
        int decimalCount = 0;
        for (int i = startIndex - 1; i >= 0; i--) {
            if (decimalCount == 3) {
                sb.append(',');
                decimalCount = 0;
            }
            sb.append(str.charAt(i));
            decimalCount++;
        }
        return sb.reverse().append(str.substring(startIndex)).toString();
    }

    public static String shortMonthStringValue(int value) {
        return switch (value) {
            case 1 -> "Jan";
            case 2 -> "Feb";
            case 3 -> "Mar";
            case 4 -> "Apr";
            case 5 -> "May";
            case 6 -> "Jun";
            case 7 -> "Jul";
            case 8 -> "Aug";
            case 9 -> "Sep";
            case 10 -> "Oct";
            case 11 -> "Nov";
            default -> "Dec";
        };
    }
}
