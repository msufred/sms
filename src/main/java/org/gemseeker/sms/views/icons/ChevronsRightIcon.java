package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ChevronsRightIcon extends SVGIcon {

    public ChevronsRightIcon() {
        super(DEFAULT_SIZE * 0.7, DEFAULT_SIZE * 0.85 * 0.7);
    }

    public ChevronsRightIcon(double size) {
        super(size * 0.7, size * 0.85 * 0.7);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m13.71,5.29L8.71.29c-.39-.39-1.02-.39-1.41,0s-.39,1.02,0,1.41l4.29,4.29-4.29,4.29c-.39.39-.39," +
                "1.02,0,1.41.2.2.45.29.71.29s.51-.1.71-.29l5-5c.39-.39.39-1.02,0-1.41Z M6.71,5.29L1.71.29C1.32-.1.68-.1" +
                ".29.29S-.1,1.32.29,1.71l4.29,4.29L.29,10.29c-.39.39-.39,1.02,0,1.41.2.2.45.29.71.29s.51-.1.71-.29l5-5c" +
                ".39-.39.39-1.02,0-1.41Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "chevrons-right-icon";
    }
}
