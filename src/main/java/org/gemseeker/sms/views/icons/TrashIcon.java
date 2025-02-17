package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TrashIcon extends SVGIcon {

    public TrashIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public TrashIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21,5h-4V4c0-1.654-1.346-3-3-3h-4C8.346,1,7,2.346,7,4v1H3C2.448,5,2,5.448,2,6s0.448,1,1," +
                "1h1v13c0,1.654,1.346,3,3,3h10 c1.654,0,3-1.346,3-3V7h1c0.553,0,1-0.448,1-1S21.553,5,21,5z " +
                "M9,4c0-0.551,0.449-1,1-1h4c0.552,0,1,0.449,1,1v1H9V4z M18,20 c0,0.552-0.448,1-1,1H7c-0.551," +
                "0-1-0.448-1-1V7h12V20z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "trash-icon";
    }
}
