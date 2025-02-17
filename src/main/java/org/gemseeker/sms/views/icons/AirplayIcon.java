package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class AirplayIcon extends SVGIcon {

    public AirplayIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public AirplayIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m19,0H3C1.35,0,0,1.35,0,3v10c0,1.65,1.35,3,3,3h1c.55,0,1-.45,1-1s-.45-1-1-1h-1c-.55,0-1-." +
                "45-1-1V3c0-.55.45-1,1-1h16c.55,0,1,.45,1,1v10c0,.55-.45,1-1,1h-1c-.55,0-1,.45-1,1s.45,1,1,1h1c1.65," +
                "0,3-1.35,3-3V3c0-1.65-1.35-3-3-3Z M11.77,12.36c-.38-.46-1.16-.46-1.54,0l-5,6c-.25.3-.3.71-.14,1.06.16" +
                ".35.52.58.91.58h10c.39,0,.74-.22.91-.58.17-.35.11-.77-.14-1.06l-5-6Zm-3.63,5.64l2.86-3.44,2.87,3.44h-5" +
                ".73Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "airplay-icon";
    }
}
