package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TvIcon extends SVGIcon {

    public TvIcon() {
        super();
    }

    public TvIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,6h-5.586l3.293-3.293c0.391-0.391,0.391-1.023,0-1.414s-1.023-0.391-1.414,0L12,5.586L7.707," +
                "1.293 c-0.391-0.391-1.023-0.391-1.414,0s-0.391,1.023,0,1.414L9.586,6H4C2.346,6,1,7.346,1,9v11c0,1.654," +
                "1.346,3,3,3h16 c1.654,0,3-1.346,3-3V9C23,7.346,21.654,6,20,6z M21,20c0,0.552-0.448,1-1,1H4c-0.551," +
                "0-1-0.448-1-1V9c0-0.551,0.449-1,1-1h16 c0.552,0,1,0.449,1,1V20z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "tv-icon";
    }
}
