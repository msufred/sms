package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CornerLeftDownIcon extends SVGIcon {

    public CornerLeftDownIcon() {
        super(DEFAULT_SIZE * 0.8);
    }

    public CornerLeftDownIcon(double size) {
        super(size * 0.8);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m17,0h-7c-2.76,0-5,2.24-5,5v9.59l-3.29-3.29c-.39-.39-1.02-.39-1.41,0s-.39,1.02,0,1.41l5," +
                "5c.09.09.2.17.33.22.12.05.25.08.38.08s.26-.03.38-.08c.12-.05.23-.12.33-.22l5-5c.39-.39.39-1.02,0-1" +
                ".41s-1.02-.39-1.41,0l-3.29,3.29V5c0-1.65,1.35-3,3-3h7c.55,0,1-.45,1-1s-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "corner-left-down-icon";
    }
}
