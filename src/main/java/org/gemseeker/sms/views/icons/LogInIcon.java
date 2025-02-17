package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class LogInIcon extends SVGIcon {

    public LogInIcon() {
        super();
    }

    public LogInIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,2h-4c-0.553,0-1,0.448-1,1s0.447,1,1,1h4c0.552,0,1,0.449,1,1v14c0,0.552-0.448,1-1," +
                "1h-4c-0.553,0-1,0.447-1,1 s0.447,1,1,1h4c1.654,0,3-1.346,3-3V5C22,3.346,20.654,2,19,2z " +
                "M15.923,12.382c0.101-0.244,0.101-0.519,0-0.764c-0.051-0.123-0.124-0.233-0.217-0.326l-4.999-4.999 " +
                "c-0.391-0.391-1.023-0.391-1.414,0s-0.391,1.023,0,1.414L12.586,11H3c-0.552,0-1,0.448-1," +
                "1c0,0.553,0.448,1,1,1h9.586l-3.293,3.293 c-0.391,0.391-0.391,1.023,0,1.414C9.488,17.902," +
                "9.744,18,10,18s0.512-0.098,0.707-0.293l4.999-4.999 C15.799,12.616,15.872,12.505,15.923,12.382z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "login-icon";
    }
}
