package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class LogOutIcon extends SVGIcon {

    public LogOutIcon() {
        super();
    }

    public LogOutIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M9,20H5c-0.551,0-1-0.448-1-1V5c0-0.551,0.449-1,1-1h4c0.552,0,1-0.448,1-1S9.552,2,9," +
                "2H5C3.346,2,2,3.346,2,5v14 c0,1.654,1.346,3,3,3h4c0.552,0,1-0.447,1-1S9.552,20,9,20z " +
                "M21.923,12.382c0.101-0.244,0.101-0.519,0-0.764c-0.051-0.123-0.124-0.233-0.217-0.326l-4.999-4.999 " +
                "c-0.391-0.391-1.023-0.391-1.414,0s-0.391,1.023,0,1.414L18.586,11H9c-0.552,0-1,0.448-1," +
                "1c0,0.553,0.448,1,1,1h9.586l-3.293,3.293 c-0.391,0.391-0.391,1.023,0,1.414C15.488," +
                "17.902,15.744,18,16,18s0.512-0.098,0.707-0.293l4.999-4.999 C21.799,12.616,21.872,12.505,21.923,12.382z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "logout-icon";
    }
}
