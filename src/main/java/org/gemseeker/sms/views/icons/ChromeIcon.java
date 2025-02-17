package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ChromeIcon extends SVGIcon {

    public ChromeIcon() {
        super();
    }

    public ChromeIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m21.1,6.65s-.03-.06-.05-.1C19.34,2.7,15.48,0,11,0,7.42,0,4.24,1.73,2.23,4.39c-.05.05-.09.11-" +
                ".13.17C.79,6.37,0,8.59,0,11c0,5.59,4.2,10.22,9.61,10.9.05.01.09.02.14.02.41.05.83.08,1.25.08,6.07,0," +
                "11-4.93,11-11,0-1.54-.32-3.01-.9-4.35ZM11,2c3.11,0,5.86,1.59,7.48,4h-7.48c-2.06,0-3.83,1.25-4.6,3.04l" +
                "-2.24-3.88c1.65-1.93,4.1-3.16,6.84-3.16Zm3,9c0,1.65-1.35,3-3,3s-3-1.35-3-3,1.35-3,3-3,3,1.35,3,3Zm-12" +
                ",0c0-1.43.34-2.77.93-3.97l3.74,6.47s.05.06.07.09c.88,1.44,2.45,2.41,4.25,2.41.21,0,.41-.04.61-.06l-2." +
                "25,3.9c-4.18-.77-7.36-4.44-7.36-8.84Zm9.59,8.97l3.73-6.47s.02-.07.04-.11c.39-.71.64-1.52.64-2.39,0-1." +
                "13-.39-2.16-1.03-3h4.5c.33.94.53,1.95.53,3,0,4.76-3.72,8.66-8.41,8.97Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "chrome-icon";
    }
}
