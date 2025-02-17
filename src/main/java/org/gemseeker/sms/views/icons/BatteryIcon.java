package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class BatteryIcon extends SVGIcon {

    public BatteryIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.575);
    }

    public BatteryIcon(double size) {
        super(size, size * 0.575);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m17,0H3C1.35,0,0,1.35,0,3v8c0,1.65,1.35,3,3,3h14c1.65,0,3-1.35,3-3V3c0-1.65-1.35-3-3-3Zm1," +
                "11c0,.55-.45,1-1,1H3c-.55,0-1-.45-1-1V3c0-.55.45-1,1-1h14c.55,0,1,.45,1,1v8Z M23,5c-.55,0-1,.45-1," +
                "1v2c0,.55.45,1,1,1s1-.45,1-1v-2c0-.55-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "battery-icon";
    }
}
