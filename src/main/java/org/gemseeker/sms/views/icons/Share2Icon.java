package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class Share2Icon extends SVGIcon {

    public Share2Icon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public Share2Icon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M18,15c-1.108,0-2.111,0.453-2.836,1.183l-5.33-3.106C9.931,12.732,10,12.376,10,12c0-0.376-" +
                "0.069-0.733-0.166-1.078 l5.327-3.109C15.886,8.545,16.89,9,18,9c2.206,0,4-1.794,4-4s-1.794-4-4-4s-" +
                "4,1.794-4,4c0,0.376,0.069,0.733,0.166,1.078 L8.839,9.187C8.114,8.455,7.109,8,6,8c-2.206,0-4,1.794-" +
                "4,4c0,2.206,1.794,4,4,4c1.11,0,2.114-0.455,2.839-1.187l5.328,3.106 C14.069,18.265,14,18.623,14,19c0," +
                "2.206,1.794,4,4,4s4-1.794,4-4S20.206,15,18,15z M18,3c1.103,0,2,0.897,2,2s-0.897,2-2,2 s-2-0.897-2-" +
                "2S16.897,3,18,3z M6,14c-1.103,0-2-0.897-2-2c0-1.103,0.897-2,2-2s2,0.897,2,2C8,13.103,7.103,14,6,14z " +
                "M18,21 c-1.103,0-2-0.897-2-2c0-0.364,0.105-0.701,0.275-0.995c0.002-0.004,0.006-0.007,0.009-0.011c0." +
                "002-0.004,0.002-0.009,0.004-0.013 C16.638,17.396,17.271,17,18,17c1.103,0,2,0.897,2,2S19.103,21,18,21z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "share2-icon";
    }
}
