package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class EyeIcon extends SVGIcon {

    public EyeIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.75);
    }

    public EyeIcon(double size) {
        super(size, size * 0.75);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M23.895,11.553C23.72,11.204,19.531,3,12,3S0.28,11.204,0.105,11.553c-0.141," +
                "0.282-0.141,0.613,0,0.895 C0.28,12.797,4.469,21,12,21s11.72-8.203,11.895-8.553C24.035," +
                "12.166,24.035,11.834,23.895,11.553z M12,19 c-5.377,0-8.921-5.393-9.858-7.001C3.076," +
                "10.389,6.606,5,12,5c5.377,0,8.921,5.393,9.858,7.001C20.924,13.611,17.394,19,12,19z " +
                "M12,8c-2.206,0-4,1.794-4,4c0,2.206,1.794,4,4,4c2.206,0,4-1.794,4-4C16,9.794,14.206," +
                "8,12,8z M12,14c-1.103,0-2-0.897-2-2 c0-1.103,0.897-2,2-2c1.103,0,2,0.897,2,2C14," +
                "13.103,13.103,14,12,14z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "download-cloud-icon";
    }
}
