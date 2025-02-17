package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CreditCardIcon extends SVGIcon {

    public CreditCardIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.75);
    }

    public CreditCardIcon(double size) {
        super(size, size * 0.75);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21,3H3C1.346,3,0,4.346,0,6v12c0,1.654,1.346,3,3,3h18c1.654,0,3-1.346,3-3V6C24,4.346,22.654,3,21,3z M3,5h18" +
                "c0.552,0,1,0.449,1,1v3H2V6C2,5.449,2.449,5,3,5z M21,19H3c-0.551,0-1-0.448-1-1v-7h20v7C22,18.552,21.552,19,21,19z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "credit-card-icon";
    }
}
