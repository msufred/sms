package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ToggleRightIcon extends SVGIcon {

    public ToggleRightIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.65);
    }

    public ToggleRightIcon(double size) {
        super(size, size * 0.65);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M16,4H8c-4.411,0-8,3.589-8,8s3.589,8,8,8h8c4.411,0,8-3.589,8-8S20.411,4,16,4z " +
                "M16,18H8c-3.309,0-6-2.691-6-6 s2.691-6,6-6h8c3.309,0,6,2.691,6,6S19.309,18,16,18z " +
                "M16,8c-2.206,0-4,1.794-4,4c0,2.206,1.794,4,4,4s4-1.794,4-4C20,9.794,18.206,8,16,8z " +
                "M16,14c-1.103,0-2-0.897-2-2 c0-1.103,0.897-2,2-2s2,0.897,2,2C18,13.103,17.103,14,16,14z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "toggle-right-icon";
    }
}
