package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class Maximize2Icon extends SVGIcon {

    public Maximize2Icon() {
        super();
    }

    public Maximize2Icon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.923,2.618c-0.101-0.245-0.296-0.44-0.541-0.541C21.26,2.027,21.13,2,21,2h-6c-0.553,0-1," +
                "0.448-1,1s0.447,1,1,1h3.586 l-5.293,5.293c-0.391,0.391-0.391,1.023,0,1.414C13.488,10.902,13.744," +
                "11,14,11s0.512-0.098,0.707-0.293L20,5.414V9 c0,0.552,0.447,1,1,1s1-0.448,1-1V3C22,2.87,21.974," +
                "2.74,21.923,2.618z M9.293,13.293L4,18.586V15c0-0.553-0.448-1-1-1s-1,0.447-1,1v6c0,0.13,0.027," +
                "0.26,0.077,0.382 c0.101,0.245,0.296,0.44,0.541,0.541C2.74,21.973,2.87,22,3,22h6c0.552,0,1-0.447," +
                "1-1s-0.448-1-1-1H5.414l5.293-5.293 c0.391-0.391,0.391-1.023,0-1.414S9.684,12.902,9.293,13.293z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "maximize2-icon";
    }
}
