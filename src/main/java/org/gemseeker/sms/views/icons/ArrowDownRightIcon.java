package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArrowDownRightIcon extends SVGIcon {

    public ArrowDownRightIcon() {
        super(DEFAULT_SIZE * 0.65);
    }

    public ArrowDownRightIcon(double size) {
        super(size * 0.65);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m12,1c0-.55-.45-1-1-1s-1,.45-1,1v7.59L1.71.29C1.32-.1.68-.1.29.29S-.1,1.32.29,1.71l8.29," +
                "8.29H1c-.55,0-1,.45-1,1s.45,1,1,1h10c.13,0,.26-.03.38-.08.24-.1.44-.3.54-.54.05-.12.08-.25.08-.38V1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "arrow-down-right-icon";
    }
}
