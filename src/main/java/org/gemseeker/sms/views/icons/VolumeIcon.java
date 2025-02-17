package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class VolumeIcon extends SVGIcon {

    public VolumeIcon() {
        super(DEFAULT_SIZE * 0.45, DEFAULT_SIZE * 0.7);
    }

    public VolumeIcon(double size) {
        super(size * 0.45, size * 0.7);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M11.433,4.099c-0.346-0.165-0.758-0.12-1.058,0.121L5.649,8H2C1.448,8,1,8.448,1,9v6c0," +
                "0.553,0.448,1,1,1h3.649l4.726,3.781 C10.556,19.926,10.777,20,11,20c0.147,0,0.295-0.032,0.433-" +
                "0.099C11.78,19.734,12,19.385,12,19V5C12,4.616,11.78,4.265,11.433,4.099 z M10,16.919l-3.375-" +
                "2.7C6.447,14.077,6.227,14,6,14H3v-4h3c0.227,0,0.447-0.077,0.625-0.219L10,7.081V16.919z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "volume-icon";
    }
}
