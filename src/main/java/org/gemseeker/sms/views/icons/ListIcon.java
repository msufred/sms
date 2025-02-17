package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ListIcon extends SVGIcon {

    public ListIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.75);
    }

    public ListIcon(double size) {
        super(size, size * 0.75);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M8,7h13c0.553,0,1-0.448,1-1s-0.447-1-1-1H8C7.448,5,7,5.448,7,6S7.448,7,8,7z " +
                "M21,11H8c-0.552,0-1,0.448-1,1c0,0.553,0.448,1,1,1h13c0.553,0,1-0.447,1-1C22,11.448,21.553,11,21,11z " +
                "M21,17H8c-0.552,0-1,0.447-1,1s0.448,1,1,1h13c0.553,0,1-0.447,1-1S21.553,17,21,17z " +
                "M3.01,5H3C2.448,5,2.005,5.448,2.005,6S2.458,7,3.01,7s1-0.448,1-1S3.562,5,3.01,5z " +
                "M3.01,11H3c-0.552,0-0.995,0.448-0.995,1c0,0.553,0.453,1,1.005,1s1-0.447,1-1C4.01,11.448,3.562,11,3.01,11z " +
                "M3.01,17H3c-0.552,0-0.995,0.447-0.995,1s0.453,1,1.005,1s1-0.447,1-1S3.562,17,3.01,17z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "list-icon";
    }
}
