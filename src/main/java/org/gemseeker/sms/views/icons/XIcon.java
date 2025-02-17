package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class XIcon extends SVGIcon {

    public XIcon() {
        super(DEFAULT_SIZE * 0.6);
    }

    public XIcon(double size) {
        super(size * 0.6);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M13.414,12l5.293-5.293c0.391-0.391,0.391-1.023,0-1.414s-1.023-0.391-1.414,0L12,10.586L6.707,5.293 " +
                "c-0.391-0.391-1.023-0.391-1.414,0s-0.391,1.023,0,1.414L10.586,12l-5.293,5.293c-0.391,0.391-0.391,1.023," +
                "0,1.414 C5.488,18.902,5.744,19,6,19s0.512-0.098,0.707-0.293L12,13.414l5.293,5.293C17.488,18.902,17.744," +
                "19,18,19s0.512-0.098,0.707-0.293 c0.391-0.391,0.391-1.023,0-1.414L13.414,12z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "x-icon";
    }
}
