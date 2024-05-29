package org.gemseeker.sms.views.panels.maps;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class TowerPointsPainter extends WaypointPainter<TowerWayPoint> {
    @Override
    protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {
        for (TowerWayPoint towerWayPoint : getWaypoints()) {
            Point2D point = map.getTileFactory().geoToPixel(towerWayPoint.getPosition(), map.getZoom());
            Rectangle rectangle = map.getViewportBounds();
            int x = (int) (point.getX() - rectangle.getX());
            int y = (int) (point.getY() - rectangle.getY());
            JButton button = towerWayPoint.getButton();
            button.setLocation(x - button.getWidth() / 2, y - button.getHeight() / 2);
        }
    }
}
