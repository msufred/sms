package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class StopCircleIcon extends SVGIcon {

    public StopCircleIcon() {
        super();
    }

    public StopCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z " +
                "M12,21c-4.962,0-9-4.037-9-9 c0-4.962,4.038-9,9-9c4.963,0,9,4.038,9,9C21,16.963," +
                "16.963,21,12,21z M15,8H9C8.448,8,8,8.448,8,9v6c0,0.553,0.448,1,1,1h6c0.553,0," +
                "1-0.447,1-1V9C16,8.448,15.553,8,15,8z M14,14h-4v-4h4V14z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "stop-circle-icon";
    }
}
