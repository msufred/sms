package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ChevronsLeftIcon extends SVGIcon {

    public ChevronsLeftIcon() {
        super(DEFAULT_SIZE * 0.7, DEFAULT_SIZE * 0.85 * 0.7);
    }

    public ChevronsLeftIcon(double size) {
        super(size * 0.7, size * 0.85 * 0.7);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m2.41,6L6.71,1.71c.39-.39.39-1.02,0-1.41s-1.02-.39-1.41,0L.29,5.29c-.39.39-.39,1.02,0,1.41l5," +
                "5c.2.2.45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02,0-1.41L2.41,6Z M9.41,6L13.71,1.71c.39-.39.39-1.02,0-1" +
                ".41s-1.02-.39-1.41,0l-5,5c-.39.39-.39,1.02,0,1.41l5,5c.2.2.45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02," +
                "0-1.41l-4.29-4.29Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "chevrons-left-icon";
    }
}
