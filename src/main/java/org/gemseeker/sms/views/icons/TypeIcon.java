package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TypeIcon extends SVGIcon {

    public TypeIcon() {
        super();
    }

    public TypeIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,3H4C3.448,3,3,3.448,3,4v3c0,0.552,0.448,1,1,1s1-0.448,1-1V5h6v14H9c-0.552,0-1,0.447-1," +
                "1s0.448,1,1,1h6 c0.553,0,1-0.447,1-1s-0.447-1-1-1h-2V5h6v2c0,0.552,0.447,1,1,1s1-0.448,1-1V4C21," +
                "3.448,20.553,3,20,3z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "type-icon";
    }
}
