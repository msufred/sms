package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ColumnIcon extends SVGIcon {

    public ColumnIcon() {
        super(DEFAULT_SIZE * 0.975, DEFAULT_SIZE);
    }

    public ColumnIcon(double size) {
        super(size * 0.975, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m17,0H3C1.35,0,0,1.35,0,3v14c0,1.65,1.35,3,3,3h14c1.65,0,3-1.35,3-3V3c0-1.65-1.35-3-3-3Zm-8," +
                "18H3c-.55,0-1-.45-1-1V3c0-.55.45-1,1-1h6v16Zm9-1c0,.55-.45,1-1,1h-6V2h6c.55,0,1,.45,1,1v14Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "column-icon";
    }
}
