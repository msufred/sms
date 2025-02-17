package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class VideoOffIcon extends SVGIcon {

    public VideoOffIcon() {
        super();
    }

    public VideoOffIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M23.455,6.109c-0.336-0.171-0.737-0.14-1.041,0.081l-5.31,3.84L17,9.926V7c0-1.654-" +
                "1.346-3-3-3h-3.34c-0.552,0-1,0.448-1,1 s0.448,1,1,1H14c0.552,0,1,0.449,1,1v3.34c0,0.265," +
                "0.105,0.52,0.293,0.707l1,1c0.347,0.348,0.896,0.39,1.293,0.103L22,8.958V17 c0,0.553,0.447," +
                "1,1,1s1-0.447,1-1V7C24,6.625,23.789,6.28,23.455,6.109z M16.708,15.294c-0.001-0.001-0.002-" +
                "0.002-0.002-0.003L5.707,4.293c0,0,0,0,0-0.001l-4-4c-0.391-0.391-1.023-0.391-1.414,0 s-" +
                "0.391,1.023,0,1.414l2.331,2.331C1.148,4.225,0,5.474,0,7v10c0,1.654,1.346,3,3,3h11c1.238," +
                "0,2.302-0.754,2.76-1.826l5.533,5.533 C22.488,23.902,22.744,24,23,24s0.512-0.098,0.707-" +
                "0.293c0.391-0.391,0.391-1.023,0-1.414L16.708,15.294z M15,17 c0,0.552-0.448,1-1,1H3c-" +
                "0.551,0-1-0.448-1-1V7c0-0.551,0.449-1,1-1h1.586L15,16.414V17z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "video-off-icon";
    }
}
