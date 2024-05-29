package org.gemseeker.sms.views.panels.maps;

import org.gemseeker.sms.data.Tower;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;

public class TowerWayPoint extends DefaultWaypoint {

    private JButton button;

    private final Tower tower;

    public TowerWayPoint(Tower tower, GeoPosition pos) {
        super(pos);
        this.tower = tower;

        button = new JButton(tower.getName());
        button.setSize(24, 24);
        button.setPreferredSize(new Dimension(24, 24));
        button.setVisible(true);
    }

    public JButton getButton() {
        return button;
    }
}
