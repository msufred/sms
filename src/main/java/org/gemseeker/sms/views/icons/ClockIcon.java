package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ClockIcon extends SVGIcon {

    public ClockIcon() {
        super();
    }

    public ClockIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m11,0C4.93,0,0,4.93,0,11s4.93,11,11,11,11-4.93,11-11S17.07,0,11,0Zm0,20c-4.96,0-9-4.04-9-" +
                "9S6.04,2,11,2s9,4.04,9,9-4.04,9-9,9Z M15.45,12.11l-3.45-1.72v-5.38c0-.55-.45-1-1-1s-1,.45-1,1v6c0," +
                ".38.21.73.55.89l4,2c.14.07.3.11.45.11.37,0,.72-.2.9-.55.25-.49.05-1.09-.45-1.34Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "clock-icon";
    }
}
