package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class PowerIcon extends SVGIcon {

    public PowerIcon() {
        super(DEFAULT_SIZE * 0.95, DEFAULT_SIZE);
    }

    public PowerIcon(double size) {
        super(size * 0.95, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19.067,5.933c-0.391-0.391-1.023-0.391-1.414,0c-0.391,0.39-0.391,1.023,0,1.414c3.118," +
                "3.119,3.117,8.195-0.002,11.314 c-3.119,3.118-8.195,3.117-11.314-0.002c-3.118-3.119-3.118-8.193," +
                "0-11.312c0.391-0.391,0.391-1.024,0-1.414 c-0.391-0.391-1.023-0.391-1.414,0c-3.897,3.898-3.897," +
                "10.242,0,14.14c1.95,1.95,4.511,2.925,7.072,2.925 c2.561,0,5.121-0.975,7.07-2.923C22.964,16.177," +
                "22.965,9.833,19.067,5.933z M12,13c0.553,0,1-0.447,1-1V2c0-0.552-0.447-1-1-1c-0.552,0-1,0.448-1," +
                "1v10C11,12.553,11.448,13,12,13z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "power-icon";
    }
}
