package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class XCircleIcon extends SVGIcon {

    public XCircleIcon() {
        super();
    }

    public XCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z " +
                "M12,21c-4.962,0-9-4.037-9-9 c0-4.962,4.038-9,9-9c4.963,0,9,4.038,9,9C21,16.963,16.963," +
                "21,12,21z M15.707,8.293c-0.391-0.391-1.023-0.391-1.414,0L12,10.586L9.707," +
                "8.293c-0.391-0.391-1.023-0.391-1.414,0 s-0.391,1.023,0,1.414L10.586,12l-2.293," +
                "2.293c-0.391,0.391-0.391,1.023,0,1.414C8.488,15.902,8.744,16,9,16 s0.512-0.098," +
                "0.707-0.293L12,13.414l2.293,2.293C14.488,15.902,14.744,16,15,16s0.512-0.098," +
                "0.707-0.293 c0.391-0.391,0.391-1.023,0-1.414L13.414,12l2.293-2.293C16.098," +
                "9.316,16.098,8.684,15.707,8.293z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "x-circle-icon";
    }
}
