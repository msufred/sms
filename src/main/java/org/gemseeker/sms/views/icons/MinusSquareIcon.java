package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MinusSquareIcon extends SVGIcon {

    public MinusSquareIcon() {
        super();
    }

    public MinusSquareIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,2H5C3.346,2,2,3.346,2,5v14c0,1.654,1.346,3,3,3h14c1.654,0,3-1.346," +
                "3-3V5C22,3.346,20.654,2,19,2z M20,19 c0,0.552-0.448,1-1,1H5c-0.551,0-1-0.448-1-" +
                "1V5c0-0.551,0.449-1,1-1h14c0.552,0,1,0.449,1,1V19z M16,11H8c-0.552,0-1,0.448-1," +
                "1c0,0.553,0.448,1,1,1h8c0.553,0,1-0.447,1-1C17,11.448,16.553,11,16,11z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "minus-square-icon";
    }
}
