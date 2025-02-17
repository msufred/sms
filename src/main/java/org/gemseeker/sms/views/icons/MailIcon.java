package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MailIcon extends SVGIcon {

    public MailIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.755);
    }

    public MailIcon(double size) {
        super(size, size * 0.755);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,3H4C2.346,3,1,4.346,1,6v12c0,1.654,1.346,3,3,3h16c1.654,0,3-1.346,3-3V6C23,4.346,21.654,3,20,3z " +
                "M4,5h16 c0.391,0,0.722,0.231,0.886,0.559L12,11.779l-8.886-6.22C3.278,5.231,3.609,5,4,5z " +
                "M20,19H4c-0.551,0-1-0.448-1-1V7.921l8.427,5.899 C11.599,13.939,11.799,14,12,14s0.401-0.061," +
                "0.573-0.181L21,7.921V18C21,18.552,20.552,19,20,19z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "mail-icon";
    }
}
