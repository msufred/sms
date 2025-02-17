package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArrowLeftCircleIcon extends SVGIcon {

    public ArrowLeftCircleIcon() {
        super();
    }

    public ArrowLeftCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m11,0C4.93,0,0,4.93,0,11s4.93,11,11,11,11-4.93,11-11S17.07,0,11,0Zm0,20c-4.96,0-9-4.04-9" +
                "-9S6.04,2,11,2s9,4.04,9,9-4.04,9-9,9Z M15,10h-5.59l2.29-2.29c.39-.39.39-1.02,0-1.41s-1.02-.39" +
                "-1.41,0l-4,4c-.09.09-.17.2-.22.33-.1.24-.1.52,0,.76.05.12.12.23.22.33l4,4c.2.2.45.29.71.29s.51" +
                "-.1.71-.29c.39-.39.39-1.02,0-1.41l-2.29-2.29h5.59c.55,0,1-.45,1-1s-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "arrow-left-circle-icon";
    }
}
