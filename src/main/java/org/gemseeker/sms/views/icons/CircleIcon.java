package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CircleIcon extends SVGIcon {

    public CircleIcon() {
        super();
    }

    public CircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m11,0C4.93,0,0,4.93,0,11s4.93,11,11,11,11-4.93,11-11S17.07,0,11,0Zm0,20c-4.96,0-9-4.04-9-9S6" +
                ".04,2,11,2s9,4.04,9,9-4.04,9-9,9Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "circle-icon";
    }
}
