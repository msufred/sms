package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MoreHorizontalIcon extends SVGIcon {

    public MoreHorizontalIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE * 0.15);
    }

    public MoreHorizontalIcon(double size) {
        super(size * 0.85, size * 0.15);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M10,12a2,2 0 1,0 4,0a2,2 0 1,0 -4,0z M17,12a2,2 0 1,0 4,0a2,2 0 1,0 -4,0z M3,12a2,2 0 1,0 4,0a2,2 0 1,0 -4,0z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "more-horizontal-icon";
    }
}
