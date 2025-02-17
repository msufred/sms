package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class EditIcon extends SVGIcon {

    public EditIcon() {
        super();
    }

    public EditIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,12c-0.553,0-1,0.448-1,1v7c0,0.552-0.448,1-1,1H4c-0.551,0-1-0.448-1-1V6c0-0.551,0.449-1," +
                "1-1h7c0.552,0,1-0.448,1-1 s-0.448-1-1-1H4C2.346,3,1,4.346,1,6v14c0,1.654,1.346,3,3,3h14c1.654,0,3-1" +
                ".346,3-3v-7C21,12.448,20.553,12,20,12z M22.207,1.793c-1.178-1.18-3.236-1.18-4.414,0l-9.5,9.5c-0.128," +
                "0.128-0.219,0.289-0.263,0.464l-1,4 c-0.085,0.341,0.015,0.701,0.263,0.949C7.483,16.896,7.738,17,8," +
                "17c0.081,0,0.162-0.01,0.243-0.03l4-1 c0.176-0.044,0.336-0.135,0.464-0.263l9.5-9.5C23.424,4.99," +
                "23.424,3.01,22.207,1.793z M20.793,4.793l-9.304,9.304l-2.114,0.529 l0.528-2.114l9.304-9.305c0.424-0.424," +
                "1.162-0.424,1.586,0C21.229,3.644,21.229,4.356,20.793,4.793z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "edit-icon";
    }
}
