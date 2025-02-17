package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class PauseIcon extends SVGIcon {

    public PauseIcon() {
        super(DEFAULT_SIZE * 0.75, DEFAULT_SIZE);
    }

    public PauseIcon(double size) {
        super(size * 0.75, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M10,3H6C5.448,3,5,3.448,5,4v16c0,0.553,0.448,1,1,1h4c0.552,0,1-0.447,1-1V4C11," +
                "3.448,10.552,3,10,3z M9,19H7V5h2V19z M18,3h-4c-0.553,0-1,0.448-1,1v16c0,0.553,0.447,1," +
                "1,1h4c0.553,0,1-0.447,1-1V4C19,3.448,18.553,3,18,3z M17,19h-2V5h2V19z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "pause-icon";
    }
}
