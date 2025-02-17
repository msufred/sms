package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CornerRightDownIcon extends SVGIcon {

    public CornerRightDownIcon() {
        super(DEFAULT_SIZE * 0.8);
    }

    public CornerRightDownIcon(double size) {
        super(size * 0.8);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m17.71,11.29c-.39-.39-1.02-.39-1.41,0l-3.29,3.29V5c0-2.76-2.24-5-5-5H1C.45,0,0,.45,0,1s.45,1" +
                ",1,1h7c1.65,0,3,1.35,3,3v9.59l-3.29-3.29c-.39-.39-1.02-.39-1.41,0s-.39,1.02,0,1.41l5,5c.09.09.2.17.33." +
                "22.12.05.25.08.38.08s.26-.03.38-.08c.12-.05.23-.12.33-.22l5-5c.39-.39.39-1.02,0-1.41Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "corner-right-down-icon";
    }
}
