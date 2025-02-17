package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class LockIcon extends SVGIcon {

    public LockIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public LockIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,10h-1V7c0-3.309-2.691-6-6-6S6,3.691,6,7v3H5c-1.654,0-3,1.346-3,3v7c0,1.654,1.346," +
                "3,3,3h14c1.654,0,3-1.346,3-3v-7 C22,11.346,20.654,10,19,10z M8,7c0-2.206,1.794-4,4-4c2.206,0,4," +
                "1.794,4,4v3H8V7z M20,20c0,0.552-0.448,1-1,1H5 c-0.551,0-1-0.448-1-1v-7c0-0.551,0.449-1," +
                "1-1h14c0.552,0,1,0.449,1,1V20z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "lock-icon";
    }
}
