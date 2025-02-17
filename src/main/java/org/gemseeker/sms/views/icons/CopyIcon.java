package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CopyIcon extends SVGIcon {

    public CopyIcon() {
        super();
    }

    public CopyIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m19,7h-9c-1.65,0-3,1.35-3,3v9c0,1.65,1.35,3,3,3h9c1.65,0,3-1.35,3-3v-9c0-1.65-1.35-3-3-3Zm1," +
                "12c0,.55-.45,1-1,1h-9c-.55,0-1-.45-1-1v-9c0-.55.45-1,1-1h9c.55,0,1,.45,1,1v9Z M4,13h-1c-.55,0-1-." +
                "45-1-1V3c0-.55.45-1,1-1h9c.55,0,1,.45,1,1v1c0,.55.45,1,1,1s1-.45,1-1v-1c0-1.65-1.35-3-3-3H3C1.35,0," +
                "0,1.35,0,3v9c0,1.65,1.35,3,3,3h1c.55,0,1-.45,1-1s-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "copy-icon";
    }
}
