package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MessageSquareIcon extends SVGIcon {

    public MessageSquareIcon() {
        super();
    }

    public MessageSquareIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,2H5C3.346,2,2,3.346,2,5v16c0,0.404,0.244,0.77,0.617,0.924C2.741,21.976," +
                "2.871,22,3,22c0.26,0,0.516-0.102,0.707-0.293 L7.414,18H19c1.654,0,3-1.346,3-3V5C22," +
                "3.346,20.654,2,19,2z M20,15c0,0.552-0.448,1-1,1H7c-0.265,0-0.52,0.105-0.707,0.293 " +
                "L4,18.586V5c0-0.551,0.449-1,1-1h14c0.552,0,1,0.449,1,1V15z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "message-square-icon";
    }
}
