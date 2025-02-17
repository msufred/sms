package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CheckSquareIcon extends SVGIcon {

    public CheckSquareIcon() {
        super(DEFAULT_SIZE * 1.05, DEFAULT_SIZE);
    }

    public CheckSquareIcon(double size) {
        super(size * 1.05, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m10,13c-.26,0-.51-.1-.71-.29l-3-3c-.39-.39-.39-1.02,0-1.41s1.02-.39,1.41,0l2.29,2.29L19.29," +
                "1.29c.39-.39,1.02-.39,1.41,0s.39,1.02,0,1.41l-10,10c-.2.2-.45.29-.71.29Z M17,20H3c-1.65,0-3-1.35-3-" +
                "3V3C0,1.35,1.35,0,3,0h11c.55,0,1,.45,1,1s-.45,1-1,1H3c-.55,0-1,.45-1,1v14c0,.55.45,1,1,1h14c.55,0," +
                "1-.45,1-1v-7c0-.55.45-1,1-1s1,.45,1,1v7c0,1.65-1.35,3-3,3Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "check-square-icon";
    }
}
