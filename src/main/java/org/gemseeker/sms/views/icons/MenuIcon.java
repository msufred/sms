package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MenuIcon extends SVGIcon {

    public MenuIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.75);
    }

    public MenuIcon(double size) {
        super(size, size * 0.75);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21,11H3c-0.552,0-1,0.448-1,1c0,0.553,0.448,1,1,1h18c0.553,0,1-0.447,1-1C22,11.448,21.553,11,21,11z " +
                "M3,7h18c0.553,0,1-0.448,1-1s-0.447-1-1-1H3C2.448,5,2,5.448,2,6S2.448,7,3,7z " +
                "M21,17H3c-0.552,0-1,0.447-1,1s0.448,1,1,1h18c0.553,0,1-0.447,1-1S21.553,17,21,17z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "list-icon";
    }
}
