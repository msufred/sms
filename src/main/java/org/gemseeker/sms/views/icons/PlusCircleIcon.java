package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class PlusCircleIcon extends SVGIcon {

    public PlusCircleIcon() {
        super();
    }

    public PlusCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z " +
                "M12,21c-4.962,0-9-4.037-9-9 c0-4.962,4.038-9,9-9c4.963,0,9,4.038,9,9C21,16.963," +
                "16.963,21,12,21z M16,11h-3V8c0-0.552-0.447-1-1-1c-0.552,0-1,0.448-1,1v3H8c-0.552," +
                "0-1,0.448-1,1c0,0.553,0.448,1,1,1h3v3 c0,0.553,0.448,1,1,1c0.553,0,1-0.447," +
                "1-1v-3h3c0.553,0,1-0.447,1-1C17,11.448,16.553,11,16,11z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "plus-circle-icon";
    }
}
