package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class Edit2Icon extends SVGIcon {

    public Edit2Icon() {
        super();
    }

    public Edit2Icon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.707,2.293c-1.445-1.446-3.969-1.446-5.414,0l-13.5,13.5c-0.123,0.123-0.212,0.276-0.258," +
                "0.444l-1.5,5.5 c-0.094,0.346,0.004,0.716,0.258,0.97C1.483,22.897,1.738,23,2,23c0.088,0,0.176-0.012," +
                "0.263-0.035l5.5-1.5 c0.168-0.046,0.321-0.135,0.444-0.258l13.5-13.5C22.43,6.984,22.828,6.022,22.828," +
                "5S22.43,3.016,21.707,2.293z M20.293,6.293 L6.98,19.605l-3.555,0.97l0.97-3.556L17.707,3.707c0.691-0.69," +
                "1.895-0.69,2.586,0C20.639,4.052,20.828,4.512,20.828,5 S20.639,5.948,20.293,6.293z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "edit2-icon";
    }
}
