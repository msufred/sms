package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class UnderlineIcon extends SVGIcon {

    public UnderlineIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public UnderlineIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,17c3.859,0,7-3.141,7-7V3c0-0.552-0.447-1-1-1s-1,0.448-1,1v7c0,2.757-2.243,5-5," +
                "5s-5-2.243-5-5V3c0-0.552-0.448-1-1-1 S5,2.448,5,3v7C5,13.859,8.14,17,12,17z M20,20H4c-0.552," +
                "0-1,0.447-1,1s0.448,1,1,1h16c0.553,0,1-0.447,1-1S20.553,20,20,20z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "underline-icon";
    }
}
