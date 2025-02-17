package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class RssIcon extends SVGIcon {

    public RssIcon() {
        super();
    }

    public RssIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M4,10c-0.552,0-1,0.448-1,1s0.448,1,1,1c4.411,0,8,3.589,8,8c0,0.553,0.448,1,1,1c0.553," +
                "0,1-0.447,1-1 C14,14.486,9.514,10,4,10z M4,3C3.448,3,3,3.448,3,4s0.448,1,1,1c8.271,0,15,6.729," +
                "15,15c0,0.553,0.447,1,1,1s1-0.447,1-1C21,10.626,13.374,3,4,3z M3,19a2,2 0 1,0 4,0a2,2 0 1,0 -4,0z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "rss-icon";
    }
}
