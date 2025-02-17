package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TrendingUpIcon extends SVGIcon {

    public TrendingUpIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.65);
    }

    public TrendingUpIcon(double size) {
        super(size, size * 0.65);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M23.923,5.618c-0.101-0.245-0.296-0.44-0.541-0.541C23.26,5.027,23.13,5,23,5h-6c-0.553," +
                "0-1,0.448-1,1s0.447,1,1,1h3.586 L13.5,14.086L9.207,9.793c-0.391-0.391-1.023-0.391-1.414,0l-7.5," +
                "7.5c-0.391,0.391-0.391,1.023,0,1.414C0.488,18.902,0.744,19,1,19 s0.512-0.098,0.707-0.293L8.5," +
                "11.914l4.293,4.293c0.391,0.391,1.023,0.391,1.414,0L22,8.414V12c0,0.553,0.447,1,1,1s1-0.447," +
                "1-1V6 C24,5.87,23.974,5.74,23.923,5.618z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "trending-up-icon";
    }
}
