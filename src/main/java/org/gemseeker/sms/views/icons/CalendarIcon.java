package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CalendarIcon extends SVGIcon {

    public CalendarIcon() {
        super(DEFAULT_SIZE * 0.95, DEFAULT_SIZE);
    }

    public CalendarIcon(double size) {
        super(size * 0.95, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m17,2h-2v-1c0-.55-.45-1-1-1s-1,.45-1,1v1h-6v-1c0-.55-.45-1-1-1s-1,.45-1,1v1h-2c-1.65,0-3,1." +
                "35-3,3v14c0,1.65,1.35,3,3,3h14c1.65,0,3-1.35,3-3V5c0-1.65-1.35-3-3-3ZM3,4h2v1c0,.55.45,1,1,1s1-.45," +
                "1-1v-1h6v1c0,.55.45,1,1,1s1-.45,1-1v-1h2c.55,0,1,.45,1,1v3H2v-3c0-.55.45-1,1-1Zm14,16H3c-.55,0-1-." +
                "45-1-1v-9h16v9c0,.55-.45,1-1,1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "calendar-icon";
    }
}
