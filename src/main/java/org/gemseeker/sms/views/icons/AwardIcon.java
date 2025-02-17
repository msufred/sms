package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class AwardIcon extends SVGIcon {

    public AwardIcon() {
        super(DEFAULT_SIZE * 0.65, DEFAULT_SIZE);
    }

    public AwardIcon(double size) {
        super(size * 0.65, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m16,8C16,3.59,12.41,0,8,0S0,3.59,0,8c0,2.58,1.24,4.88,3.14,6.34l-1.13,8.53c-.05.38.12.76.44.97" +
                ".32.21.73.22,1.06.02l4.49-2.69,4.49,2.69c.16.1.34.14.51.14.19,0,.38-.05.55-.16.32-.21.49-.59.44-.97l-1." +
                "13-8.53c1.9-1.46,3.14-3.76,3.14-6.34Zm-14,0c0-3.31,2.69-6,6-6s6,2.69,6,6-2.69,6-6,6-6-2.69-6-6Zm9.74," +
                "13.08l-3.22-1.93c-.32-.19-.71-.19-1.03,0l-3.22,1.93.75-5.66c.92.37,1.93.58,2.98.58s2.06-.21,2.99-.58l." +
                "75,5.66Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "award-icon";
    }
}
