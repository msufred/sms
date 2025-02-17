package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class AnchorIcon extends SVGIcon {

    public AnchorIcon() {
        super();
    }

    public AnchorIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m21,10h-3c-.55,0-1,.45-1,1s.45,1,1,1h1.94c-.46,4.17-3.78,7.48-7.94,7.94V7.86c1.72-.45,3-2,3-3" +
                ".86,0-2.21-1.79-4-4-4s-4,1.79-4,4c0,1.86,1.28,3.41,3,3.86v12.08c-4.16-.46-7.48-3.77-7.94-7.94h1.94c.55" +
                ",0,1-.45,1-1s-.45-1-1-1H1c-.55,0-1,.45-1,1,0,6.07,4.93,11,11,11s11-4.93,11-11c0-.55-.45-1-1-1Zm-12-6c0" +
                "-1.1.9-2,2-2s2,.9,2,2-.9,2-2,2-2-.9-2-2Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "anchor-icon";
    }
}
