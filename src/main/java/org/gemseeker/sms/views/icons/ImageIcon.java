package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ImageIcon extends SVGIcon {

    public ImageIcon() {
        super();
    }

    public ImageIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,2H5C3.346,2,2,3.346,2,5v14c0,1.654,1.346,3,3,3h14c1.654,0,3-1.346,3-3V5C22,3.346,20.654," +
                "2,19,2z M4,5 c0-0.551,0.449-1,1-1h14c0.552,0,1,0.449,1,1v7.586l-3.293-3.293c-0.391-0.391-1.023-0.391-1.414," +
                "0L4.656,19.93 C4.275,19.789,4,19.43,4,19V5z M19,20H7.414L16,11.414l4,4V19C20,19.552,19.552,20,19,20z " +
                "M8.5,11C9.878,11,11,9.878,11,8.5S9.878,6,8.5,6S6,7.122,6,8.5S7.122,11,8.5,11z M8.5,8C8.776,8,9,8.224," +
                "9,8.5 S8.776,9,8.5,9S8,8.776,8,8.5S8.224,8,8.5,8z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "image-icon";
    }
}
