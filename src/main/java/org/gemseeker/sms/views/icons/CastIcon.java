package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CastIcon extends SVGIcon {

    public CastIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.875);
    }

    public CastIcon(double size) {
        super(size, size * 0.875);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m1.2,12.12c-.54-.11-1.07.24-1.18.78-.11.54.24,1.07.78,1.18,1.57.32,2.8,1.55,3.12,3.12.1.47.51." +
                "8.98.8.07,0,.13,0,.2-.02.54-.11.89-.64.78-1.18-.48-2.36-2.32-4.2-4.68-4.68Z M1.11,8.06c-.55-.06-1.04." +
                "33-1.1.88-.06.55.33,1.04.88,1.1,3.74.42,6.65,3.32,7.07,7.07.06.51.49.89.99.89.04,0,.07,0,.11,0,.55-.06" +
                ".94-.56.88-1.1-.52-4.68-4.15-8.31-8.83-8.83Z M19,0H3C1.35,0,0,1.35,0,3v2C0,5.55.45,6,1,6s1-.45,1-1v-2c0" +
                "-.55.45-1,1-1h16c.55,0,1,.45,1,1v12c0,.55-.45,1-1,1h-6c-.55,0-1,.45-1,1s.45,1,1,1h6c1.65,0,3-1.35," +
                "3-3V3c0-1.65-1.35-3-3-3Z M1.01,16h0c-.55,0-1,.45-1,1s.45,1,1,1,1-.45,1-1-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "cast-icon";
    }
}
