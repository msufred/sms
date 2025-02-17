package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CornerDownLeftIcon extends SVGIcon {

    public CornerDownLeftIcon() {
        super(DEFAULT_SIZE * 0.8);
    }

    public CornerDownLeftIcon(double size) {
        super(size * 0.8);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m17,0c-.55,0-1,.45-1,1v7c0,1.65-1.35,3-3,3H3.41l3.29-3.29c.39-.39.39-1.02,0-1.41s-1.02-.39-1." +
                "41,0L.29,11.29c-.09.09-.17.2-.22.33-.1.24-.1.52,0,.76.05.12.12.23.22.33l5,5c.2.2.45.29.71.29s.51-.1." +
                "71-.29c.39-.39.39-1.02,0-1.41l-3.29-3.29h9.59c2.76,0,5-2.24,5-5V1c0-.55-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "corner-down-left-icon";
    }
}
