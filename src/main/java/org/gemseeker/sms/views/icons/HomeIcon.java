package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class HomeIcon extends SVGIcon {

    public HomeIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public HomeIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.614,8.21l-9-7c-0.361-0.28-0.868-0.28-1.228,0l-9,7C2.143,8.4,2,8.691,2,9v11c0,1.654,1.346," +
                "3,3,3h14 c1.654,0,3-1.346,3-3V9C22,8.691,21.857,8.4,21.614,8.21z M14,21h-4v-8h4V21z M20,20c0," +
                "0.552-0.448,1-1,1h-3v-9c0-0.552-0.447-1-1-1 H9c-0.552,0-1,0.448-1,1v9H5c-0.551,0-1-0.448-1-1V9.489l8-6.222l8," +
                "6.222V20z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "home-icon";
    }
}
