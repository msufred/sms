package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TargetIcon extends SVGIcon {

    public TargetIcon() {
        super();
    }

    public TargetIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z " +
                "M12,21c-4.962,0-9-4.037-9-9 c0-4.962,4.038-9,9-9c4.963,0,9,4.038,9,9C21,16.963," +
                "16.963,21,12,21z M12,5c-3.86,0-7,3.14-7,7c0,3.859,3.14,7,7,7c3.859,0,7-3.141,7-7C19," +
                "8.14,15.859,5,12,5z M12,17c-2.757,0-5-2.243-5-5 s2.243-5,5-5s5,2.243,5,5S14.757,17," +
                "12,17z M12,9c-1.654,0-3,1.346-3,3s1.346,3,3,3s3-1.346,3-3S13.654,9,12,9z M12,13c-" +
                "0.551,0-1-0.448-1-1c0-0.551,0.449-1,1-1 c0.552,0,1,0.449,1,1C13,12.552,12.552,13,12,13z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "target-icon";
    }
}
