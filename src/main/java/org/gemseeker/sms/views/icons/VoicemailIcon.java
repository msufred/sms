package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class VoicemailIcon extends SVGIcon {

    public VoicemailIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.5);
    }

    public VoicemailIcon(double size) {
        super(size, size * 0.5);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M18.5,6C15.468,6,13,8.467,13,11.5c0,1.328,0.474,2.548,1.261,3.5H9.739C10.526,14.048,11," +
                "12.828,11,11.5 C11,8.467,8.533,6,5.5,6S0,8.467,0,11.5C0,14.532,2.467,17,5.5,17h13c3.032,0,5.5-2.468," +
                "5.5-5.5C24,8.467,21.532,6,18.5,6z M2,11.5 C2,9.57,3.57,8,5.5,8S9,9.57,9,11.5S7.43,15,5.5,15S2," +
                "13.43,2,11.5z M18.5,15c-1.93,0-3.5-1.57-3.5-3.5S16.57,8,18.5,8 S22,9.57,22,11.5S20.43,15,18.5,15z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "voicemail-icon";
    }
}
