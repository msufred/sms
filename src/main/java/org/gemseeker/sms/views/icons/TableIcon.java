package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TableIcon extends SVGIcon {

    public TableIcon() {
        super();
    }

    public TableIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,2H5C3.346,2,2,3.346,2,5v14c0,1.654,1.346,3,3,3h14c1.654," +
                "0,3-1.346,3-3V5C22,3.346,20.654,2,19,2z M8,20H5 c-0.551,0-1-0.448-1-1v-9h4V20z " +
                "M8,8H4V5c0-0.551,0.449-1,1-1h3V8z M20,19c0,0.552-0.448,1-1,1h-9V10h10V19z " +
                "M20,8H10V4h9 c0.552,0,1,0.449,1,1V8z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "table-icon";
    }
}
