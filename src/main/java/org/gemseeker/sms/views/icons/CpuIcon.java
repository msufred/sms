package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CpuIcon extends SVGIcon {

    public CpuIcon() {
        super();
    }

    public CpuIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M15,8H9C8.448,8,8,8.448,8,9v6c0,0.553,0.448,1,1,1h6c0.553,0,1-0.447,1-1V9C16,8.448,15.553,8,15,8z M14,14h-4v-4h4V14z " +
                "M23,13h-2v-3h2c0.553,0,1-0.448,1-1s-0.447-1-1-1h-2V6c0-1.654-1.346-3-3-3h-2V1c0-0.552-0.447-1-1-1s-1,0.448-1,1v2h-4V1 " +
                "c0-0.552-0.448-1-1-1S8,0.448,8,1v2H6C4.346,3,3,4.346,3,6v2H1C0.448,8,0,8.448,0,9s0.448,1,1,1h2v3H1c-0.552,0-1,0.447-1,1" +
                "s0.448,1,1,1h2v3c0,1.654,1.346,3,3,3h2v2c0,0.553,0.448,1,1,1s1-0.447,1-1v-2h4v2c0,0.553,0.447,1,1,1s1-0.447,1-1v-2h2" +
                "c1.654,0,3-1.346,3-3v-3h2c0.553,0,1-0.447,1-1S23.553,13,23,13z M19,18c0,0.552-0.448,1-1,1H6c-0.551,0-1-0.448-1-1V6" +
                "c0-0.551,0.449-1,1-1h12c0.552,0,1,0.449,1,1V18z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "cpu-icon";
    }
}
