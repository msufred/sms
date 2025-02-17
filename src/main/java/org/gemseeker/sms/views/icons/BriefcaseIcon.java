package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class BriefcaseIcon extends SVGIcon {

    public BriefcaseIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public BriefcaseIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m19,4h-3v-1c0-1.65-1.35-3-3-3h-4c-1.65,0-3,1.35-3,3v1h-3c-1.65,0-3,1.35-3,3v10c0,1.65,1.35,3," +
                "3,3h16c1.65,0,3-1.35,3-3V7c0-1.65-1.35-3-3-3Zm-11-1c0-.55.45-1,1-1h4c.55,0,1,.45,1,1v1h-6v-1Zm6,3v12h" +
                "-6V6h6ZM2,17V7c0-.55.45-1,1-1h3v12h-3c-.55,0-1-.45-1-1Zm18,0c0,.55-.45,1-1,1h-3V6h3c.55,0,1,.45,1,1v10Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "briefcase-icon";
    }
}
