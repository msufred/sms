package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class AlignRightIcon extends SVGIcon {

    public AlignRightIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.675);
    }

    public AlignRightIcon(double size) {
        super(size, size * 0.675);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m19,6H5c-.55,0-1-.45-1-1s.45-1,1-1h14c.55,0,1,.45,1,1s-.45,1-1,1Z " +
                "M19,2H1c-.55,0-1-.45-1-1S.45,0,1,0h18c.55,0,1,.45,1,1s-.45,1-1,1Z " +
                "M19,10H1c-.55,0-1-.45-1-1s.45-1,1-1h18c.55,0,1,.45,1,1s-.45,1-1,1Z " +
                "M19,14H5c-.55,0-1-.45-1-1s.45-1,1-1h14c.55,0,1,.45,1,1s-.45,1-1,1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "align-right-icon";
    }
}
