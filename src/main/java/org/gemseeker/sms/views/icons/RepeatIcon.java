package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class RepeatIcon extends SVGIcon {

    public RepeatIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public RepeatIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M3,12c0.552,0,1-0.448,1-1V9c0-1.654,1.346-3,3-3h11.586l-2.293,2.293c-0.391,0.391-0.391," +
                "1.023,0,1.414 C16.488,9.902,16.744,10,17,10s0.512-0.098,0.707-0.293l3.999-3.999c0.093-0.092," +
                "0.166-0.203,0.217-0.326 c0.101-0.244,0.101-0.52,0-0.764c-0.051-0.123-0.124-0.233-0.217-0.326l-" +
                "3.999-3.999c-0.391-0.391-1.023-0.391-1.414,0 s-0.391,1.023,0,1.414L18.586,4H7C4.243,4,2,6.243,2," +
                "9v2C2,11.552,2.448,12,3,12z M21,12c-0.553,0-1,0.448-1,1v2c0,1.654-1.346,3-3,3H5.414l2.293-" +
                "2.293c0.391-0.391,0.391-1.023,0-1.414 s-1.023-0.391-1.414,0l-3.999,3.999c-0.093,0.092-0.166," +
                "0.203-0.217,0.326c-0.101,0.244-0.101,0.52,0,0.764 c0.051,0.123,0.124,0.233,0.217,0.326l3.999," +
                "3.999C6.488,23.902,6.744,24,7,24s0.512-0.098,0.707-0.293 c0.391-0.391,0.391-1.023,0-1.414L5.414," +
                "20H17c2.757,0,5-2.243,5-5v-2C22,12.448,21.553,12,21,12z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "repeat-icon";
    }
}
