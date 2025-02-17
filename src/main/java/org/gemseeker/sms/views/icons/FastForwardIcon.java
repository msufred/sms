package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FastForwardIcon extends SVGIcon {

    public FastForwardIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.75);
    }

    public FastForwardIcon(double size) {
        super(size, size * 0.75);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22.614,11.21l-9-7c-0.304-0.235-0.711-0.276-1.054-0.109C12.218,4.27,12,4.618,12,5v7v7c0," +
                "0.382,0.218,0.73,0.561,0.898 C12.7,19.967,12.851,20,13,20c0.218,0,0.435-0.071,0.614-0.211l9-7C22.857," +
                "12.6,23,12.309,23,12S22.857,11.4,22.614,11.21z M14,16.955V7.044L20.371,12L14,16.955z M2.614," +
                "4.21C2.313,3.976,1.905,3.934,1.561,4.102C1.218,4.27,1,4.618,1,5v14c0,0.382,0.218,0.73,0.561,0.898 " +
                "C1.7,19.967,1.85,20,2,20c0.218,0,0.435-0.071,0.614-0.211l9-7C11.857,12.6,12,12.309,12,12s-" +
                "0.143-0.6-0.386-0.79L2.614,4.21z M3,16.955V7.044L9.371,12L3,16.955z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "fast-forward-icon";
    }
}
