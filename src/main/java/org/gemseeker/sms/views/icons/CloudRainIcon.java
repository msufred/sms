package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CloudRainIcon extends SVGIcon {

    public CloudRainIcon() {
        super();
    }

    public CloudRainIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m16.01,12c-.55,0-1,.45-1,1v8c0,.55.45,1,1,1s1-.45,1-1v-8c0-.55-.45-1-1-1Z " +
                "M8.01,12c-.55,0-1,.45-1,1v8c0,.55.45,1,1,1s1-.45,1-1v-8c0-.55-.45-1-1-1Z " +
                "M12.01,14c-.55,0-1,.45-1,1v8c0,.55.45,1,1,1s1-.45,1-1v-8c0-.55-.45-1-1-1Z " +
                "M23.5,9.59c-.96-2.18-3.11-3.59-5.49-3.59h-.52C15.95,1.65,11.3-.89,6.75.29," +
                "1.95,1.53-.95,6.45.29,11.25c.48,1.87,1.58,3.57,3.1,4.78.43.34,1.06.27,1.41-" +
                ".16.34-.43.27-1.06-.16-1.41-1.18-.94-2.03-2.26-2.41-3.72-.96-3.74,1.29-7.56," +
                "5.03-8.53,3.74-.96,7.56,1.29,8.53,5.03.11.44.51.75.97.75h1.26c1.59,0,3.02.94," +
                "3.66,2.4.43.98.45,2.07.06,3.06-.39,1-1.14,1.78-2.12,2.21-.51.22-.74.81-.51," +
                "1.32.16.38.53.6.92.6.13,0,.27-.03.4-.08,1.47-.64,2.6-1.82,3.18-3.31.58-1.49." +
                "55-3.12-.09-4.59Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "cloud-rain-icon";
    }
}
