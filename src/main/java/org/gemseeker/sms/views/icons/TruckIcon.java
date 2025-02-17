package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TruckIcon extends SVGIcon {

    public TruckIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.9);
    }

    public TruckIcon(double size) {
        super(size, size * 0.9);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M23.707,10.293l-3-3C20.52,7.105,20.266,7,20,7h-3V3c0-0.552-0.447-1-1-1H1C0.448,2,0," +
                "2.448,0,3v13c0,0.553,0.448,1,1,1 h1.351C2.133,17.456,2,17.961,2,18.5C2,20.43,3.57,22,5.5,22S9," +
                "20.43,9,18.5c0-0.539-0.133-1.044-0.351-1.5h6.702 C15.133,17.456,15,17.961,15,18.5c0,1.93,1.57," +
                "3.5,3.5,3.5s3.5-1.57,3.5-3.5c0-0.539-0.133-1.044-0.351-1.5H23c0.553,0,1-0.447,1-1 v-5C24,10.735," +
                "23.895,10.48,23.707,10.293z M2,4h13v4v7H2V4z M7,18.5C7,19.327,6.327,20,5.5,20S4,19.327,4,18.5S4.673," +
                "17,5.5,17 S7,17.673,7,18.5z M20,18.5c0,0.827-0.673,1.5-1.5,1.5S17,19.327,17,18.5s0.673-1.5," +
                "1.5-1.5S20,17.673,20,18.5z M22,15h-5V9h2.586 L22,11.414V15z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "truck-icon";
    }
}
