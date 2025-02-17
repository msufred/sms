package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class BarChartIcon extends SVGIcon {

    public BarChartIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public BarChartIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m7,6c-.55,0-1,.45-1,1v10c0,.55.45,1,1,1s1-.45,1-1V7c0-.55-.45-1-1-1Z " +
                "M13,0c-.55,0-1,.45-1,1v16c0,.55.45,1,1,1s1-.45,1-1V1c0-.55-.45-1-1-1Z " +
                "M1,12c-.55,0-1,.45-1,1v4c0,.55.45,1,1,1s1-.45,1-1v-4c0-.55-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "bar-chart-icon";
    }
}
