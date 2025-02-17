package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class Volume1Icon extends SVGIcon {

    public Volume1Icon() {
        super(DEFAULT_SIZE * 0.755, DEFAULT_SIZE * 0.7);
    }

    public Volume1Icon(double size) {
        super(size * 0.755, size * 0.7);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M11.433,4.099c-0.346-0.165-0.758-0.12-1.058,0.121L5.649,8H2C1.448,8,1,8.448,1,9v6c0," +
                "0.553,0.448,1,1,1h3.649 l4.726,3.781C10.556,19.926,10.777,20,11,20c0.147,0,0.295-0.032,0.433-" +
                "0.099C11.78,19.734,12,19.385,12,19V5 C12,4.616,11.78,4.265,11.433,4.099z M10,16.919l-3.375-" +
                "2.7C6.447,14.077,6.227,14,6,14H3v-4h3c0.227,0,0.447-0.077,0.625-0.219 L10,7.081V16.919z " +
                "M16.247,7.753c-0.391-0.391-1.023-0.391-1.414,0c-0.391,0.39-0.391,1.023,0,1.414c1.559,1.559," +
                "1.559,4.097,0,5.656 c-0.391,0.391-0.391,1.023,0,1.414c0.195,0.195,0.451,0.293,0.707," +
                "0.293s0.512-0.098,0.707-0.293 C18.586,13.897,18.586,10.092,16.247,7.753z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "volume-1-icon";
    }
}
