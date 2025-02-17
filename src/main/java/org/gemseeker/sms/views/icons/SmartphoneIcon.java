package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class SmartphoneIcon extends SVGIcon {

    public SmartphoneIcon() {
        super(DEFAULT_SIZE * 0.75, DEFAULT_SIZE);
    }

    public SmartphoneIcon(double size) {
        super(size * 0.75, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M17,1H7C5.346,1,4,2.346,4,4v16c0,1.654,1.346,3,3,3h10c1.654,0,3-1.346,3-3V4C20,2.346," +
                "18.654,1,17,1z M18,20 c0,0.552-0.448,1-1,1H7c-0.551,0-1-0.448-1-1V4c0-0.551,0.449-1,1-1h10c0.552," +
                "0,1,0.449,1,1V20z M12.01,17H12c-0.552,0-0.995,0.447-0.995,1s0.453,1,1.005,1c0.553,0,1-0.447," +
                "1-1S12.563,17,12.01,17z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "smartphone-icon";
    }
}
