package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class DownloadIcon extends SVGIcon {

    public DownloadIcon() {
        super();
    }

    public DownloadIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21,14c-0.553,0-1,0.447-1,1v4c0,0.552-0.448,1-1,1H5c-0.551,0-1-0.448-1-1v-4c0-0" +
                ".553-0.448-1-1-1s-1,0.447-1,1v4 c0,1.654,1.346,3,3,3h14c1.654,0,3-1.346,3-3v-4C22,14.44" +
                "7,21.553,14,21,14z M11.292,15.706c0.093,0.093,0.203,0.166,0.326,0.217C11.74,15.973,11.8" +
                "7,16,12,16s0.26-0.026,0.382-0.077 c0.123-0.051,0.234-0.124,0.326-0.217l4.999-4.999c0.39" +
                "1-0.391,0.391-1.023,0-1.414s-1.023-0.391-1.414,0L13,12.586V3 c0-0.552-0.447-1-1-1c-0.55" +
                "2,0-1,0.448-1,1v9.586L7.707,9.293c-0.391-0.391-1.023-0.391-1.414,0s-0.391,1.023,0,1.414 " +
                "L11.292,15.706z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "download-icon";
    }
}
