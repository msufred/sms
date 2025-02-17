package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class BatteryChargingIcon extends SVGIcon {

    public BatteryChargingIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.575);
    }

    public BatteryChargingIcon(double size) {
        super(size, size * 0.575);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m5,12h-2c-.55,0-1-.45-1-1V3c0-.55.45-1,1-1h3.19c.55,0,1-.45,1-1S6.74,0,6.19,0h-3.19C1.35,0," +
                "0,1.35,0,3v8c0,1.65,1.35,3,3,3h2c.55,0,1-.45,1-1s-.45-1-1-1Z M17,0h-2C14.45,0,14,.45,14,1s.45,1,1," +
                "1h2c.55,0,1,.45,1,1v8c0,.55-.45,1-1,1h-3.19c-.55,0-1,.45-1,1s.45,1,1,1h3.19c1.65,0,3-1.35,3-3V3C20," +
                "1.35,18.65,0,17,0Z M23,5c-.55,0-1,.45-1,1v2c0,.55.45,1,1,1s1-.45,1-1v-2c0-.55-.45-1-1-1Z M13.88," +
                "6.53c-.17-.33-.51-.53-.88-.53h-4.13l2.96-4.45c.31-.46.18-1.08-.28-1.39-.46-.31-1.08-.18-1.39.28l-4," +
                "6c-.2.31-.22.7-.05,1.03.17.33.51.53.88.53h4.13l-2.96,4.45c-.31.46-.18,1.08.28,1.39.17.11.36.17.55.17." +
                "32,0,.64-.16.83-.45l4-6c.2-.31.22-.7.05-1.03Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "battery-charging-icon";
    }
}
