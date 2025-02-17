package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class HeadphoneIcon extends SVGIcon {

    public HeadphoneIcon() {
        super();
    }

    public HeadphoneIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,2C6.486,2,2,6.486,2,12v2v4v1c0,1.654,1.346,3,3,3h1c1.654,0,3-1.346,3-3v-3c0-1.654-1.346-3-3-3H4v-1 " +
                "c0-4.411,3.589-8,8-8s8,3.589,8,8v1h-2c-1.654,0-3,1.346-3,3v3c0,1.654,1.346,3,3,3h1c1.654,0,3-1.346,3-3v-1v-4v-2 " +
                "C22,6.486,17.514,2,12,2z M6,15c0.551,0,1,0.448,1,1v3c0,0.552-0.449,1-1,1H5c-0.551,0-1-0.448-1-1v-1v-3H6z M20,19 " +
                "c0,0.552-0.448,1-1,1h-1c-0.552,0-1-0.448-1-1v-3c0-0.552,0.448-1,1-1h2v3V19z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "headphone-icon";
    }
}
