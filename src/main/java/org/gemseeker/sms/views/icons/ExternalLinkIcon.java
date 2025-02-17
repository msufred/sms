package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ExternalLinkIcon extends SVGIcon {

    public ExternalLinkIcon() {
        super();
    }

    public ExternalLinkIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M18,12c-0.553,0-1,0.448-1,1v6c0,0.552-0.448,1-1,1H5c-0.551,0-1-0.448-1-1V8c0-0.551," +
                "0.449-1,1-1h6c0.552,0,1-0.448,1-1 s-0.448-1-1-1H5C3.346,5,2,6.346,2,8v11c0,1.654,1.346,3,3," +
                "3h11c1.654,0,3-1.346,3-3v-6C19,12.448,18.553,12,18,12z M21.923,2.618c-0.101-0.245-0.296-0.44-" +
                "0.541-0.541C21.26,2.027,21.13,2,21,2h-6c-0.553,0-1,0.448-1,1s0.447,1,1,1h3.586 " +
                "l-9.293,9.293c-0.391,0.391-0.391,1.023,0,1.414C9.488,14.902,9.744,15,10,15s0.512-0.098," +
                "0.707-0.293L20,5.414V9 c0,0.552,0.447,1,1,1s1-0.448,1-1V3C22,2.87,21.974,2.74,21.923,2.618z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "external-link-icon";
    }
}
