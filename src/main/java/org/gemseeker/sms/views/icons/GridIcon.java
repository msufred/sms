package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class GridIcon extends SVGIcon {

    public GridIcon() {
        super();
    }

    public GridIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M10,2H3C2.448,2,2,2.448,2,3v7c0,0.552,0.448,1,1,1h7c0.552,0,1-0.448,1-1V3C11,2.448,10.552,2,10,2z " +
                "M9,9H4V4h5V9z M21,2h-7c-0.553,0-1,0.448-1,1v7c0,0.552,0.447,1,1,1h7c0.553,0,1-0.448,1-1V3C22,2.448,21.553,2,21,2z " +
                "M20,9h-5V4h5V9z M21,13h-7c-0.553,0-1,0.447-1,1v7c0,0.553,0.447,1,1,1h7c0.553,0,1-0.447,1-1v-7C22,13.447,21.553,13,21,13z " +
                "M20,20h-5v-5 h5V20z M10,13H3c-0.552,0-1,0.447-1,1v7c0,0.553,0.448,1,1,1h7c0.552,0,1-0.447,1-1v-7C11,13.447,10.552,13,10,13z " +
                "M9,20H4v-5h5 V20z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "grid-icon";
    }
}
