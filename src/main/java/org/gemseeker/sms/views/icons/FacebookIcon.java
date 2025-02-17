package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FacebookIcon extends SVGIcon {

    public FacebookIcon() {
        super(DEFAULT_SIZE * 0.6, DEFAULT_SIZE);
    }

    public FacebookIcon(double size) {
        super(size * 0.6, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M18,7c0.553,0,1-0.448,1-1V2c0-0.552-0.447-1-1-1h-3c-3.309,0-6,2.691-6,6v2H7c-0.552,0-1," +
                "0.448-1,1v4c0,0.553,0.448,1,1,1 h2v7c0,0.553,0.448,1,1,1h4c0.553,0,1-0.447,1-1v-7h2c0.459,0," +
                "0.858-0.313,0.97-0.758l1-4c0.075-0.299,0.008-0.615-0.182-0.858 S18.308,9,18,9h-3V7H18z " +
                "M14,11h2.719l-0.5,2H14c-0.553,0-1,0.447-1,1v7h-2v-7c0-0.553-0.448-1-1-1H8v-2h2c0.552,0,1-0.448," +
                "1-1V7 c0-2.206,1.794-4,4-4h2v2h-2c-1.103,0-2,0.897-2,2v3C13,10.552,13.447,11,14,11z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "facebook-icon";
    }
}
