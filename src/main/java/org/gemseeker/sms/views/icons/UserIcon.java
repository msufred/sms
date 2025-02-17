package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class UserIcon extends SVGIcon {

    public UserIcon() {
        super(DEFAULT_SIZE * 0.9, DEFAULT_SIZE);
    }

    public UserIcon(double size) {
        super(size * 0.9, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M16,14H8c-2.757,0-5,2.243-5,5v2c0,0.553,0.448,1,1,1s1-0.447,1-1v-2c0-1.654,1.346-3," +
                "3-3h8c1.654,0,3,1.346,3,3v2 c0,0.553,0.447,1,1,1s1-0.447,1-1v-2C21,16.243,18.757,14,16,14z " +
                "M12,12c2.757,0,5-2.243,5-5s-2.243-5-5-5S7,4.243,7,7S9.243,12,12,12z M12,4c1.654,0,3,1.346,3," +
                "3s-1.346,3-3,3S9,8.654,9,7 S10.346,4,12,4z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "user-icon";
    }
}
