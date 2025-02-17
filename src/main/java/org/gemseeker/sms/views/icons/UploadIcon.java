package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class UploadIcon extends SVGIcon {

    public UploadIcon() {
        super();
    }

    public UploadIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21,14c-0.553,0-1,0.447-1,1v4c0,0.552-0.448,1-1,1H5c-0.551,0-1-0.448-1-1v-4c0-0.553-" +
                "0.448-1-1-1s-1,0.447-1,1v4 c0,1.654,1.346,3,3,3h14c1.654,0,3-1.346,3-3v-4C22,14.447,21.553,14," +
                "21,14z M7.707,8.707L11,5.414V15c0,0.553,0.448,1,1,1c0.553,0,1-0.447,1-1V5.414l3.293,3.293C16.488," +
                "8.902,16.744,9,17,9 s0.512-0.098,0.707-0.293c0.391-0.391,0.391-1.023,0-1.414l-4.999-4.999c-" +
                "0.093-0.093-0.203-0.166-0.326-0.217 c-0.244-0.101-0.52-0.101-0.764,0c-0.122,0.051-0.233,0.124-" +
                "0.325,0.216l-5,5c-0.391,0.391-0.391,1.023,0,1.414 S7.316,9.098,7.707,8.707z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "upload-icon";
    }
}
