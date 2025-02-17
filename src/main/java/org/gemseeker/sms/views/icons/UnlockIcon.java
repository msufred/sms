package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class UnlockIcon extends SVGIcon {

    public UnlockIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public UnlockIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,10H8V6.999c-0.001-1.068,0.414-2.073,1.168-2.83c0.755-0.756,1.759-1.173,2.828-1.174c0.001," +
                "0,0.003,0,0.004,0 c1.895,0,3.543,1.347,3.92,3.204c0.11,0.541,0.634,0.895,1.18,0.781c0.541-0.11," +
                "0.891-0.638,0.781-1.179 C17.314,3.015,14.843,0.995,12,0.995c-0.002,0-0.004,0-0.006,0c-1.603," +
                "0.001-3.109,0.627-4.241,1.762C6.621,3.891,5.999,5.398,6,7v3 H5c-1.654,0-3,1.346-3,3v7c0,1.654," +
                "1.346,3,3,3h14c1.654,0,3-1.346,3-3v-7C22,11.346,20.654,10,19,10z M20,20c0,0.552-0.448,1-1,1H5 " +
                "c-0.551,0-1-0.448-1-1v-7c0-0.551,0.449-1,1-1h14c0.552,0,1,0.449,1,1V20z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "unlock-icon";
    }
}
