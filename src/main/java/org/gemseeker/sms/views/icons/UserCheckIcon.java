package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class UserCheckIcon extends SVGIcon {

    public UserCheckIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.8);
    }

    public UserCheckIcon(double size) {
        super(size, size * 0.8);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,14H5c-2.757,0-5,2.243-5,5v2c0,0.553,0.448,1,1,1s1-0.447,1-1v-2c0-1.654,1.346-3," +
                "3-3h7c1.654,0,3,1.346,3,3v2 c0,0.553,0.447,1,1,1s1-0.447,1-1v-2C17,16.243,14.757,14,12,14z " +
                "M8.5,12c2.757,0,5-2.243,5-5s-2.243-5-5-5s-5,2.243-5,5S5.743,12,8.5,12z M8.5,4c1.654,0,3,1.346," +
                "3,3s-1.346,3-3,3 s-3-1.346-3-3S6.846,4,8.5,4z M23.707,8.293c-0.391-0.391-1.023-0.391-1.414,0L19," +
                "11.586l-1.293-1.293c-0.391-0.391-1.023-0.391-1.414,0 s-0.391,1.023,0,1.414l2,2C18.488,13.902," +
                "18.744,14,19,14s0.512-0.098,0.707-0.293l4-4C24.098,9.316,24.098,8.684,23.707,8.293z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "user-check-icon";
    }
}
