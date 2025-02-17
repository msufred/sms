package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArrowUpLeftIcon extends SVGIcon {

    public ArrowUpLeftIcon() {
        super(DEFAULT_SIZE * 0.65);
    }

    public ArrowUpLeftIcon(double size) {
        super(size * 0.65);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m3.41,2h7.59c.55,0,1-.45,1-1s-.45-1-1-1H1c-.13,0-.26.03-.38.08C.37.18.18.37.08.62c-.05.12" +
                "-.08.25-.08.38v10c0,.55.45,1,1,1s1-.45,1-1V3.41l8.29,8.29c.2.2.45.29.71.29s.51-.1.71-.29c.39-.39.39" +
                "-1.02,0-1.41L3.41,2Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "arrow-up-left-icon";
    }
}
