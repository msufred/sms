package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FrownIcon extends SVGIcon {

    public FrownIcon() {
        super();
    }

    public FrownIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z " +
                "M12,21c-4.962,0-9-4.037-9-9 c0-4.962,4.038-9,9-9c4.963,0,9,4.038,9,9C21,16.963,16.963,21,12,21z " +
                "M12,13c-2.966,0-4.727,2.302-4.8,2.4c-0.33,0.439-0.241,1.062,0.197,1.394c0.438,0.333,1.064,0.247,1.399-0.189 " +
                "C8.809,16.589,10.052,15,12,15c1.935,0,3.173,1.565,3.203,1.604C15.399,16.863,15.698,17,16.001,17c0.209," +
                "0,0.419-0.065,0.599-0.2 c0.442-0.331,0.532-0.958,0.2-1.399C16.727,15.302,14.966,13,12,13z " +
                "M9.01,10c0.552,0,1-0.448,1-1s-0.448-1-1-1H9C8.448,8,8.005,8.448,8.005,9S8.458,10,9.01,10z " +
                "M15.01,10c0.553,0,1-0.448,1-1s-0.447-1-1-1H15c-0.553,0-0.995,0.448-0.995,1S14.457,10,15.01,10z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "frown-icon";
    }
}
