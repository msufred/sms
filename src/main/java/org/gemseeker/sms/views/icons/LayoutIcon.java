package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class LayoutIcon extends SVGIcon {

    public LayoutIcon() {
        super();
    }

    public LayoutIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,2H5C3.346,2,2,3.346,2,5v14c0,1.654,1.346,3,3,3h14c1.654,0,3-1.346,3-3V5C22,3.346,20.654,2,19,2z " +
                "M5,4h14 c0.552,0,1,0.449,1,1v3H4V5C4,4.449,4.449,4,5,4z M4,19v-9h4v10H5C4.449,20,4,19.552,4,19z " +
                "M19,20h-9V10h10v9 C20,19.552,19.552,20,19,20z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "layout-icon";
    }
}
