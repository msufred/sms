package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MousePointerIcon extends SVGIcon {

    public MousePointerIcon() {
        super();
    }

    public MousePointerIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20.354,9.147l-16.97-7.07C3.01,1.92,2.58,2.005,2.293,2.293C2.006,2.58,1.921,3.011,2.077," +
                "3.385l7.07,16.97 c0.155,0.373,0.52,0.615,0.923,0.615c0.011,0,0.022,0,0.034-0.001c0.416-0.014," +
                "0.779-0.283,0.913-0.678l1.993-5.867l5.283,5.283 C18.488,19.902,18.744,20,19,20s0.512-0.098," +
                "0.707-0.293c0.391-0.391,0.391-1.023,0-1.414l-5.283-5.283l5.867-1.993 c0.395-0.134,0.664-0.498," +
                "0.678-0.913C20.983,9.688,20.738,9.307,20.354,9.147z M12.258,11.633c-0.294,0.1-0.525,0.331-0.625," +
                "0.625 l-1.659,4.882L4.857,4.857l12.284,5.118L12.258,11.633z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "mouse-pointer-icon";
    }
}
