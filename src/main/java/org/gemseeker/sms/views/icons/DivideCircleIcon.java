package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class DivideCircleIcon extends SVGIcon {

    public DivideCircleIcon() {
        super();
    }

    public DivideCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M16,11H8c-0.552,0-1,0.448-1,1c0,0.553,0.448,1,1,1h8c0.553,0,1-0.447,1-1C17,11.448,16.553,11," +
                "16,11z M11.29,15.29C11.11,15.479,11,15.74,11,16s0.11,0.52,0.29,0.71C11.48,16.89,11.74,17,12,17c0.26," +
                "0,0.52-0.11,0.71-0.29 C12.89,16.52,13,16.26,13,16s-0.11-0.521-0.29-0.71C12.33,14.92,11.67,14.92,11.2" +
                "9,15.29z M12,9c0.26,0,0.52-0.11,0.71-0.29C12.89,8.52,13,8.26,13,8c0-0.26-0.11-0.52-0.29-0.71c-0.38-0" +
                ".37-1.04-0.37-1.42,0 C11.11,7.48,11,7.74,11,8c0,0.26,0.11,0.52,0.29,0.71C11.48,8.89,11.74,9,12,9z " +
                "M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z M12,21c-4.962,0-9-4.037-9-9 " +
                "c0-4.962,4.038-9,9-9c4.963,0,9,4.038,9,9C21,16.963,16.963,21,12,21z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "divide-circle-icon";
    }
}
