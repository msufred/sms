package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MinusIcon extends SVGIcon {

    public MinusIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE * 0.15);
    }

    public MinusIcon(double size) {
        super(size * 0.85, size * 0.15);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,13H5c-0.552,0-1-0.447-1-1c0-0.552,0.448-1,1-1h14c0.553,0,1,0.448,1,1C20,12.553,19.553,13,19,13z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "minus-icon";
    }
}
