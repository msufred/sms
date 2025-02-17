package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class SaveIcon extends SVGIcon {

    public SaveIcon() {
        super();
    }

    public SaveIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.707,7.293l-5-5C16.52,2.105,16.266,2,16,2H5C3.346,2,2,3.346,2,5v14c0,1.654,1.346,3," +
                "3,3h14c1.654,0,3-1.346,3-3V8 C22,7.735,21.895,7.48,21.707,7.293z M16,20H8v-6h8V20z M20,19c0," +
                "0.552-0.448,1-1,1h-1v-7c0-0.552-0.447-1-1-1H7 c-0.552,0-1,0.448-1,1v7H5c-0.551,0-1-0.448-1-1V5c0-0.551," +
                "0.449-1,1-1h1v4c0,0.552,0.448,1,1,1h8c0.553,0,1-0.448,1-1s-0.447-1-1-1 H8V4h7.586L20,8.414V19z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "save-icon";
    }
}
