package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class SkipBackIcon extends SVGIcon {

    public SkipBackIcon() {
        super(DEFAULT_SIZE * 0.95, DEFAULT_SIZE);
    }

    public SkipBackIcon(double size) {
        super(size * 0.95, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19.434,3.099c-0.348-0.166-0.759-0.12-1.059,0.121l-10,8C8.138,11.409,8,11.696,8,12s0.138," +
                "0.591,0.375,0.781l10,8 C18.556,20.926,18.777,21,19,21c0.147,0,0.296-0.032,0.434-0.099C19.779,20.734," +
                "20,20.385,20,20V4 C20,3.616,19.779,3.265,19.434,3.099z M18,17.919L10.601,12L18,6.081V17.919z M5," +
                "4C4.448,4,4,4.448,4,5v14c0,0.553,0.448,1,1,1s1-0.447,1-1V5C6,4.448,5.552,4,5,4z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "skip-back-icon";
    }
}
