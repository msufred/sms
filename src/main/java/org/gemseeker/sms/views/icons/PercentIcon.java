package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class PercentIcon extends SVGIcon {

    public PercentIcon() {
        super();
    }

    public PercentIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19.707,4.293c-0.391-0.391-1.023-0.391-1.414,0l-14,14c-0.391,0.391-0.391,1.023,0,1.414C4.488," +
                "19.902,4.744,20,5,20 s0.512-0.098,0.707-0.293l14-14C20.098,5.316,20.098,4.684,19.707,4.293z " +
                "M6.5,10C8.43,10,10,8.43,10,6.5S8.43,3,6.5,3S3,4.57,3,6.5S4.57,10,6.5,10z M6.5,5C7.327,5,8,5.673," +
                "8,6.5S7.327,8,6.5,8 S5,7.327,5,6.5S5.673,5,6.5,5z M17.5,14c-1.93,0-3.5,1.57-3.5,3.5s1.57,3.5,3.5," +
                "3.5s3.5-1.57,3.5-3.5S19.43,14,17.5,14z M17.5,19 c-0.827,0-1.5-0.673-1.5-1.5s0.673-1.5,1.5-1.5s1.5," +
                "0.673,1.5,1.5S18.327,19,17.5,19z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "percent-icon";
    }
}
