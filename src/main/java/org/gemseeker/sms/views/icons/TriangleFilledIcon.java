package org.gemseeker.sms.views.icons;

import io.github.msufred.feathericons.SVGIcon;
import javafx.scene.shape.SVGPath;

public class TriangleFilledIcon extends SVGIcon {

    public TriangleFilledIcon() {
        super(DEFAULT_SIZE);
    }

    public TriangleFilledIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M1 21h22L12 2");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "triangle-filled-icon";
    }
}
