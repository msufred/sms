package org.gemseeker.sms.views.panels.maps;

import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Tower;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Point2D;

public class TowerPainter implements Painter<JXMapViewer> {

    private final ObservableList<Tower> towers;

    public TowerPainter(ObservableList<Tower> towers) {
        this.towers = towers;
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer mapViewer, int width, int height) {
        // convert from viewport to world bitmap
        Rectangle rectangle = mapViewer.getViewportBounds();
        g.translate(-rectangle.x, -rectangle.y);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Tower tower : towers) {
            // reference to original stroke and color of Graphics2D
            Color origColor = g.getColor();
            Stroke origStroke = g.getStroke();

            // convert GeoPosition to pixel coordinate
            GeoPosition pos = new GeoPosition(tower.getLatitude(), tower.getLongitude());
            Point2D point = mapViewer.getTileFactory().geoToPixel(pos, mapViewer.getZoom());

            switch (tower.getType()) {
                case Tower.TYPE_ACCESS_POINT -> drawPentagon(g, point);
                case Tower.TYPE_RELAY -> drawTriangle(g, point);
                case Tower.TYPE_DEFAULT -> drawCircle(g, point);
                default -> drawStar(g, point);
            }

            int strLength = (int) g.getFontMetrics().getStringBounds(tower.getName(), g).getWidth();
            g.setColor(Color.BLACK);
            g.drawString(tower.getName(), (int) point.getX() - strLength / 2, (int) point.getY() + 20);

            // reset
            g.setColor(origColor);
            g.setStroke(origStroke);
        }
        g.dispose();
    }

    private void drawPentagon(Graphics2D g, Point2D point) {
        drawPolygon(g, point, 5, 10, Color.decode("#c2410c"));
    }

    private void drawTriangle(Graphics2D g, Point2D point) {
        drawPolygon(g, point, 3, 12, Color.decode("#eab308"));
    }

    private void drawCircle(Graphics2D g, Point2D point) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawOval((int) (point.getX() - 10), (int) (point.getY() - 10), 20, 20);

        g.setColor(Color.decode("#15803d"));
        g.fillOval((int) (point.getX() - 10), (int) (point.getY() - 10), 20, 20);
    }

    private void drawStar(Graphics2D g, Point2D point) {
        int radius = 14;
        double spikiness = 0.5;
        int spikes = 5;
        int nPoints = spikes * 2 + 1; // 5 spikes

        int[] x = new int[nPoints];
        int[] y = new int[nPoints];
        for (int i = 0; i < nPoints; i++) {
            double iRadius = (i % 2 == 0) ? radius : radius * spikiness;
            double angle = (double) (i * 360) / (2 * spikes);
            x[i] = (int) (point.getX() + iRadius * Math.cos(Math.toRadians(angle - 90)));
            y[i] = (int) (point.getY() + iRadius * Math.sin(Math.toRadians(angle - 90)));
        }

        g.setColor(Color.decode("#dc2626"));
        g.fillPolygon(x, y, nPoints);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1));
        g.drawPolygon(x, y, nPoints);
    }

    private void drawPolygon(Graphics2D g, Point2D point, int sides, int radius, Color color) {
        int[] x = new int[sides];
        int[] y = new int[sides];
        double thetaInc = 2 * Math.PI / sides;
        double theta = (sides % 2 == 0) ? thetaInc : -Math.PI/2;
        for (int i = 0; i < sides; i++) {
            x[i] = (int) (point.getX() + radius * Math.cos(theta));
            y[i] = (int) (point.getY() + radius * Math.sin(theta));
            theta += thetaInc;
        }

        g.setColor(color);
        g.fillPolygon(x, y, sides);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1));
        g.drawPolygon(x, y, sides);
    }

}
