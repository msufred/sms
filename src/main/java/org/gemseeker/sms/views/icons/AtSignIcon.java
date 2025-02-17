package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class AtSignIcon extends SVGIcon {

    public AtSignIcon() {
        super();
    }

    public AtSignIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m11,0c-2.94,0-5.7,1.14-7.78,3.22C1.14,5.3,0,8.06,0,11c0,6.06,4.94,11,11,11,2.4,0,4.78-.8," +
                "6.69-2.27.44-.34.52-.96.19-1.4-.33-.44-.96-.52-1.4-.19-1.56,1.2-3.51,1.85-5.47,1.85-4.96,0-9-4.04-9" +
                "-9,0-2.4.94-4.66,2.64-6.36,1.7-1.7,3.96-2.64,6.36-2.64,4.96,0,9,4.04,9,9v1c0,1.1-.9,2-2,2s-2-.9-2" +
                "-2v-5c0-.55-.45-1-1-1s-1,.45-1,1v.03c-.84-.63-1.87-1.03-3-1.03-2.76,0-5,2.24-5,5s2.24,5,5,5c1.49," +
                "0,2.82-.67,3.73-1.71.72,1.03,1.92,1.71,3.27,1.71,2.21,0,4-1.79,4-4v-1C22,4.93,17.07,0,11,0Zm0,14c" +
                "-1.65,0-3-1.35-3-3s1.35-3,3-3,3,1.35,3,3-1.35,3-3,3Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "at-sign-icon";
    }
}
