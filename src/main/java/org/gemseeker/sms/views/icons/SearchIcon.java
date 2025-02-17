package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class SearchIcon extends SVGIcon {

    public SearchIcon() {
        super();
    }

    public SearchIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m19.71,18.29l-3.68-3.68c1.23-1.54,1.97-3.49,1.97-5.61C18,4.04,13.96,0,9,0S0,4.04,0,9s4.04,9,9," +
                "9c2.12,0,4.07-.74,5.61-1.97l3.68,3.68c.2.2.45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02,0-1.41ZM2,9c0-3.86," +
                "3.14-7,7-7s7,3.14,7,7c0,1.92-.78,3.66-2.04,4.93,0,0-.01,0-.02.01s0,.01-.01.02c-1.27,1.26-3.01,2.04-4.93," +
                "2.04-3.86,0-7-3.14-7-7Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "search-icon";
    }
}
