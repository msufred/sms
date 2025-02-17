package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ShieldIcon extends SVGIcon {

    public ShieldIcon() {
        super(DEFAULT_SIZE * 0.8, DEFAULT_SIZE);
    }

    public ShieldIcon(double size) {
        super(size * 0.8, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20.352,4.063l-8-3c-0.227-0.085-0.476-0.085-0.702,0l-8,3C3.259,4.21,3,4.583,3,5v7c0,6.543," +
                "8.204,10.72,8.553,10.895 C11.693,22.965,11.847,23,12,23s0.307-0.035,0.447-0.105C12.797,22.72,21," +
                "18.543,21,12V5C21,4.583,20.741,4.21,20.352,4.063z M19,12 c0,4.536-5.393,7.946-7.001,8.862C10.39," +
                "19.948,5,16.549,5,12V5.693l7-2.625l7,2.625V12z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "shield-icon";
    }
}
