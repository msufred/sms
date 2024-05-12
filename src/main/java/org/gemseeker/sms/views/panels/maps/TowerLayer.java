package org.gemseeker.sms.views.panels.maps;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.gemseeker.sms.data.Tower;

import java.util.LinkedHashMap;
import java.util.Objects;

public class TowerLayer extends MapLayer {

    private final ObservableList<Tower> towers;
    private final LinkedHashMap<Integer, Circle> markers = new LinkedHashMap<>();

    public TowerLayer(ObservableList<Tower> towers) {
        this.towers = towers;
        for (Tower t : towers) {
            Circle circle = new Circle();
            if (Objects.equals(t.getType(), Tower.TYPE_ACCESS_POINT)) {
                circle.setFill(Color.CRIMSON);
                circle.setRadius(8);
            } else if (Objects.equals(t.getType(), Tower.TYPE_RELAY)) {
                circle.setFill(Color.CORAL);
                circle.setRadius(8);
            } else {
                circle.setFill(Color.DARKGRAY);
                circle.setRadius(5);
            }

            Tooltip.install(circle, new Tooltip(t.getName()));
            markers.put(t.getId(), circle);
            getChildren().add(circle);
        }
    }

    @Override
    protected void layoutLayer() {
        for (Tower t : towers) {
            Circle marker = markers.get(t.getId());
            if (marker != null) {
                Point2D point = getMapPoint(t.getLatitude(), t.getLongitude());
                marker.setTranslateX(point.getX());
                marker.setTranslateY(point.getY());
            }
        }
    }

    private static class TowerNode extends VBox {
        final Circle circle;
        final Label label;

        public TowerNode(Tower tower) {
            circle = new Circle(6, Color.CRIMSON);
            label = new Label();
        }
    }
}
