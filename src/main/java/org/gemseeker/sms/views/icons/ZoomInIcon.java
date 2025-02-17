package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ZoomInIcon extends SVGIcon {

    public ZoomInIcon() {
        super();
    }

    public ZoomInIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.707,20.293l-3.682-3.682C19.258,15.071,20,13.122,20,11c0-4.962-4.037-9-9-9c-4.962," +
                "0-9,4.038-9,9c0,4.963,4.038,9,9,9 c2.122,0,4.071-0.742,5.611-1.975l3.682,3.682C20.488,21.902," +
                "20.744,22,21,22s0.512-0.098,0.707-0.293 C22.098,21.316,22.098,20.684,21.707,20.293z " +
                "M4,11c0-3.86,3.14-7,7-7c3.859,0,7,3.14,7,7c0,1.922-0.779,3.665-2.038,4.931 c-0.006,0.005-0.013," +
                "0.007-0.019,0.013s-0.007,0.013-0.012,0.019C14.665,17.221,12.922,18,11,18C7.14,18,4,14.859,4,11z " +
                "M14,10h-2V8c0-0.552-0.448-1-1-1s-1,0.448-1,1v2H8c-0.552,0-1,0.448-1,1s0.448,1,1,1h2v2c0,0.553," +
                "0.448,1,1,1s1-0.447,1-1 v-2h2c0.553,0,1-0.448,1-1S14.553,10,14,10z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "zoom-in-icon";
    }
}
