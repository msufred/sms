package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MoreVerticalIcon extends SVGIcon {

    public MoreVerticalIcon() {
        super(DEFAULT_SIZE * 0.15, DEFAULT_SIZE * 0.85);
    }

    public MoreVerticalIcon(double size) {
        super(size * 0.15, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M10,12a2,2 0 1,0 4,0a2,2 0 1,0 -4,0z M10,5a2,2 0 1,0 4,0a2,2 0 1,0 -4,0z M10,19a2,2 0 1,0 4,0a2,2 0 1,0 -4,0z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "more-vertical-icon";
    }
}
