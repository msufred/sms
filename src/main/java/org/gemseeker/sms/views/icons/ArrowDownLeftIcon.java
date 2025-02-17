package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArrowDownLeftIcon extends SVGIcon {

    public ArrowDownLeftIcon() {
        super(DEFAULT_SIZE * 0.65);
    }

    public ArrowDownLeftIcon(double size) {
        super(size * 0.65);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m11,10H3.41L11.71,1.71c.39-.39.39-1.02,0-1.41s-1.02-.39-1.41,0L2,8.59V1c0-.55-.45-1-1-1S0," +
                ".45,0,1v10c0,.13.03.26.08.38.1.24.3.44.54.54.12.05.25.08.38.08h10c.55,0,1-.45,1-1s-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "arrow-down-left-icon";
    }
}
