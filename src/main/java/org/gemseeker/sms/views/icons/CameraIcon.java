package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CameraIcon extends SVGIcon {

    public CameraIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public CameraIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m21,3h-3.46l-1.7-2.55c-.19-.28-.5-.45-.83-.45h-6c-.33,0-.65.17-.83.45l-1.7,2.55h-3.46c-1.65," +
                "0-3,1.35-3,3v11c0,1.65,1.35,3,3,3h18c1.65,0,3-1.35,3-3V6c0-1.65-1.35-3-3-3Zm1,14c0,.55-.45,1-1,1H3c-" +
                ".55,0-1-.45-1-1V6c0-.55.45-1,1-1h4c.33,0,.65-.17.83-.45l1.7-2.55h4.93l1.7,2.55c.19.28.5.45.83.45h4c." +
                "55,0,1,.45,1,1v11Z M12,6c-2.76,0-5,2.24-5,5s2.24,5,5,5,5-2.24,5-5-2.24-5-5-5Zm0,8c-1.65,0-3-1.35-3-" +
                "3s1.35-3,3-3,3,1.35,3,3-1.35,3-3,3Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "camera-icon";
    }
}
