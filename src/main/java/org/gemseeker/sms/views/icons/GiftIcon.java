package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class GiftIcon extends SVGIcon {

    public GiftIcon() {
        super();
    }

    public GiftIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22,6h-2.351C19.867,5.544,20,5.039,20,4.5C20,2.57,18.43,1,16.5,1c-2.299,0-3.691,1.612-4.5,3.138 " +
                "C11.192,2.612,9.799,1,7.5,1C5.57,1,4,2.57,4,4.5C4,5.039,4.133,5.544,4.351,6H2C1.448,6,1,6.448,1,7v5c0," +
                "0.553,0.448,1,1,1h1v9 c0,0.553,0.448,1,1,1h16c0.553,0,1-0.447,1-1v-9h1c0.553,0,1-0.447,1-1V7C23,6.448," +
                "22.553,6,22,6z M16.5,3C17.327,3,18,3.673,18,4.5 S17.327,6,16.5,6h-3.154C13.816,4.759,14.787,3,16.5,3z " +
                "M6,4.5C6,3.673,6.673,3,7.5,3c1.713,0,2.683,1.759,3.154,3H7.5 C6.673,6,6,5.327,6,4.5z M3,8h4.5H11v3H3V8z " +
                "M5,13h6v8H5V13z M19,21h-6v-8h6V21z M21,11h-8V8h3.5H21V11z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "gift-icon";
    }
}
