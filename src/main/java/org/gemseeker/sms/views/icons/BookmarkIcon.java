package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class BookmarkIcon extends SVGIcon {

    public BookmarkIcon() {
        super(DEFAULT_SIZE * 0.75, DEFAULT_SIZE);
    }

    public BookmarkIcon(double size) {
        super(size * 0.75, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m13,0H3C1.35,0,0,1.35,0,3v16c0,.38.21.72.54.89s.73.14,1.04-.08l6.42-4.58,6.42,4.58c.17.12.38.19" +
                ".58.19.16,0,.31-.04.46-.11.33-.17.54-.51.54-.89V3c0-1.65-1.35-3-3-3Zm1,17.06l-5.42-3.87c-.17-.12-.38-." +
                "19-.58-.19s-.41.06-.58.19l-5.42,3.87V3c0-.55.45-1,1-1h10c.55,0,1,.45,1,1v14.06Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "bookmark-icon";
    }
}
