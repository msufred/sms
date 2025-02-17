package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class XSquareIcon extends SVGIcon {

    public XSquareIcon() {
        super();
    }

    public XSquareIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,2H5C3.346,2,2,3.346,2,5v14c0,1.654,1.346,3,3,3h14c1.654,0,3-1.346,3-3V5C22," +
                "3.346,20.654,2,19,2z M20,19 c0,0.552-0.448,1-1,1H5c-0.551,0-1-0.448-1-1V5c0-0.551,0.449-1," +
                "1-1h14c0.552,0,1,0.449,1,1V19z M15.707,8.293c-0.391-0.391-1.023-0.391-1.414,0L12," +
                "10.586L9.707,8.293c-0.391-0.391-1.023-0.391-1.414,0 s-0.391,1.023,0,1.414L10.586," +
                "12l-2.293,2.293c-0.391,0.391-0.391,1.023,0,1.414C8.488,15.902,8.744,16,9,16 " +
                "s0.512-0.098,0.707-0.293L12,13.414l2.293,2.293C14.488,15.902,14.744,16,15," +
                "16s0.512-0.098,0.707-0.293 c0.391-0.391,0.391-1.023,0-1.414L13.414,12l2.293-2.293C16.098," +
                "9.316,16.098,8.684,15.707,8.293z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "x-square-icon";
    }
}
