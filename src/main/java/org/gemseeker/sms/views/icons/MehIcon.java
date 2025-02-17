package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MehIcon extends SVGIcon {

    public MehIcon() {
        super();
    }

    public MehIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z " +
                "M12,21c-4.962,0-9-4.037-9-9 c0-4.962,4.038-9,9-9c4.963,0,9,4.038,9,9C21,16.963,16.963,21,12,21z " +
                "M16,14H8c-0.552,0-1,0.447-1,1s0.448,1,1,1h8c0.553,0,1-0.447,1-1S16.553,14,16,14z " +
                "M9.01,10c0.552,0,1-0.448,1-1s-0.448-1-1-1H9C8.448,8,8.005,8.448,8.005,9S8.458,10,9.01,10z " +
                "M15.01,10c0.553,0,1-0.448,1-1s-0.447-1-1-1H15c-0.553,0-0.995,0.448-0.995,1S14.457,10,15.01,10z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "meh-icon";
    }
}
