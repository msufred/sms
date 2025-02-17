package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ThermometerIcon extends SVGIcon {

    public ThermometerIcon() {
        super(DEFAULT_SIZE * 0.5, DEFAULT_SIZE);
    }

    public ThermometerIcon(double size) {
        super(size * 0.5, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M16.073,15.447c-0.3-0.45-0.659-0.849-1.073-1.188V3.5C15,1.57,13.43,0,11.5,0S8,1.57,8," +
                "3.5v10.758 c-0.979,0.805-1.644,1.912-1.895,3.171c-0.287,1.441,0.005,2.907,0.821,4.129c1.023,1.53," +
                "2.734,2.444,4.577,2.444 c1.089,0,2.144-0.32,3.052-0.928c1.222-0.815,2.052-2.059,2.339-3.5C17.181," +
                "18.134,16.889,16.668,16.073,15.447z M14.933,19.185 c-0.183,0.917-0.711,1.708-1.488,2.228c-0.578," +
                "0.386-1.249,0.59-1.94,0.59c-1.174,0-2.263-0.581-2.914-1.556 c-0.52-0.777-0.705-1.71-0.522-" +
                "2.627c0.182-0.918,0.711-1.709,1.488-2.229C9.833,15.405,10,15.094,10,14.76V3.5 C10,2.673,10.673," +
                "2,11.5,2S13,2.673,13,3.5v11.26c0,0.334,0.167,0.646,0.444,0.831c0.385,0.257,0.709,0.582,0.966," +
                "0.967 C14.93,17.335,15.115,18.268,14.933,19.185z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "thermometer-icon";
    }
}
