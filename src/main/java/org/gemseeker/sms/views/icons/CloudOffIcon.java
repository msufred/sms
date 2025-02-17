package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CloudOffIcon extends SVGIcon {

    public CloudOffIcon() {
        super();
    }

    public CloudOffIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m9.74,5c2.96.26,5.44,2.37,6.17,5.25.11.44.51.75.97.75h1.27c.54,0,1.06.11,1.56.32,2.03.86,2.98," +
                "3.21,2.12,5.24-.21.51.02,1.1.53,1.31.13.05.26.08.39.08.39,0,.76-.23.92-.61,1.29-3.05-.14-6.58-3.19-7." +
                "86-.75-.32-1.55-.52-2.35-.48h-.52c-1.16-3.31-4.16-5.68-7.7-6-.55-.05-1.04.36-1.08.91-.05.55.36,1.04.91," +
                "1.08Z M1.84.29C1.45-.1.82-.1.43.29S.04,1.32.43,1.71l3.14,3.14c-1.54,1.16-2.66,2.78-3.21,4.67-.67,2.31-" +
                ".39,4.74.77,6.85,1.59,2.88,4.6,4.64,7.86,4.64.05,0,.1,0,.15,0h9c.47,0,.94-.08,1.4-.19l2.89,2.89c.2.2." +
                "45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02,0-1.41L1.84.29Zm7.28,18.71c-2.61.07-4.98-1.34-6.24-3.61-.9-1" +
                ".64-1.12-3.53-.6-5.32.45-1.56,1.4-2.88,2.71-3.8l12.73,12.73h-8.6Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "cloud-off-icon";
    }
}
