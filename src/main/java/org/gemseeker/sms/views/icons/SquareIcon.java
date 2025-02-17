package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class SquareIcon extends SVGIcon {

    public SquareIcon() {
        super();
    }

    public SquareIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,22H5c-1.654,0-3-1.346-3-3V5c0-1.654,1.346-3,3-3h14c1.654,0,3,1.346,3,3v14C22,20.654,20.654,22,19,22z " +
                "M5,4 C4.449,4,4,4.449,4,5v14c0,0.552,0.449,1,1,1h14c0.552,0,1-0.448,1-1V5c0-0.551-0.448-1-1-1H5z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "square-icon";
    }
}
