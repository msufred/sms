package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class SpeakerIcon extends SVGIcon {

    public SpeakerIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public SpeakerIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M18,23H6c-1.654,0-3-1.346-3-3V4c0-1.654,1.346-3,3-3h12c1.654,0,3,1.346,3,3v16C21," +
                "21.654,19.654,23,18,23z M6,3 C5.449,3,5,3.449,5,4v16c0,0.552,0.449,1,1,1h12c0.552,0,1-0.448," +
                "1-1V4c0-0.551-0.448-1-1-1H6z M12,19c-2.757,0-5-2.243-5-5s2.243-5,5-5s5,2.243,5,5S14.757,19," +
                "12,19z M12,11c-1.654,0-3,1.346-3,3s1.346,3,3,3 s3-1.346,3-3S13.654,11,12,11z M12.01,7c-0.552," +
                "0-1.005-0.448-1.005-1S11.448,5,12,5h0.01c0.553,0,1,0.448,1,1S12.563,7,12.01,7z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "speaker-icon";
    }
}
