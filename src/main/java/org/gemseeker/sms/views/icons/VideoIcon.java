package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class VideoIcon extends SVGIcon {

    public VideoIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.7);
    }

    public VideoIcon(double size) {
        super(size, size * 0.7);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M23.458,6.111c-0.334-0.172-0.735-0.142-1.039,0.075L17,10.057V7c0-1.654-1.346-3-3-3H3C1.346," +
                "4,0,5.346,0,7v10 c0,1.654,1.346,3,3,3h11c1.654,0,3-1.346,3-3v-3.057l5.419,3.871C22.592,17.938,22.795," +
                "18,23,18c0.156,0,0.313-0.037,0.458-0.11 C23.791,17.718,24,17.375,24,17V7C24,6.625,23.791,6.282,23.458," +
                "6.111z M15,17c0,0.552-0.448,1-1,1H3c-0.551,0-1-0.448-1-1V7 c0-0.551,0.449-1,1-1h11c0.552,0,1,0.449,1," +
                "1V17z M22,15.057L17.721,12L22,8.943V15.057z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "video-icon";
    }
}
