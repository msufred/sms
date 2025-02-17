package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TrelloIcon extends SVGIcon {

    public TrelloIcon() {
        super();
    }

    public TrelloIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,2H5C3.346,2,2,3.346,2,5v14c0,1.654,1.346,3,3,3h14c1.654,0,3-1.346,3-3V5C22," +
                "3.346,20.654,2,19,2z M20,19 c0,0.552-0.448,1-1,1H5c-0.551,0-1-0.448-1-1V5c0-0.551,0.449-1," +
                "1-1h14c0.552,0,1,0.449,1,1V19z M10,6H7C6.448,6,6,6.448,6,7v9c0,0.553,0.448,1,1,1h3c0.552,0," +
                "1-0.447,1-1V7C11,6.448,10.552,6,10,6z M9,15H8V8h1V15z M17,6h-3c-0.553,0-1,0.448-1,1v5c0," +
                "0.553,0.447,1,1,1h3c0.553,0,1-0.447,1-1V7C18,6.448,17.553,6,17,6z M16,11h-1V8h1V11z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "trello-icon";
    }
}
