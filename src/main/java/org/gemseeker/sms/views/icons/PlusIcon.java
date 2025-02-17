package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class PlusIcon extends SVGIcon {

    public PlusIcon() {
        super();
    }

    public PlusIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,11h-6V5c0-0.552-0.447-1-1-1c-0.552,0-1,0.448-1,1v6H5c-0.552,0-1,0.448-1,1c0,0.553," +
                "0.448,1,1,1h6v6 c0,0.553,0.448,1,1,1c0.553,0,1-0.447,1-1v-6h6c0.553,0,1-0.447,1-1C20,11.448," +
                "19.553,11,19,11z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "plus-icon";
    }
}
