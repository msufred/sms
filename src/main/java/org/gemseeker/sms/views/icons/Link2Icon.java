package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class Link2Icon extends SVGIcon {

    public Link2Icon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.5);
    }

    public Link2Icon(double size) {
        super(size, size * 0.5);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M9,16H6c-2.206,0-4-1.794-4-4c0-2.206,1.794-4,4-4h3c0.552,0,1-0.448,1-1S9.552,6,9,6H6c-3.309," +
                "0-6,2.691-6,6s2.691,6,6,6 h3c0.552,0,1-0.447,1-1S9.552,16,9,16z M18,6h-3c-0.553,0-1,0.448-1,1s0.447," +
                "1,1,1h3c2.206,0,4,1.794,4,4c0,2.206-1.794,4-4,4h-3c-0.553,0-1,0.447-1,1 s0.447,1,1,1h3c3.309,0," +
                "6-2.691,6-6S21.309,6,18,6z M7,12c0,0.553,0.448,1,1,1h8c0.553,0,1-0.447,1-1c0-0.552-0.447-1-1-1H8C7.448," +
                "11,7,11.448,7,12z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "link2-icon";
    }
}
