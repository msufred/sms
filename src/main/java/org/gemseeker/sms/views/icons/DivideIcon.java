package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class DivideIcon extends SVGIcon {

    public DivideIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public DivideIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,9c1.654,0,3-1.346,3-3s-1.346-3-3-3S9,4.346,9,6S10.346,9,12,9z M12,5c0.552,0,1,0.449," +
                "1,1s-0.448,1-1,1 c-0.551,0-1-0.449-1-1S11.449,5,12,5z M19,11H5c-0.552,0-1,0.448-1,1c0,0.553,0.44" +
                "8,1,1,1h14c0.553,0,1-0.447,1-1C20,11.448,19.553,11,19,11z M12,15c-1.654,0-3,1.346-3,3s1.346,3,3," +
                "3s3-1.346,3-3S13.654,15,12,15z M12,19c-0.551,0-1-0.448-1-1s0.449-1,1-1 c0.552,0,1,0.448,1,1S12.5" +
                "52,19,12,19z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "divide-icon";
    }
}
