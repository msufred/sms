package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class BoldIcon extends SVGIcon {

    public BoldIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public BoldIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m12.4,8.64c.98-.91,1.6-2.2,1.6-3.64,0-2.76-2.24-5-5-5H1C.45,0,0,.45,0,1v16c0,.55.45,1,1,1h9c2." +
                "76,0,5-2.24,5-5,0-1.88-1.06-3.5-2.6-4.36ZM2,2h7c1.65,0,3,1.35,3,3s-1.35,3-3,3H2V2Zm8,14H2v-6h8c1.65,0," +
                "3,1.35,3,3s-1.35,3-3,3Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "bold-icon";
    }
}
