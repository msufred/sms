package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ChevronLeftIcon extends SVGIcon {

    public ChevronLeftIcon() {
        super(DEFAULT_SIZE * 0.5 * 0.8, DEFAULT_SIZE * 0.85 * 0.8);
    }

    public ChevronLeftIcon(double size) {
        super(size * 0.5 * 0.8, size * 0.85 * 0.8);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m2.41,7L7.71,1.71c.39-.39.39-1.02,0-1.41s-1.02-.39-1.41,0L.29,6.29c-.39.39-.39,1.02,0,1.41l6," +
                "6c.2.2.45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02,0-1.41L2.41,7Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "chevron-left-icon";
    }
}
