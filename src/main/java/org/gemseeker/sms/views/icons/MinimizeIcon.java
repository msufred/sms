package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MinimizeIcon extends SVGIcon {

    public MinimizeIcon() {
        super();
    }

    public MinimizeIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M18,9h3c0.553,0,1-0.448,1-1s-0.447-1-1-1h-3c-0.552,0-1-0.449-1-1V3c0-" +
                "0.552-0.447-1-1-1s-1,0.448-1,1v3 C15,7.654,16.346,9,18,9z M6,15H3c-0.552,0-1," +
                "0.447-1,1s0.448,1,1,1h3c0.551,0,1,0.448,1,1v3c0,0.553,0.448,1,1,1s1-0.447," +
                "1-1v-3 C9,16.346,7.654,15,6,15z M8,2C7.448,2,7,2.448,7,3v3c0,0.551-0.449," +
                "1-1,1H3C2.448,7,2,7.448,2,8s0.448,1,1,1h3c1.654,0,3-1.346,3-3V3 C9,2.448," +
                "8.552,2,8,2z M21,15h-3c-1.654,0-3,1.346-3,3v3c0,0.553,0.447,1,1,1s1-0.447," +
                "1-1v-3c0-0.552,0.448-1,1-1h3c0.553,0,1-0.447,1-1 S21.553,15,21,15z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "minimize-icon";
    }
}
