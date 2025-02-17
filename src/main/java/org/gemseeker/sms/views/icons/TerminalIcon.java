package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TerminalIcon extends SVGIcon {

    public TerminalIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.95);
    }

    public TerminalIcon(double size) {
        super(size, size * 0.95);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M10.707,10.293l-6-6c-0.391-0.391-1.023-0.391-1.414,0s-0.391,1.023,0,1.414L8.586," +
                "11l-5.293,5.293 c-0.391,0.391-0.391,1.023,0,1.414C3.488,17.902,3.744,18,4,18s0.512-0.098," +
                "0.707-0.293l6-6 C11.098,11.316,11.098,10.684,10.707,10.293z M20,18h-8c-0.552,0-1,0.447-1," +
                "1s0.448,1,1,1h8c0.553,0,1-0.447,1-1S20.553,18,20,18z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "terminal-icon";
    }
}
