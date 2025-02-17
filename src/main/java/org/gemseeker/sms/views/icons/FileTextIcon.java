package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FileTextIcon extends SVGIcon {

    public FileTextIcon() {
        super(DEFAULT_SIZE * 0.775, DEFAULT_SIZE);
    }

    public FileTextIcon(double size) {
        super(size * 0.775, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20.922,7.615c-0.051-0.122-0.124-0.231-0.216-0.323l-5.998-5.998c-0.092-0.092-0.201-0.165-" +
                "0.323-0.216 C14.264,1.027,14.134,1,14,1H6C4.346,1,3,2.346,3,4v16c0,1.654,1.346,3,3,3h12c1.654,0," +
                "3-1.346,3-3V8 C21,7.867,20.973,7.736,20.922,7.615z M15,4.414L17.586,7H15V4.414z M18,21H6c-0.551," +
                "0-1-0.448-1-1V4c0-0.551,0.449-1,1-1h7v5 c0,0.552,0.447,1,1,1h5v11C19,20.552,18.552,21,18,21z " +
                "M16,12H8c-0.552,0-1,0.448-1,1c0,0.553,0.448,1,1,1h8c0.553,0,1-0.447,1-1C17,12.448,16.553,12,16," +
                "12z M16,16H8c-0.552,0-1,0.447-1,1s0.448,1,1,1h8c0.553,0,1-0.447,1-1S16.553,16,16,16z " +
                "M8,10h2c0.552,0,1-0.448,1-1s-0.448-1-1-1H8C7.448,8,7,8.448,7,9S7.448,10,8,10z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "file-text-icon";
    }
}
