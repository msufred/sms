package org.gemseeker.sms.views.panels.maps;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SourceLayer extends MapLayer {

    public static final double LATITUDE = 6.34137;
    public static final double LONGITUDE = 124.72314;

    private final MapPoint source;
    private final Circle marker;

    public SourceLayer() {
        source = new MapPoint(LATITUDE, LONGITUDE);
        marker = new Circle(8, Color.RED);
        Tooltip.install(marker, new Tooltip("MAIN TOWER"));
        getChildren().add(marker);
    }

    @Override
    protected void layoutLayer() {
        Point2D point = getMapPoint(source.getLatitude(), source.getLongitude());
        marker.setTranslateX(point.getX());
        marker.setTranslateY(point.getY());
    }
}
