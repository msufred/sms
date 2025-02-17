package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class DollarIcon extends SVGIcon {

    public DollarIcon() {
        super(DEFAULT_SIZE * 0.6, DEFAULT_SIZE);
    }

    public DollarIcon(double size) {
        super(size * 0.6, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M14.5,11H13V6h4c0.553,0,1-0.448,1-1s-0.447-1-1-1h-4V1c0-0.552-0.447-1-1-1c-0.552," +
                "0-1,0.448-1,1v3H9.5 C7.019,4,5,6.019,5,8.5S7.019,13,9.5,13H11v5H6c-0.552,0-1,0.447-1,1s0." +
                "448,1,1,1h5v3c0,0.553,0.448,1,1,1c0.553,0,1-0.447,1-1v-3 h1.5c2.481,0,4.5-2.019,4.5-4.5S1" +
                "6.981,11,14.5,11z M9.5,11C8.122,11,7,9.878,7,8.5S8.122,6,9.5,6H11v5H9.5z M14.5,18H13v-5h1" +
                ".5 c1.379,0,2.5,1.121,2.5,2.5S15.879,18,14.5,18z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "dollar-icon";
    }
}
