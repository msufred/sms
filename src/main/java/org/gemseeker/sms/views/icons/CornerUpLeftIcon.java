package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CornerUpLeftIcon extends SVGIcon {

    public CornerUpLeftIcon() {
        super(DEFAULT_SIZE * 0.8);
    }

    public CornerUpLeftIcon(double size) {
        super(size * 0.8);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m13,5H3.41l3.29-3.29c.39-.39.39-1.02,0-1.41s-1.02-.39-1.41,0L.29,5.29c-.09.09-.17.2-.22.33-." +
                "1.24-.1.52,0,.76.05.12.12.23.22.33l5,5c.2.2.45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02,0-1.41l-3.29-3" +
                ".29h9.59c1.65,0,3,1.35,3,3v7c0,.55.45,1,1,1s1-.45,1-1v-7c0-2.76-2.24-5-5-5Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "corner-up-left-icon";
    }
}
