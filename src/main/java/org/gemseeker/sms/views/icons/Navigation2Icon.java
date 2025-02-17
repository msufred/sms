package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class Navigation2Icon extends SVGIcon {

    public Navigation2Icon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public Navigation2Icon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19.938,20.654l-7-19C12.794,1.261,12.419,1,12,1s-0.793,0.261-0.938,0.654l-7,19c-0.145," +
                "0.395-0.028,0.838,0.292,1.109 s0.777,0.314,1.142,0.104L12,18.151l6.504,3.717C18.658,21.957,18.829," +
                "22,19,22c0.231,0,0.461-0.08,0.646-0.236 C19.967,21.492,20.084,21.049,19.938,20.654z M12.496," +
                "16.132C12.342,16.044,12.171,16,12,16s-0.342,0.044-0.496,0.132L6.887,18.77 L12,4.893l5.112,13.877L12.496,16.132z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "navigation2-icon";
    }
}
