package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ServerIcon extends SVGIcon {

    public ServerIcon() {
        super();
    }

    public ServerIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,1H4C2.346,1,1,2.346,1,4v4c0,1.654,1.346,3,3,3h16c1.654,0,3-1.346,3-3V4C23,2.346,21.654,1,20,1z " +
                "M21,8 c0,0.551-0.448,1-1,1H4C3.449,9,3,8.551,3,8V4c0-0.551,0.449-1,1-1h16c0.552,0,1,0.449,1,1V8z " +
                "M20,13H4c-1.654,0-3,1.346-3,3v4c0,1.654,1.346,3,3,3h16c1.654,0,3-1.346,3-3v-4C23,14.346,21.654,13,20,13z " +
                "M21,20 c0,0.552-0.448,1-1,1H4c-0.551,0-1-0.448-1-1v-4c0-0.552,0.449-1,1-1h16c0.552,0,1,0.448,1,1V20z " +
                "M6.01,5H6C5.448,5,5.005,5.448,5.005,6S5.458,7,6.01,7s1-0.448,1-1S6.562,5,6.01,5z M6.01,17H6c-0.552," +
                "0-0.995,0.447-0.995,1s0.453,1,1.005,1s1-0.447,1-1S6.562,17,6.01,17z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "server-icon";
    }
}
