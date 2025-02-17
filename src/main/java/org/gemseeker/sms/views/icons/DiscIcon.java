package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class DiscIcon extends SVGIcon {

    public DiscIcon() {
        super();
    }

    public DiscIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z M12,21c-4.962,0-9-4." +
                "037-9-9 c0-4.962,4.038-9,9-9c4.963,0,9,4.038,9,9C21,16.963,16.963,21,12,21z M12,8c-2.206,0-4,1.794-" +
                "4,4c0,2.206,1.794,4,4,4c2.206,0,4-1.794,4-4C16,9.794,14.206,8,12,8z M12,14c-1.103,0-2-0.897-2-2 c0-1" +
                ".103,0.897-2,2-2c1.103,0,2,0.897,2,2C14,13.103,13.103,14,12,14z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "disc-icon";
    }
}
