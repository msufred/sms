package org.gemseeker.sms.views.icons;

import io.github.msufred.feathericons.SVGIcon;
import javafx.scene.shape.SVGPath;

public class PentagonIcon extends SVGIcon {

    public PentagonIcon() {
        super(DEFAULT_SIZE);
    }

    public PentagonIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m2 9l4 12h12l4-12l-10-7z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "pentagon-icon";
    }

}
