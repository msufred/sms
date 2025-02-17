package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CheckIcon extends SVGIcon {

    public CheckIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.75);
    }

    public CheckIcon(double size) {
        super(size, size * 0.75);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m17.71.29c-.39-.39-1.02-.39-1.41,0L6,10.59,1.71,6.29c-.39-.39-1.02-.39-1.41,0s-.39,1.02,0," +
                "1.41l5,5c.2.2.45.29.71.29s.51-.1.71-.29L17.71,1.71c.39-.39.39-1.02,0-1.41Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "check-icon";
    }
}
