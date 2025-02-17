package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArrowDownIcon extends SVGIcon {

    public ArrowDownIcon() {
        super(DEFAULT_SIZE * 0.85);
    }

    public ArrowDownIcon(double size) {
        super(size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m15.71,7.29c-.39-.39-1.02-.39-1.41,0l-5.29,5.29V1c0-.55-.45-1-1-1s-1,.45-1,1v11.59L1.71,7.29c" +
                "-.39-.39-1.02-.39-1.41,0s-.39,1.02,0,1.41l7,7c.09.09.2.17.33.22.12.05.25.08.38.08s.26-.03.38-.08c.12" +
                "-.05.23-.12.33-.22l7-7c.39-.39.39-1.02,0-1.41Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "arrow-down-icon";
    }
}
