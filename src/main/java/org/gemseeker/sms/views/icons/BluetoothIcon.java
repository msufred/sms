package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class BluetoothIcon extends SVGIcon {

    public BluetoothIcon() {
        super(DEFAULT_SIZE * 0.55, DEFAULT_SIZE);
    }

    public BluetoothIcon(double size) {
        super(size * 0.55, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m7.91,12l4.79-4.79c.39-.39.39-1.02,0-1.41L7.21.29c-.29-.29-.72-.37-1.09-.22-.37.15-.62.52-." +
                "62.92v8.59l-3.79-3.79c-.39-.39-1.02-.39-1.41,0s-.39,1.02,0,1.41l4.79,4.79L.29,16.79c-.39.39-.39,1.02," +
                "0,1.41s1.02.39,1.41,0l3.79-3.79v8.59c0,.4.24.77.62.92.12.05.25.08.38.08.26,0,.52-.1.71-.29l5.5-5.5c." +
                "39-.39.39-1.02,0-1.41l-4.79-4.79Zm-.41-8.59l3.09,3.09-3.09,3.09V3.41Zm0,17.17v-6.17l3.09,3.09-3.09,3.09Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "bluetooth-icon";
    }
}
