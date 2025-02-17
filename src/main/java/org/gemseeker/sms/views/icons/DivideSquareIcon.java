package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class DivideSquareIcon extends SVGIcon {

    public DivideSquareIcon() {
        super();
    }

    public DivideSquareIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,2H5C3.346,2,2,3.346,2,5v14c0,1.654,1.346,3,3,3h14c1.654,0,3-1.346,3-3V5C22," +
                "3.346,20.654,2,19,2z M20,19 c0,0.552-0.448,1-1,1H5c-0.551,0-1-0.448-1-1V5c0-0.551,0.449" +
                "-1,1-1h14c0.552,0,1,0.449,1,1V19z M16,11H8c-0.552,0-1,0.448-1,1c0,0.553,0.448,1,1,1h8c0" +
                ".553,0,1-0.447,1-1C17,11.448,16.553,11,16,11z M11.29,15.29C11.11,15.479,11,15.74,11,16s0" +
                ".11,0.52,0.29,0.71C11.48,16.89,11.74,17,12,17c0.26,0,0.52-0.11,0.71-0.29 C12.89,16.52,13" +
                ",16.26,13,16s-0.11-0.521-0.29-0.71C12.33,14.92,11.67,14.92,11.29,15.29z M12,9c0.26,0,0." +
                "52-0.11,0.71-0.29C12.89,8.52,13,8.26,13,8c0-0.26-0.11-0.52-0.29-0.71c-0.38-0.37-1.04-0." +
                "37-1.42,0 C11.11,7.48,11,7.74,11,8c0,0.26,0.11,0.52,0.29,0.71C11.48,8.89,11.74,9,12,9z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "divide-square-icon";
    }
}
