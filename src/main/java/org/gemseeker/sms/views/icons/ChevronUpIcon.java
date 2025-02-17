package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ChevronUpIcon extends SVGIcon {

    public ChevronUpIcon() {
        super(DEFAULT_SIZE * 0.85 * 0.8, DEFAULT_SIZE * 0.5 * 0.8);
    }

    public ChevronUpIcon(double size) {
        super(size * 0.85 * 0.8, size * 0.5 * 0.8);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m13,8c-.26,0-.51-.1-.71-.29L7,2.41,1.71,7.71c-.39.39-1.02.39-1.41,0s-.39-1.02,0-1.41L6.29.29c." +
                "39-.39,1.02-.39,1.41,0l6,6c.39.39.39,1.02,0,1.41-.2.2-.45.29-.71.29Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "chevron-up-icon";
    }
}
