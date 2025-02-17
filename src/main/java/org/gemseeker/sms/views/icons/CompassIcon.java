package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CompassIcon extends SVGIcon {

    public CompassIcon() {
        super();
    }

    public CompassIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m11,0C4.93,0,0,4.93,0,11s4.93,11,11,11,11-4.93,11-11S17.07,0,11,0Zm0,20c-4.96,0-9-4.04-9-9S6." +
                "04,2,11,2s9,4.04,9,9-4.04,9-9,9Z M14.92,5.81l-6.36,2.12c-.3.1-.53.33-.63.63l-2.12,6.36c-.12.36-.03.76" +
                ".24,1.02.19.19.45.29.71.29.11,0,.21-.02.32-.05l6.36-2.12c.3-.1.53-.33.63-.63l2.12-6.36c.12-.36.03-.76" +
                "-.24-1.02-.27-.27-.66-.36-1.02-.24Zm-2.59,6.52l-3.99,1.33,1.33-3.99,3.99-1.33-1.33,3.99Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "compass-icon";
    }
}
