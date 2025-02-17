package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class VolumeXIcon extends SVGIcon {

    public VolumeXIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.7);
    }

    public VolumeXIcon(double size) {
        super(size, size * 0.7);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M11.433,4.099c-0.346-0.165-0.758-0.12-1.058,0.121L5.649,8H2C1.448,8,1,8.448,1,9v6c0," +
                "0.553,0.448,1,1,1h3.649 l4.726,3.781C10.556,19.926,10.777,20,11,20c0.147,0,0.295-0.032,0.433-" +
                "0.099C11.78,19.734,12,19.385,12,19V5 C12,4.616,11.78,4.265,11.433,4.099z M10,16.919l-3.375-" +
                "2.7C6.447,14.077,6.227,14,6,14H3v-4h3c0.227,0,0.447-0.077,0.625-0.219 L10,7.081V16.919z " +
                "M21.414,12l2.293-2.293c0.391-0.391,0.391-1.023,0-1.414s-1.023-0.391-1.414,0L20,10.586l-" +
                "2.293-2.293 c-0.391-0.391-1.023-0.391-1.414,0s-0.391,1.023,0,1.414L18.586,12l-2.293," +
                "2.293c-0.391,0.391-0.391,1.023,0,1.414 C16.488,15.902,16.744,16,17,16s0.512-0.098,0.707-" +
                "0.293L20,13.414l2.293,2.293C22.488,15.902,22.744,16,23,16 s0.512-0.098,0.707-0.293c0.391-" +
                "0.391,0.391-1.023,0-1.414L21.414,12z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "volume-x-icon";
    }
}
