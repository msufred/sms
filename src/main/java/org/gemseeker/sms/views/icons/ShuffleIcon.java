package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ShuffleIcon extends SVGIcon {

    public ShuffleIcon() {
        super(DEFAULT_SIZE * 0.95, DEFAULT_SIZE);
    }

    public ShuffleIcon(double size) {
        super(size * 0.95, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.923,2.618c-0.101-0.245-0.296-0.44-0.541-0.541C21.26,2.027,21.13,2,21,2h-5c-0.553,0-1," +
                "0.448-1,1s0.447,1,1,1h2.586 L3.293,19.293c-0.391,0.391-0.391,1.023,0,1.414C3.488,20.902,3.744,21," +
                "4,21s0.512-0.098,0.707-0.293L20,5.414V8 c0,0.552,0.447,1,1,1s1-0.448,1-1V3C22,2.87,21.974,2.74," +
                "21.923,2.618z M21,15c-0.553,0-1,0.447-1,1v2.586l-4.293-4.293c-0.391-0.391-1.023-0.391-1.414," +
                "0s-0.391,1.023,0,1.414L18.586,20H16 c-0.553,0-1,0.447-1,1s0.447,1,1,1h5c0.13,0,0.26-0.026," +
                "0.382-0.077c0.245-0.101,0.44-0.296,0.541-0.541 C21.974,21.26,22,21.13,22,21v-5C22,15.447," +
                "21.553,15,21,15z M8.293,9.707C8.488,9.902,8.744,10,9,10s0.512-0.098,0.707-0.293c0.391-0.391," +
                "0.391-1.023,0-1.414l-5-5c-0.391-0.391-1.023-0.391-1.414,0s-0.391,1.023,0,1.414L8.293,9.707z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "shuffle-icon";
    }
}
