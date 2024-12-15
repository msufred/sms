package org.gemseeker.sms.views.components;

import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Like a Pie chart but in horizontal bar orientation.
 */
public class HorizontalBarChart extends GridPane {

    private final ArrayList<String> styles = new ArrayList<>(Arrays.asList(
            "tag-normal",
            "tag-turquoise",
            "tag-greensea",
            "tag-sunflower",
            "tag-orange",
            "tag-emerald",
            "tag-nephritis",
            "tag-carrot",
            "tag-pumpkin",
            "tag-peterriver",
            "tag-belizehole",
            "tag-alizarin",
            "tag-pomegranate",
            "tag-amethyst",
            "tag-wisteria",
            "tag-midnight",
            "tag-silver"
    ));

    private final HashMap<String, Double> valueMap = new HashMap<>();
    private final Random random = new Random();

    private int currCol = 0;

    public HorizontalBarChart() {
    }

    public void addItem(String label, double percentage) {
        System.out.println("Percentage: " + percentage);
        HBox hBox = new HBox(new Label(label));
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.getStyleClass().add(styles.get(random.nextInt(styles.size())));
        ColumnConstraints constraints = new ColumnConstraints(percentage);
        getColumnConstraints().add(currCol, constraints);
        addColumn(currCol++, hBox);
    }

    public void clear() {
        valueMap.clear();
        getChildren().clear();
        currCol = 0;
    }
}
