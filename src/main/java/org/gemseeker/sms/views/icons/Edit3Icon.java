package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class Edit3Icon extends SVGIcon {

    public Edit3Icon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.9);
    }

    public Edit3Icon(double size) {
        super(size, size * 0.9);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21,19h-9c-0.552,0-1,0.447-1,1s0.448,1,1,1h9c0.553,0,1-0.447,1-1S21.553,19,21,19z " +
                "M20.207,2.793c-1.178-1.18-3.236-1.18-4.414,0l-12.5,12.5c-0.128,0.128-0.219,0.289-0.263," +
                "0.465l-1,4 c-0.085,0.341,0.015,0.701,0.263,0.949C2.483,20.896,2.738,21,3,21c0.081,0," +
                "0.162-0.01,0.243-0.03l4-1 c0.176-0.044,0.336-0.135,0.464-0.263l12.5-12.5C21.424,5.99," +
                "21.424,4.01,20.207,2.793z M18.793,5.793L6.489,18.097l-2.114,0.529 l0.528-2.114L17.207," +
                "4.207c0.424-0.424,1.162-0.424,1.586,0C19.229,4.644,19.229,5.356,18.793,5.793z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "edit3-icon";
    }
}
