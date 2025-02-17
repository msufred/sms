package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArrowUpIcon extends SVGIcon {

    public ArrowUpIcon() {
        super(DEFAULT_SIZE * 0.85);
    }

    public ArrowUpIcon(double size) {
        super(size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m15.71,7.29L8.71.29c-.09-.09-.2-.17-.33-.22-.24-.1-.52-.1-.76,0-.12.05-.23.12-.33.22L.29," +
                "7.29c-.39.39-.39,1.02,0,1.41s1.02.39,1.41,0L7,3.41v11.59c0,.55.45,1,1,1s1-.45,1-1V3.41l5.29,5.29c.2" +
                ".2.45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02,0-1.41Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "arrow-up-icon";
    }
}
