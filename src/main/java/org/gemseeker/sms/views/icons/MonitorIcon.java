package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MonitorIcon extends SVGIcon {

    public MonitorIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public MonitorIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,2H4C2.346,2,1,3.346,1,5v10c0,1.654,1.346,3,3,3h7v2H8c-0.552,0-1,0.447-1,1s0.448," +
                "1,1,1h8c0.553,0,1-0.447,1-1 s-0.447-1-1-1h-3v-2h7c1.654,0,3-1.346,3-3V5C23,3.346,21.654,2,20," +
                "2z M21,15c0,0.552-0.448,1-1,1H4c-0.551,0-1-0.448-1-1V5 c0-0.551,0.449-1,1-1h16c0.552,0,1,0.449,1,1V15z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "monitor-icon";
    }
}
