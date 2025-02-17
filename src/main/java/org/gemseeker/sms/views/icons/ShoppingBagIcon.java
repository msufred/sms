package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ShoppingBagIcon extends SVGIcon {

    public ShoppingBagIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public ShoppingBagIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.928,5.646c-0.015-0.039-0.038-0.073-0.057-0.109C21.847,5.491,21.831,5.441,21.8," +
                "5.4l-3-4C18.611,1.148,18.314,1,18,1 H6C5.685,1,5.389,1.148,5.2,1.4l-3,4C2.169,5.442,2.153," +
                "5.491,2.128,5.537c-0.02,0.036-0.042,0.069-0.057,0.108 C2.028,5.759,2,5.877,2,6v14c0,1.654," +
                "1.346,3,3,3h14c1.654,0,3-1.346,3-3V6C22,5.877,21.972,5.759,21.928,5.646z M6.5,3h11L19,5H5 " +
                "L6.5,3z M19,21H5c-0.551,0-1-0.448-1-1V7h16v13C20,20.552,19.552,21,19,21z M16,9c-0.553,0-1," +
                "0.448-1,1c0,1.654-1.346,3-3,3s-3-1.346-3-3c0-0.552-0.448-1-1-1s-1,0.448-1,1c0,2.757,2.243," +
                "5,5,5 s5-2.243,5-5C17,9.448,16.553,9,16,9z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "shopping-bag-icon";
    }
}
