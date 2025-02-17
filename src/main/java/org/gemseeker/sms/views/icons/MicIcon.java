package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MicIcon extends SVGIcon {

    public MicIcon() {
        super(DEFAULT_SIZE * 0.7, DEFAULT_SIZE);
    }

    public MicIcon(double size) {
        super(size * 0.7, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,16c2.206,0,4-1.794,4-4V4c0-2.206-1.794-4-4-4C9.794,0,8,1.794,8,4v8C8,14.206,9.794,16,12,16z " +
                "M10,4 c0-1.103,0.897-2,2-2c1.103,0,2,0.897,2,2v8c0,1.103-0.897,2-2,2c-1.103,0-2-0.897-2-2V4z " +
                "M19,9c-0.553,0-1,0.448-1,1v2c0,3.309-2.691,6-6,6s-6-2.691-6-6v-2c0-0.552-0.448-1-1-1s-1,0.448-1,1v2 " +
                "c0,4.072,3.06,7.436,7,7.931V22H8c-0.552,0-1,0.447-1,1s0.448,1,1,1h8c0.553,0,1-0.447,1-1s-0.447-1-1-1h-3v-2.069 " +
                "c3.94-0.495,7-3.859,7-7.931v-2C20,9.448,19.553,9,19,9z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "mic-icon";
    }
}
