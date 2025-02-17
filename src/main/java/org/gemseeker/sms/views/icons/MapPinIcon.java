package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MapPinIcon extends SVGIcon {

    public MapPinIcon() {
        super(DEFAULT_SIZE * 0.8, DEFAULT_SIZE);
    }

    public MapPinIcon(double size) {
        super(size * 0.8, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,0C6.486,0,2,4.486,2,10c0,7.449,9.06,13.575,9.445,13.832C11.613,23.944,11.807,24,12," +
                "24s0.387-0.056,0.555-0.168 C12.94,23.575,22,17.449,22,10C22,4.486,17.514,0,12,0z M12,21.771C10.103," +
                "20.363,4,15.413,4,10c0-4.411,3.589-8,8-8s8,3.589,8,8 C20,15.413,13.897,20.363,12,21.771z " +
                "M12,6c-2.206,0-4,1.794-4,4s1.794,4,4,4c2.206,0,4-1.794,4-4S14.206,6,12,6z M12,12c-1.103," +
                "0-2-0.897-2-2s0.897-2,2-2 c1.103,0,2,0.897,2,2S13.103,12,12,12z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "map-pin-icon";
    }
}
