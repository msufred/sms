package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArrowLeftIcon extends SVGIcon {

    public ArrowLeftIcon() {
        super(DEFAULT_SIZE * 0.85);
    }

    public ArrowLeftIcon(double size) {
        super(size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m15,7H3.41L8.71,1.71c.39-.39.39-1.02,0-1.41s-1.02-.39-1.41,0L.29,7.29c-.09.09-.17.2-.22.33" +
                "-.1.24-.1.52,0,.76.05.12.12.23.22.33l7,7c.2.2.45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02,0-1.41l-5.29" +
                "-5.29h11.59c.55,0,1-.45,1-1s-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "arrow-left-icon";
    }
}
