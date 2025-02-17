package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class SlidersIcon extends SVGIcon {

    public SlidersIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public SlidersIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M4,11c0.552,0,1-0.448,1-1V3c0-0.552-0.448-1-1-1S3,2.448,3,3v7C3,10.552,3.448,11,4,11z " +
                "M12,11c-0.552,0-1,0.448-1,1v9c0,0.553,0.448,1,1,1c0.553,0,1-0.447,1-1v-9C13,11.448,12.553,11,12,11z " +
                "M20,13c0.553,0,1-0.447,1-1V3c0-0.552-0.447-1-1-1s-1,0.448-1,1v9C19,12.553,19.447,13,20,13z " +
                "M7,13H1c-0.552,0-1,0.447-1,1s0.448,1,1,1h2v6c0,0.553,0.448,1,1,1s1-0.447,1-1v-6h2c0.552,0,1-0.447,1-1S7.552,13,7,13z " +
                "M15,7h-2V3c0-0.552-0.447-1-1-1c-0.552,0-1,0.448-1,1v4H9C8.448,7,8,7.448,8,8s0.448,1,1,1h6c0.553,0,1-0.448,1-1 " +
                "S15.553,7,15,7z M23,15h-6c-0.553,0-1,0.447-1,1s0.447,1,1,1h2v4c0,0.553,0.447,1,1,1s1-0.447,1-1v-4h2c0.553,0,1-0.447,1-1 " +
                "S23.553,15,23,15z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "sliders-icon";
    }
}
