package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class SmileIcon extends SVGIcon {

    public SmileIcon() {
        super();
    }

    public SmileIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z " +
                "M12,21c-4.962,0-9-4.037-9-9 c0-4.962,4.038-9,9-9c4.963,0,9,4.038,9,9C21,16.963," +
                "16.963,21,12,21z M15.203,13.396C15.191,13.411,13.948,15,12,15c-1.935,0-3.173-" +
                "1.565-3.203-1.604C8.465,12.958,7.841,12.87,7.4,13.2 c-0.442,0.331-0.531,0.958-0.2," +
                "1.399C7.273,14.698,9.034,17,12,17s4.727-2.302,4.8-2.4c0.33-0.439,0.241-1.062-" +
                "0.197-1.394 C16.163,12.872,15.537,12.96,15.203,13.396z M9.01,10c0.552,0,1-0.448," +
                "1-1s-0.448-1-1-1H9C8.448,8,8.005,8.448,8.005,9S8.458,10,9.01,10z M15.01,10c0.553," +
                "0,1-0.448,1-1s-0.447-1-1-1H15c-0.553,0-0.995,0.448-0.995,1S14.457,10,15.01,10z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "smile-icon";
    }
}
