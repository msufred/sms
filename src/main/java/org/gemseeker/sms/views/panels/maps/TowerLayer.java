package org.gemseeker.sms.views.panels.maps;

import com.gluonhq.maps.MapLayer;
import io.github.msufred.feathericons.SVGIcon;
import io.github.msufred.feathericons.TriangleIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.gemseeker.sms.data.Tower;
import org.gemseeker.sms.views.icons.CircleFilledIcon;
import org.gemseeker.sms.views.icons.PentagonIcon;
import org.gemseeker.sms.views.icons.StarFilledIcon;
import org.gemseeker.sms.views.icons.TriangleFilledIcon;

import java.util.LinkedHashMap;
import java.util.Objects;

public class TowerLayer extends MapLayer {

    private final ObservableList<Marker> markers = FXCollections.observableArrayList();

    public TowerLayer(ObservableList<Tower> towers) {
        for (Tower t : towers) {
            Marker marker = new Marker(t);
            getChildren().add(marker);
            markers.add(marker);
        }
    }

    @Override
    protected void layoutLayer() {
        for (Marker marker : markers) {
            Point2D point2D = getMapPoint(marker.tower.getLatitude(), marker.tower.getLongitude());
            marker.setTranslateX(point2D.getX() - (marker.SIZE / 2));
            marker.setTranslateY(point2D.getY() - (marker.SIZE / 2));
        }
    }

    private static class Marker extends VBox {
        final double SIZE = 18;
        final Tower tower;
        public Marker(Tower tower) {
            this.tower = tower;
            SVGIcon icon;
            if (Objects.equals(tower.getType(), Tower.TYPE_ACCESS_POINT)) {
                icon = new PentagonIcon(SIZE);
                icon.setStyle("-fx-background-color: #c2410c");
            } else if (Objects.equals(tower.getType(), Tower.TYPE_RELAY)) {
                icon = new TriangleFilledIcon(SIZE);
                icon.setStyle("-fx-background-color: #eab308");
            } else if (Objects.equals(tower.getType(), Tower.TYPE_DEFAULT)){
                icon = new CircleFilledIcon(SIZE);
                icon.setStyle("-fx-background-color: #15803d");
            } else {
                icon = new StarFilledIcon(SIZE + 2);
                icon.setStyle("-fx-background-color: #dc2626");
            }
            Tooltip.install(icon, new Tooltip(tower.getName()));
            getChildren().add(icon);
        }
    }
}
