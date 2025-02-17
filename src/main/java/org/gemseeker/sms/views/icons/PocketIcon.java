package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class PocketIcon extends SVGIcon {

    public PocketIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.95);
    }

    public PocketIcon(double size) {
        super(size, size * 0.95);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,2H4C2.346,2,1,3.346,1,5v6c0,6.065,4.935,11,11,11s11-4.935,11-11V5C23,3.346,21.654,2," +
                "20,2z M21,11 c0,4.963-4.037,9-9,9c-4.962,0-9-4.037-9-9V5c0-0.551,0.449-1,1-1h16c0.552,0,1,0.449," +
                "1,1V11z M15.293,9.293L12,12.586L8.707,9.293c-0.391-0.391-1.023-0.391-1.414,0s-0.391,1.023,0,1.414l4," +
                "4 C11.488,14.902,11.744,15,12,15s0.512-0.098,0.707-0.293l4-4c0.391-0.391,0.391-1.023,0-1.414S15.684," +
                "8.902,15.293,9.293z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "pocket-icon";
    }
}
