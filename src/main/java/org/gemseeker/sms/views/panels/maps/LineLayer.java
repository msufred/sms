package org.gemseeker.sms.views.panels.maps;

import com.gluonhq.maps.MapLayer;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.gemseeker.sms.data.Tower;

import java.util.LinkedHashMap;

public class LineLayer extends MapLayer {

    private final ObservableList<Tower> towers;
    private final LinkedHashMap<Integer, Line> lines = new LinkedHashMap<>();

    public LineLayer(ObservableList<Tower> towers) {
        this.towers = towers;
        for (Tower tower : towers) {
            // create Line for each tower
            Line line = new Line();
            line.setStrokeWidth(2);
            line.setStroke(Color.ORANGE);
            lines.put(tower.getId(), line);
            getChildren().add(line);
        }
    }

    @Override
    protected void layoutLayer() {
        for (Tower tower : towers) {
            Tower parent = null;
            for (Tower t : towers) {
                if (t.getId() == tower.getParentTowerId()) {
                    parent = t;
                    break;
                }
            }

            if (parent != null) {
                System.out.println("Parent not null");
                Point2D pStart = getMapPoint(parent.getLatitude(), parent.getLongitude());
                Point2D pEnd = getMapPoint(tower.getLatitude(), tower.getLongitude());
                Line line = lines.get(tower.getId());
                if (line != null) {
                    line.setVisible(true);
                    line.setStartX(pStart.getX());
                    line.setStartY(pStart.getY());
                    line.setEndX(pEnd.getX());
                    line.setEndY(pEnd.getY());
                }
            }
        }
    }
}
