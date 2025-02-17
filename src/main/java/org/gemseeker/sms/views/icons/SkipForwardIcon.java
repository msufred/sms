package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class SkipForwardIcon extends SVGIcon {

    public SkipForwardIcon() {
        super(DEFAULT_SIZE * 0.95, DEFAULT_SIZE);
    }

    public SkipForwardIcon(double size) {
        super(size * 0.95, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M5.625,3.219c-0.3-0.24-0.712-0.286-1.058-0.121C4.22,3.265,4,3.616,4,4v16c0,0.385,0.22," +
                "0.734,0.567,0.901 C4.705,20.968,4.853,21,5,21c0.223,0,0.444-0.074,0.625-0.219l10-8C15.862,12.591," +
                "16,12.304,16,12s-0.138-0.591-0.375-0.781 L5.625,3.219z M6,17.919V6.081L13.399,12L6,17.919z M19," +
                "4c-0.553,0-1,0.448-1,1v14c0,0.553,0.447,1,1,1s1-0.447,1-1V5C20,4.448,19.553,4,19,4z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "skip-forward-icon";
    }
}
