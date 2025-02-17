package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FramerIcon extends SVGIcon {

    public FramerIcon() {
        super(DEFAULT_SIZE * 0.7, DEFAULT_SIZE);
    }

    public FramerIcon(double size) {
        super(size * 0.7, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,9V2c0-0.552-0.447-1-1-1H5C4.596,1,4.231,1.244,4.076,1.617c-0.155,0.374-0.069," +
                "0.804,0.217,1.09L9.586,8H5 C4.448,8,4,8.448,4,9v7c0,0.001,0,0.002,0,0.003c0,0.134,0.027," +
                "0.262,0.075,0.378c0.049,0.119,0.122,0.229,0.218,0.326l7,7 C11.484,23.898,11.74,24,12," +
                "24c0.129,0,0.259-0.024,0.383-0.076C12.757,23.77,13,23.404,13,23v-6h6c0.404,0,0.77-0.243," +
                "0.924-0.617 c0.155-0.374,0.069-0.804-0.217-1.09L14.414,10H19C19.553,10,20,9.552,20,9z " +
                "M11,20.586L7.414,17H11V20.586z M16.586,15H12H6v-5 h5.586L16.586,15z M18,8h-5.586l-5-5H18V8z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "framer-icon";
    }
}
