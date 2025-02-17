package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ChevronsUpIcon extends SVGIcon {

    public ChevronsUpIcon() {
        super(DEFAULT_SIZE * 0.85 * 0.7, DEFAULT_SIZE * 0.7);
    }

    public ChevronsUpIcon(double size) {
        super(size * 0.85 * 0.7, size * 0.7);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m11,7c-.26,0-.51-.1-.71-.29L6,2.41,1.71,6.71c-.39.39-1.02.39-1.41,0s-.39-1.02,0-1.41L5.29.29c." +
                "39-.39,1.02-.39,1.41,0l5,5c.39.39.39,1.02,0,1.41-.2.2-.45.29-.71.29Z M11,14c-.26,0-.51-.1-.71-.29l-4." +
                "29-4.29L1.71,13.71c-.39.39-1.02.39-1.41,0s-.39-1.02,0-1.41l5-5c.39-.39,1.02-.39,1.41,0l5,5c.39.39.39," +
                "1.02,0,1.41-.2.2-.45.29-.71.29Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "chevrons-up-icon";
    }
}
