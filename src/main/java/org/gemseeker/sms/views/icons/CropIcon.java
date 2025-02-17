package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CropIcon extends SVGIcon {

    public CropIcon() {
        super();
    }

    public CropIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M23,17h-4V8c0-1.654-1.346-3-3.009-3L7.095,5.077L7.13,1.009C7.135,0.457,6.691,0.005,6.139,0 " +
                "C5.566-0.039,5.135,0.439,5.13,0.991L5.094,5.094L0.991,5.13C0.439,5.135-0.005,5.586,0,6.139S0.421,7." +
                "128,1.009,7.13l4.068-0.035 L5,16c0,1.654,1.346,3,3,3h9v4c0,0.553,0.447,1,1,1s1-0.447,1-1v-4h4c0.553," +
                "0,1-0.447,1-1S23.553,17,23,17z M8,17 c-0.551,0-1-0.448-1-0.991l0.077-8.931L16,7c0.552,0,1,0.449,1,1v9H8z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "crop-icon";
    }
}
