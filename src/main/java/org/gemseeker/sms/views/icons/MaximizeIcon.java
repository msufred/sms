package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MaximizeIcon extends SVGIcon {

    public MaximizeIcon() {
        super();
    }

    public MaximizeIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M8,2H5C3.346,2,2,3.346,2,5v3c0,0.552,0.448,1,1,1s1-0.448,1-1V5c0-0.551,0.449-1,1-1h3c0.552," +
                "0,1-0.448,1-1S8.552,2,8,2z M19,2h-3c-0.553,0-1,0.448-1,1s0.447,1,1,1h3c0.552,0,1,0.449,1,1v3c0," +
                "0.552,0.447,1,1,1s1-0.448,1-1V5 C22,3.346,20.654,2,19,2z M8,20H5c-0.551,0-1-0.448-1-1v-3c0-0.553-" +
                "0.448-1-1-1s-1,0.447-1,1v3c0,1.654,1.346,3,3,3h3c0.552,0,1-0.447,1-1 S8.552,20,8,20z M21,15c-0.553," +
                "0-1,0.447-1,1v3c0,0.552-0.448,1-1,1h-3c-0.553,0-1,0.447-1,1s0.447,1,1,1h3c1.654,0,3-1.346,3-3v-3 " +
                "C22,15.447,21.553,15,21,15z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "maximize-icon";
    }
}
