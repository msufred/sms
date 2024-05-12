package org.gemseeker.sms.views.icons;

import io.github.msufred.feathericons.SVGIcon;
import javafx.scene.shape.SVGPath;

public class StarFilledIcon extends SVGIcon {

    public StarFilledIcon() {
        super(DEFAULT_SIZE);
    }

    public StarFilledIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2L9.19 8.63L2 9.24l5.46 4.73L5.82 21z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "star-filled-icon";
    }
}
