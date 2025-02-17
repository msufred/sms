package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class SidebarIcon extends SVGIcon {

    public SidebarIcon() {
        super();
    }

    public SidebarIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,2H5C3.346,2,2,3.346,2,5v14c0,1.654,1.346,3,3,3h14c1.654,0,3-1.346,3-3V5C22,3.346,20.654,2,19,2z " +
                "M4,19V5 c0-0.551,0.449-1,1-1h3v16H5C4.449,20,4,19.552,4,19z M20,19c0,0.552-0.448,1-1,1h-9V4h9c0.552,0,1,0.449,1,1V19z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "sidebar-icon";
    }
}
