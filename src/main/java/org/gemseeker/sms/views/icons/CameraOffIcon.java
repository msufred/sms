package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CameraOffIcon extends SVGIcon {

    public CameraOffIcon() {
        super();
    }

    public CameraOffIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m21.71,20.29h0S6.71,5.29,6.71,5.29h0S1.71.29,1.71.29C1.32-.1.68-.1.29.29S-.1,1.32.29,1.71l3.29" +
                ",3.29h-.59c-1.65,0-3,1.35-3,3v11c0,1.65,1.35,3,3,3h17.59l1.71,1.71c.2.2.45.29.71.29s.51-.1.71-.29c.39-." +
                "39.39-1.02,0-1.41l-2-2Zm-12.21-8.97c.06-.08.12-.15.19-.22l4.21,4.21c-.39.33-.85.56-1.37.66-.79.15-1.59-" +
                ".02-2.25-.47-.66-.45-1.11-1.14-1.25-1.92-.15-.79.02-1.59.47-2.25Zm-6.5,8.67c-.55,0-1-.45-1-1v-11c0-.55." +
                "45-1,1-1h2.59l2.69,2.69c-.15.16-.3.33-.42.51-.75,1.1-1.03,2.43-.79,3.75.25,1.31.99,2.45,2.09,3.21.84.58" +
                ",1.82.88,2.81.88.31,0,.62-.03.93-.09.92-.17,1.74-.6,2.41-1.21l3.27,3.27H3Z M21,5h-3.46l-1.7-2.55c-.19-." +
                "28-.5-.45-.83-.45h-6c-.55,0-1,.45-1,1s.45,1,1,1h5.46l1.7,2.55c.19.28.5.45.83.45h4c.55,0,1,.45,1,1v9.34c0" +
                ",.55.45,1,1,1s1-.45,1-1v-9.34c0-1.65-1.35-3-3-3Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "camera-off-icon";
    }
}
