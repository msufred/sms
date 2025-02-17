package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class UserPlusIcon extends SVGIcon {

    public UserPlusIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.8);
    }

    public UserPlusIcon(double size) {
        super(size, size * 0.8);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,14H5c-2.757,0-5,2.243-5,5v2c0,0.553,0.448,1,1,1s1-0.447,1-1v-2c0-1.654,1.346-3," +
                "3-3h7c1.654,0,3,1.346,3,3v2 c0,0.553,0.447,1,1,1s1-0.447,1-1v-2C17,16.243,14.757,14,12,14z " +
                "M8.5,12c2.757,0,5-2.243,5-5s-2.243-5-5-5s-5,2.243-5,5S5.743,12,8.5,12z M8.5,4c1.654,0,3,1.346," +
                "3,3s-1.346,3-3,3 s-3-1.346-3-3S6.846,4,8.5,4z M23,10h-2V8c0-0.552-0.447-1-1-1s-1,0.448-1," +
                "1v2h-2c-0.553,0-1,0.448-1,1s0.447,1,1,1h2v2c0,0.553,0.447,1,1,1s1-0.447,1-1 v-2h2c0.553,0," +
                "1-0.448,1-1S23.553,10,23,10z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "user-plus-icon";
    }
}
