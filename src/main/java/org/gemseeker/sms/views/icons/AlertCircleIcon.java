package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class AlertCircleIcon extends SVGIcon {

    public AlertCircleIcon() {
        super();
    }

    public AlertCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m11,0C4.93,0,0,4.93,0,11s4.93,11,11,11,11-4.93,11-11S17.07,0,11,0Zm0,20c-4.96,0-9-4.04-9-9S6." +
                "04,2,11,2s9,4.04,9,9-4.04,9-9,9Z M11,12c.55,0,1-.45,1-1v-4c0-.55-.45-1-1-1s-1,.45-1,1v4c0,.55.45,1,1,1Z " +
                "M11.01,14h0c-.55,0-1,.45-1,1s.45,1,1,1,1-.45,1-1-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "alert-circle-icon";
    }
}
