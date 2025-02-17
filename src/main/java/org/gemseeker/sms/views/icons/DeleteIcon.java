package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class DeleteIcon extends SVGIcon {

    public DeleteIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.75);
    }

    public DeleteIcon(double size) {
        super(size, size * 0.75);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21,3H8C7.711,3,7.438,3.125,7.248,3.341l-7,8c-0.33,0.377-0.33,0.94,0,1.317l7,8C7.438,20.876," +
                "7.711,21,8,21h13 c1.654,0,3-1.346,3-3V6C24,4.346,22.654,3,21,3z M22,18c0,0.552-0.448,1-1,1H8.454l-6." +
                "125-7l6.125-7H21c0.552,0,1,0.449,1,1V18z M18.707,8.293c-0.391-0.391-1.023-0.391-1.414,0L15,10.586l-2" +
                ".293-2.293c-0.391-0.391-1.023-0.391-1.414,0 s-0.391,1.023,0,1.414L13.586,12l-2.293,2.293c-0.391,0.39" +
                "1-0.391,1.023,0,1.414C11.488,15.902,11.744,16,12,16 s0.512-0.098,0.707-0.293L15,13.414l2.293,2.293C1" +
                "7.488,15.902,17.744,16,18,16s0.512-0.098,0.707-0.293 c0.391-0.391,0.391-1.023,0-1.414L16.414,12l2.29" +
                "3-2.293C19.098,9.316,19.098,8.684,18.707,8.293z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "delete-icon";
    }
}
