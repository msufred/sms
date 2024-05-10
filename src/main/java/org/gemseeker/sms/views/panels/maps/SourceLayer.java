package org.gemseeker.sms.views.panels.maps;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SourceLayer extends MapLayer {

    private final MapPoint source;
    private final Circle marker;

    public SourceLayer() {
        source = new MapPoint(6.34137, 124.72314);
        marker = new Circle(5, Color.RED);
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
