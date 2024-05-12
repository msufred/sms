package org.gemseeker.sms.views.panels.maps;

import com.gluonhq.maps.MapLayer;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.gemseeker.sms.data.Tower;

import java.util.LinkedHashMap;

public class LineLayer extends MapLayer {

    private final ObservableList<TowerPoint> towers;
    private final LinkedHashMap<Integer, Line> lines = new LinkedHashMap<>();

    public LineLayer(ObservableList<TowerPoint> towers) {
        this.towers = towers;
        for (TowerPoint tower : towers) {
            // create Line for each tower
            Line line = new Line();
            line.setStrokeWidth(2);
            line.setStroke(Color.ORANGE);
            lines.put(tower.getChildTower().getId(), line);
            getChildren().add(line);
        }
    }

    @Override
    protected void layoutLayer() {
        for (TowerPoint tower : towers) {
            Line line = lines.get(tower.getChildTower().getId());

            Tower child = tower.getChildTower();
            Tower parent = tower.getParentTower();

            Point2D start = getMapPoint(child.getLatitude(), child.getLongitude());
            Point2D end;
            if (parent != null) {
                end = getMapPoint(parent.getLatitude(), parent.getLongitude());
            } else {
                end = getMapPoint(SourceLayer.LATITUDE, SourceLayer.LONGITUDE);
            }

            line.setStartX(start.getX());
            line.setStartY(start.getY());
            line.setEndX(end.getX());
            line.setEndY(end.getY());
        }
    }
}
