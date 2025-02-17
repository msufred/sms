package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CheckCircleIcon extends SVGIcon {

    public CheckCircleIcon() {
        super();
    }

    public CheckCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m21,9.09c-.55,0-1,.45-1,1v.92c0,4.96-4.04,8.99-9,8.99h0c-4.96,0-9-4.04-8.99-9,0-4.96,4.04-9," +
                "9-9h0c1.27,0,2.5.26,3.66.78.51.22,1.1,0,1.32-.51.23-.5,0-1.1-.51-1.32C14.06.32,12.56,0,11.01,0h0C4.94," +
                "0,0,4.93,0,10.99c0,6.07,4.93,11,10.99,11.01h0c6.06,0,11-4.93,11-10.99v-.92c0-.55-.45-1-1-1Z M8.71,9." +
                "31c-.39-.39-1.02-.39-1.41,0s-.39,1.02,0,1.41l3,3c.19.19.44.29.71.29s.52-.11.71-.29L21.71,3.71c.39-.39" +
                ".39-1.02,0-1.41-.39-.39-1.02-.39-1.41,0l-9.29,9.3-2.29-2.29Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "check-circle-icon";
    }
}
