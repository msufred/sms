package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ClipboardIcon extends SVGIcon {

    public ClipboardIcon() {
        super(DEFAULT_SIZE * 0.75, DEFAULT_SIZE);
    }

    public ClipboardIcon(double size) {
        super(size * 0.75, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m15,2h-1c0-1.1-.9-2-2-2h-6c-1.1,0-2,.9-2,2h-1c-1.65,0-3,1.35-3,3v14c0,1.65,1.35,3,3,3h12c1.65," +
                "0,3-1.35,3-3V5c0-1.65-1.35-3-3-3Zm-9,0h6v2h-6v-1s0,0,0,0,0,0,0,0v-1s0,0,0,0Zm10,17c0,.55-.45,1-1,1H3c-" +
                ".55,0-1-.45-1-1V5c0-.55.45-1,1-1h1c0,1.1.9,2,2,2h6c1.1,0,2-.9,2-2h1c.55,0,1,.45,1,1v14Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "clipboard-icon";
    }
}
