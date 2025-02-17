package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MinusCircleIcon extends SVGIcon {

    public MinusCircleIcon() {
        super();
    }

    public MinusCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,23C5.935,23,1,18.065,1,12S5.935,1,12,1s11,4.935,11,11S18.065,23,12,23z " +
                "M12,3c-4.962,0-9,4.038-9,9 c0,4.963,4.038,9,9,9c4.963,0,9-4.037,9-9C21,7.038,16.963,3,12,3z " +
                "M16,13H8c-0.552,0-1-0.447-1-1c0-0.552,0.448-1,1-1h8c0.553,0,1,0.448,1,1C17,12.553,16.553,13,16,13z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "minus-circle-icon";
    }
}
