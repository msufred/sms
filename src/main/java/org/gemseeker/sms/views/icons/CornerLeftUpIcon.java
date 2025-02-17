package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CornerLeftUpIcon extends SVGIcon {

    public CornerLeftUpIcon() {
        super(DEFAULT_SIZE * 0.8);
    }

    public CornerLeftUpIcon(double size) {
        super(size * 0.8);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m17,16h-7c-1.65,0-3-1.35-3-3V3.41l3.29,3.29c.2.2.45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02," +
                "0-1.41L6.71.29c-.09-.09-.2-.17-.33-.22-.24-.1-.52-.1-.76,0-.12.05-.23.12-.33.22L.29,5.29c-.39.39-." +
                "39,1.02,0,1.41s1.02.39,1.41,0l3.29-3.29v9.59c0,2.76,2.24,5,5,5h7c.55,0,1-.45,1-1s-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "corner-left-up-icon";
    }
}
