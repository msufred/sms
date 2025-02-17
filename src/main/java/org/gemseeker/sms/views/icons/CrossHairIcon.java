package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CrossHairIcon extends SVGIcon {

    public CrossHairIcon() {
        super();
    }

    public CrossHairIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z M13,20.941V18c0-0." +
                "553-0.447-1-1-1 c-0.552,0-1,0.447-1,1v2.941C6.836,20.478,3.522,17.165,3.059,13H6c0.552,0,1-0.447,1" +
                "-1c0-0.552-0.448-1-1-1H3.059 C3.522,6.836,6.836,3.522,11,3.059V6c0,0.552,0.448,1,1,1c0.553,0,1-0.4" +
                "48,1-1V3.059c4.165,0.463,7.478,3.777,7.941,7.941H18 c-0.553,0-1,0.448-1,1c0,0.553,0.447,1,1,1h2.94" +
                "1C20.478,17.165,17.165,20.478,13,20.941z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "cross-hair-icon";
    }
}
