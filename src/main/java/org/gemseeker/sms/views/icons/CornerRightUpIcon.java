package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CornerRightUpIcon extends SVGIcon {

    public CornerRightUpIcon() {
        super(DEFAULT_SIZE * 0.8);
    }

    public CornerRightUpIcon(double size) {
        super(size * 0.8);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m17.71,5.29L12.71.29c-.09-.09-.2-.17-.33-.22-.24-.1-.52-.1-.76,0-.12.05-.23.12-.33.22l-5,5c-." +
                "39.39-.39,1.02,0,1.41s1.02.39,1.41,0l3.29-3.29v9.59c0,1.65-1.35,3-3,3H1c-.55,0-1,.45-1,1s.45,1,1," +
                "1h7c2.76,0,5-2.24,5-5V3.41l3.29,3.29c.2.2.45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02,0-1.41Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "corner-right-up-icon";
    }
}
