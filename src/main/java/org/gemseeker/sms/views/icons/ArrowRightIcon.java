package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArrowRightIcon extends SVGIcon {

    public ArrowRightIcon() {
        super(DEFAULT_SIZE * 0.85);
    }

    public ArrowRightIcon(double size) {
        super(size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m15.92,8.38c.1-.24.1-.52,0-.76-.05-.12-.12-.23-.22-.33L8.71.29c-.39-.39-1.02-.39-1.41,0s-.39," +
                "1.02,0,1.41l5.29,5.29H1c-.55,0-1,.45-1,1s.45,1,1,1h11.59l-5.29,5.29c-.39.39-.39,1.02,0,1.41.2.2.45.29" +
                ".71.29s.51-.1.71-.29l7-7c.09-.09.17-.2.22-.33Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "arrow-right-icon";
    }
}
