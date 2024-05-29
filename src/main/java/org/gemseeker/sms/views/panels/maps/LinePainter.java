package org.gemseeker.sms.views.panels.maps;

import javafx.collections.ObservableList;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Point2D;

public class LinePainter implements Painter<JXMapViewer> {

    private static final Color LINE_COLOR = Color.ORANGE;
    private static final boolean ANTI_ALIAS = true;

    private final ObservableList<TowerPoint> towers;

    public LinePainter(ObservableList<TowerPoint> towers) {
        this.towers = towers;
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer mapViewer, int width, int height) {
        //g = (Graphics2D) g.create();

        // convert from viewport to world bitmap
        Rectangle rectangle = mapViewer.getViewportBounds();
        g.translate(-rectangle.x, -rectangle.y);

        if (ANTI_ALIAS) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4));

        drawLine(g, mapViewer);
        g.setColor(LINE_COLOR);
        g.setStroke(new BasicStroke(2));

        drawLine(g, mapViewer);
        g.dispose();
    }

    private void drawLine(Graphics2D g, JXMapViewer mapViewer) {
        for (TowerPoint pair : towers) {
            if (pair.getParentTower() != null && pair.getChildTower() != null) {
                // convert GeoPosition to pixel coordinate
                GeoPosition startPos = new GeoPosition(pair.getChildTower().getLatitude(), pair.getChildTower().getLongitude());
                GeoPosition endPos = new GeoPosition(pair.getParentTower().getLatitude(), pair.getParentTower().getLongitude());
                Point2D start = mapViewer.getTileFactory().geoToPixel(startPos, mapViewer.getZoom());
                Point2D end = mapViewer.getTileFactory().geoToPixel(endPos, mapViewer.getZoom());
                g.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
            }
        }
    }
}
