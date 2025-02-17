package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ChevronsDownIcon extends SVGIcon {

    public ChevronsDownIcon() {
        super(DEFAULT_SIZE * 0.85 * 0.7, DEFAULT_SIZE * 0.7);
    }

    public ChevronsDownIcon(double size) {
        super(size * 0.85 * 0.7, size * 0.7);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m10.29,7.29l-4.29,4.29L1.71,7.29c-.39-.39-1.02-.39-1.41,0s-.39,1.02,0,1.41l5,5c.2.2.45.29." +
                "71.29s.51-.1.71-.29l5-5c.39-.39.39-1.02,0-1.41s-1.02-.39-1.41,0Z M5.29,6.71c.2.2.45.29.71.29s.51-." +
                "1.71-.29L11.71,1.71c.39-.39.39-1.02,0-1.41s-1.02-.39-1.41,0l-4.29,4.29L1.71.29C1.32-.1.68-.1.29." +
                "29S-.1,1.32.29,1.71l5,5Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "chevrons-down-icon";
    }
}
