package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class HashIcon extends SVGIcon {

    public HashIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public HashIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,14h-4.216l0.444-4H20c0.553,0,1-0.448,1-1s-0.447-1-1-1h-3.549l0.543-4.89c0.061-0.549-0.335-1.043-0.884-1.104 " +
                "c-0.539-0.064-1.043,0.334-1.104,0.883L14.438,8H10.45l0.543-4.89c0.061-0.549-0.334-1.043-0.883-1.104 " +
                "C9.56,1.942,9.067,2.341,9.006,2.89L8.438,8H4C3.448,8,3,8.448,3,9s0.448,1,1,1h4.216l-0.444,4H4c-0.552," +
                "0-1,0.447-1,1s0.448,1,1,1 h3.55l-0.543,4.89c-0.061,0.549,0.334,1.043,0.883,1.104C7.927,21.998,7.964," +
                "22,8.001,22c0.502,0,0.936-0.378,0.992-0.89L9.562,16 h3.988l-0.543,4.89c-0.061,0.549,0.335,1.043,0.884," +
                "1.104C13.927,21.998,13.964,22,14.001,22c0.503,0,0.937-0.378,0.993-0.89 L15.562,16H20c0.553,0,1-0.447," +
                "1-1S20.553,14,20,14z M9.784,14l0.444-4h3.988l-0.444,4H9.784z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "hash-icon";
    }
}
