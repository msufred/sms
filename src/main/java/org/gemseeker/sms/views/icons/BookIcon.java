package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class BookIcon extends SVGIcon {

    public BookIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public BookIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m17,0H3.5C1.57,0,0,1.57,0,3.5v15c0,1.93,1.57,3.5,3.5,3.5h13.5c.55,0,1-.45,1-1V1c0-.55-" +
                ".45-1-1-1ZM3.5,2h12.5v13H3.5c-.54,0-1.04.13-1.5.35V3.5c0-.83.67-1.5,1.5-1.5Zm0,18c-.83,0-1.5-." +
                "67-1.5-1.5s.67-1.5,1.5-1.5h12.5v3H3.5Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "book-icon";
    }
}
