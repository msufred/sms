package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FlagIcon extends SVGIcon {

    public FlagIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public FlagIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20.402,2.103c-0.358-0.163-0.788-0.09-1.084,0.166C19.31,2.276,18.444,3,16,3c-1.308," +
                "0-2.435-0.451-3.628-0.929 C11.054,1.545,9.692,1,8,1C4.75,1,3.509,2.077,3.293,2.293C3.105," +
                "2.48,3,2.735,3,3v12v7c0,0.553,0.448,1,1,1s1-0.447,1-1v-6.457 C5.434,15.326,6.352,15,8," +
                "15c1.308,0,2.435,0.451,3.628,0.929C12.945,16.455,14.308,17,16,17c3.25,0,4.491-1.077," +
                "4.707-1.293 C20.895,15.52,21,15.266,21,15V3C21,2.609,20.758,2.266,20.402,2.103z M19," +
                "14.46C18.562,14.677,17.633,15,16,15 c-1.308,0-2.435-0.451-3.628-0.929C11.054,13.545," +
                "9.692,13,8,13c-1.301,0-2.28,0.173-3,0.394V3.54C5.439,3.323,6.367,3,8,3 c1.308,0,2.435," +
                "0.451,3.628,0.929C12.945,4.455,14.308,5,16,5c1.301,0,2.279-0.172,3-0.394V14.46z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "flag-icon";
    }
}
