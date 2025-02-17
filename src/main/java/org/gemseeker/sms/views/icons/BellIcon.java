package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class BellIcon extends SVGIcon {

    public BellIcon() {
        super();
    }

    public BellIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m19.57,15.18s-2.57-1.91-2.57-8.18c0-3.86-3.14-7-7-7S3,3.14,3,7C3,13.27.46,15.16.45,15.17c-.37" +
                ".24-.53.7-.4,1.12.13.42.52.71.96.71h18c.43,0,.81-.29.95-.7s-.02-.87-.38-1.12Zm-16.43-.18c.91-1.48,1." +
                "86-4,1.86-8,0-2.76,2.24-5,5-5s5,2.24,5,5c0,4,.96,6.52,1.86,8H3.14Z M12.23,19.14c-.48-.28-1.09-.12-1." +
                "37.36-.28.48-.89.64-1.37.36-.15-.09-.28-.21-.36-.36-.28-.48-.89-.64-1.37-.36-.48.28-.64.89-.36,1.37." +
                "26.45.64.83,1.09,1.09.47.27.99.4,1.5.4,1.04,0,2.04-.54,2.6-1.49.28-.48.12-1.09-.36-1.37Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "bell-icon";
    }
}
