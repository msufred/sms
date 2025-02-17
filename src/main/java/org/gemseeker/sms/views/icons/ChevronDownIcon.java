package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ChevronDownIcon extends SVGIcon {

    public ChevronDownIcon() {
        super(DEFAULT_SIZE * 0.85 * 0.8, DEFAULT_SIZE * 0.5 * 0.8);
    }

    public ChevronDownIcon(double size) {
        super(size * 0.85 * 0.8, size * 0.5 * 0.8);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m13.71.29c-.39-.39-1.02-.39-1.41,0l-5.29,5.29L1.71.29C1.32-.1.68-.1.29.29S-.1,1.32.29,1.71l6," +
                "6c.2.2.45.29.71.29s.51-.1.71-.29L13.71,1.71c.39-.39.39-1.02,0-1.41Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "chevron-down-icon";
    }
}
