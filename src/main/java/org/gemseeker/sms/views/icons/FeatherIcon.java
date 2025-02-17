package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FeatherIcon extends SVGIcon {

    public FeatherIcon() {
        super();
    }

    public FeatherIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20.947,3.043c-1.323-1.323-3.082-2.051-4.952-2.051s-3.629,0.729-4.952,2.051l-6.75,6.75C4.105," +
                "9.98,4,10.235,4,10.5v8.086 l-2.707,2.707c-0.391,0.391-0.391,1.023,0,1.414C1.488,22.902,1.744,23,2," +
                "23s0.512-0.098,0.707-0.293L5.414,20H13.5 c0.266,0,0.521-0.105,0.708-0.294l3.962-3.974c0.022-0.02," +
                "0.042-0.04,0.062-0.062l2.715-2.723 C23.677,10.216,23.677,5.773,20.947,3.043z M13.085,18H7.414l2-" +
                "2h5.665L13.085,18z M17.073,14h-5.659l5.293-5.293 c0.391-0.391,0.391-1.023,0-1.414s-1.023-0.391-" +
                "1.414,0l-6.999,6.999c-0.001,0.001-0.001,0.001-0.002,0.002L6,16.586v-5.672 l6.457-6.457c0.945-0.945," +
                "2.202-1.465,3.538-1.465s2.593,0.521,3.538,1.465c1.95,1.951,1.95,5.125-0.001,7.077L17.073,14z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "feather-icon";
    }
}
