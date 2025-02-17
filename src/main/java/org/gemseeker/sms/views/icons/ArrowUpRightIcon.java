package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArrowUpRightIcon extends SVGIcon {

    public ArrowUpRightIcon() {
        super(DEFAULT_SIZE * 0.65);
    }

    public ArrowUpRightIcon(double size) {
        super(size * 0.65);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m11.92.62c-.1-.24-.3-.44-.54-.54-.12-.05-.25-.08-.38-.08H1C.45,0,0,.45,0,1s.45,1,1,1h7.59L.29," +
                "10.29c-.39.39-.39,1.02,0,1.41.2.2.45.29.71.29s.51-.1.71-.29L10,3.41v7.59c0,.55.45,1,1,1s1-.45,1-1V1c0-" +
                ".13-.03-.26-.08-.38Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "arrow-up-right-icon";
    }
}
