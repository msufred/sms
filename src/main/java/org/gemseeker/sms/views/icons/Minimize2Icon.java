package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class Minimize2Icon extends SVGIcon {

    public Minimize2Icon() {
        super();
    }

    public Minimize2Icon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M10.382,13.077C10.26,13.026,10.13,13,10,13H4c-0.552,0-1,0.447-1,1s0.448,1,1,1h3.586l-5.293,5.293 " +
                "c-0.391,0.391-0.391,1.023,0,1.414C2.488,21.902,2.744,22,3,22s0.512-0.098,0.707-0.293L9,16.414V20c0,0.553," +
                "0.448,1,1,1 s1-0.447,1-1v-6c0-0.13-0.027-0.26-0.077-0.382C10.822,13.373,10.627,13.178,10.382,13.077z " +
                "M21.707,2.293c-0.391-0.391-1.023-0.391-1.414,0L15,7.586V4c0-0.552-0.447-1-1-1s-1,0.448-1,1v6 c0,0.13," +
                "0.027,0.26,0.077,0.382c0.101,0.245,0.296,0.44,0.541,0.541C13.74,10.973,13.87,11,14,11h6c0.553,0,1-0.448,1-1 " +
                "s-0.447-1-1-1h-3.586l5.293-5.293C22.098,3.316,22.098,2.684,21.707,2.293z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "minimize2-icon";
    }
}
