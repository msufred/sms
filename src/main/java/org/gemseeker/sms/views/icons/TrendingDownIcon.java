package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TrendingDownIcon extends SVGIcon {

    public TrendingDownIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.65);
    }

    public TrendingDownIcon(double size) {
        super(size, size * 0.65);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M24,12c0-0.552-0.447-1-1-1s-1,0.448-1,1v3.586l-7.793-7.793c-0.391-0.391-1.023-0.391-1.414," +
                "0L8.5,12.086L1.707,5.293 c-0.391-0.391-1.023-0.391-1.414,0s-0.391,1.023,0,1.414l7.5,7.5c0.391,0.391," +
                "1.023,0.391,1.414,0L13.5,9.914L20.586,17H17 c-0.553,0-1,0.447-1,1s0.447,1,1,1h6c0.13,0,0.26-0.026," +
                "0.382-0.077c0.245-0.101,0.44-0.296,0.541-0.541 C23.974,18.26,24,18.13,24,18V12z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "trending-down-icon";
    }
}
