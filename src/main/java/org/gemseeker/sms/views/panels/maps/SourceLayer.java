package org.gemseeker.sms.views.panels.maps;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import io.github.msufred.feathericons.StarIcon;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.gemseeker.sms.views.icons.StarFilledIcon;

public class SourceLayer extends MapLayer {

    public static final double LATITUDE = 6.34137;
    public static final double LONGITUDE = 124.72314;

    private final MapPoint source;
    private final Marker marker;

    public SourceLayer() {
        source = new MapPoint(LATITUDE, LONGITUDE);
        marker = new Marker();
        Tooltip.install(marker, new Tooltip("MAIN TOWER"));
        getChildren().add(marker);
    }

    @Override
    protected void layoutLayer() {
        Point2D point = getMapPoint(source.getLatitude(), source.getLongitude());
        marker.setTranslateX(point.getX());
        marker.setTranslateY(point.getY());
    }

    private static class Marker extends StackPane {
        public Marker() {
            StarFilledIcon star = new StarFilledIcon(20);
            star.setStyle("-fx-background-color: #dc2626");
            getChildren().add(star);
        }
    }
}
