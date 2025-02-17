package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class LinkedinIcon extends SVGIcon {

    public LinkedinIcon() {
        super();
    }

    public LinkedinIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M16,7c-3.86,0-7,3.14-7,7v7c0,0.553,0.448,1,1,1h4c0.553,0,1-0.447,1-1v-7c0-0.552,0.448-1," +
                "1-1s1,0.448,1,1v7 c0,0.553,0.447,1,1,1h4c0.553,0,1-0.447,1-1v-7C23,10.14,19.859,7,16,7z " +
                "M21,20h-2v-6c0-1.654-1.346-3-3-3s-3,1.346-3,3v6h-2v-6 c0-2.757,2.243-5,5-5s5,2.243,5,5V20z " +
                "M6,8H2C1.448,8,1,8.448,1,9v12c0,0.553,0.448,1,1,1h4c0.552,0,1-0.447,1-1V9C7,8.448,6.552,8,6,8z " +
                "M5,20H3V10h2V20z M4,1C2.346,1,1,2.346,1,4s1.346,3,3,3s3-1.346,3-3S5.654,1,4,1z " +
                "M4,5C3.449,5,3,4.551,3,4s0.449-1,1-1s1,0.449,1,1 S4.551,5,4,5z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "linkedin-icon";
    }
}
