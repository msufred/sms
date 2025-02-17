package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class InfoIcon extends SVGIcon {

    public InfoIcon() {
        super();
    }

    public InfoIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z " +
                "M12,21c-4.962,0-9-4.037-9-9 c0-4.962,4.038-9,9-9c4.963,0,9,4.038,9,9C21,16.963,16.963,21,12,21z " +
                "M12,11c-0.552,0-1,0.448-1,1v4c0,0.553,0.448,1,1,1c0.553,0,1-0.447,1-1v-4C13,11.448,12.553,11,12,11z " +
                "M12.01,7H12c-0.552,0-0.995,0.448-0.995,1s0.453,1,1.005,1c0.553,0,1-0.448,1-1S12.563,7,12.01,7z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "info-icon";
    }
}
