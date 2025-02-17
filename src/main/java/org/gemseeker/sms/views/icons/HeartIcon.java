package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class HeartIcon extends SVGIcon {

    public HeartIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public HeartIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.547,3.903c-1.228-1.228-2.86-1.905-4.597-1.905c-1.728,0-3.352,0.669-4.577,1.885c-0.007," +
                "0.007-0.014,0.013-0.021,0.02 L12,4.255l-0.353-0.353c-1.228-1.228-2.86-1.904-4.597-1.904c-1.736,0-3.369," +
                "0.676-4.597,1.904S0.549,6.763,0.549,8.5 s0.676,3.369,1.904,4.598l8.84,8.84C11.48,22.125,11.735,22.23," +
                "12,22.23s0.52-0.105,0.707-0.293l8.84-8.84 C24.082,10.563,24.083,6.44,21.547,3.903z M20.133,11.683L12," +
                "19.816l-8.133-8.133c-0.85-0.85-1.318-1.98-1.318-3.183 s0.468-2.333,1.318-3.183s1.98-1.318," +
                "3.183-1.318c1.203,0,2.333,0.468,3.183,1.318l1.06,1.06c0.375,0.375,1.039,0.375,1.414,0 " +
                "l1.05-1.05c0.004-0.004,0.008-0.007,0.012-0.011c0.85-0.85,1.979-1.317,3.182-1.317s2.332," +
                "0.468,3.185,1.321 C21.889,7.074,21.888,9.929,20.133,11.683z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "heart-icon";
    }
}
