package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class UmbrellaIcon extends SVGIcon {

    public UmbrellaIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.95);
    }

    public UmbrellaIcon(double size) {
        super(size, size * 0.95);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M10.855,1.054C5.116,1.602,0.552,6.166,0.004,11.905c-0.027,0.281,0.066,0.56,0.256," +
                "0.768C0.45,12.881,0.718,13,1,13h10v6 c0,2.206,1.794,4,4,4s4-1.794,4-4c0-0.553-0.447-1-1-1s-1," +
                "0.447-1,1c0,1.103-0.897,2-2,2s-2-0.897-2-2v-6h10 c0.282,0,0.551-0.119,0.74-0.327c0.189-0.208," +
                "0.282-0.487,0.255-0.768C23.364,5.292,17.474,0.434,10.855,1.054z M12,11H2.161 c0.888-4.262," +
                "4.469-7.533,8.884-7.955c5.138-0.49,9.767,3.007,10.793,7.955H12z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "umbrella-icon";
    }
}
