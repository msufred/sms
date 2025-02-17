package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class PrinterIcon extends SVGIcon {

    public PrinterIcon() {
        super();
    }

    public PrinterIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,8h-1V2c0-0.552-0.447-1-1-1H6C5.448,1,5,1.448,5,2v6H4c-1.654,0-3,1.346-3,3v5c0," +
                "1.654,1.346,3,3,3h1v3 c0,0.553,0.448,1,1,1h12c0.553,0,1-0.447,1-1v-3h1c1.654,0,3-1.346," +
                "3-3v-5C23,9.346,21.654,8,20,8z M7,3h10v5H7V3z M17,21H7v-6h10 V21z M21,16c0,0.552-0.448," +
                "1-1,1h-1v-3c0-0.553-0.447-1-1-1H6c-0.552,0-1,0.447-1,1v3H4c-0.551,0-1-0.448-1-1v-5 " +
                "c0-0.551,0.449-1,1-1h16c0.552,0,1,0.449,1,1V16z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "printer-icon";
    }
}
