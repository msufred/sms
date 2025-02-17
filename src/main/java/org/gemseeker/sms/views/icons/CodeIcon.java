package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CodeIcon extends SVGIcon {

    public CodeIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.6);
    }

    public CodeIcon(double size) {
        super(size, size * 0.6);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m21.71,6.29L15.71.29c-.39-.39-1.02-.39-1.41,0s-.39,1.02,0,1.41l5.29,5.29-5.29,5.29c-.39." +
                "39-.39,1.02,0,1.41.2.2.45.29.71.29s.51-.1.71-.29l6-6c.39-.39.39-1.02,0-1.41Z M7.71.29c-.39-.39-1." +
                "02-.39-1.41,0L.29,6.29c-.39.39-.39,1.02,0,1.41l6,6c.2.2.45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02" +
                ",0-1.41L2.41,7,7.71,1.71c.39-.39.39-1.02,0-1.41Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "code-icon";
    }
}
