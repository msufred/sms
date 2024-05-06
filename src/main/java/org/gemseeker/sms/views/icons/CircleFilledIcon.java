package org.gemseeker.sms.views.icons;

import io.github.msufred.feathericons.SVGIcon;
import javafx.scene.shape.SVGPath;

/**
 *
 * @author Gem
 */
public class CircleFilledIcon extends SVGIcon {

    public CircleFilledIcon() {
        super(DEFAULT_SIZE);
    }

    public CircleFilledIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12 2A10 10 0 0 0 2 12a10 10 0 0 0 10 10a10 10 0 0 0 10-10A10 10 0 0 0 12 2");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "circle-filled-icon";
    }

}
