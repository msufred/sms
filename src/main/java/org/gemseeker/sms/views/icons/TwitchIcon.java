package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TwitchIcon extends SVGIcon {

    public TwitchIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public TwitchIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21,1H3C2.448,1,2,1.448,2,2v16c0,0.553,0.448,1,1,1h4v3c0,0.404,0.244,0.77,0.617,0.924C7.741," +
                "22.976,7.871,23,8,23 c0.26,0,0.516-0.102,0.707-0.293L12.414,19H17c0.266,0,0.52-0.105,0.707-0.293l4" +
                "-4C21.895,14.52,22,14.266,22,14V2 C22,1.448,21.553,1,21,1z M20,13.586L16.586,17H12c-0.265,0-0.52," +
                "0.105-0.707,0.293L9,19.586V18c0-0.553-0.448-1-1-1H4V3h16V13.586z M16,12c0.553,0,1-0.448,1-1V7c0-" +
                "0.552-0.447-1-1-1s-1,0.448-1,1v4C15,11.552,15.447,12,16,12z M11,12c0.552,0,1-0.448,1-1V7c0-0.552-" +
                "0.448-1-1-1s-1,0.448-1,1v4C10,11.552,10.448,12,11,12z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "twitch-icon";
    }
}
