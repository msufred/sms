package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FileIcon extends SVGIcon {

    public FileIcon() {
        super(DEFAULT_SIZE * 0.775, DEFAULT_SIZE);
    }

    public FileIcon(double size) {
        super(size * 0.775, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20.922,8.615c-0.051-0.122-0.124-0.231-0.216-0.323l-6.998-6.998c-0.092-0.092-0.201-" +
                "0.165-0.323-0.216 C13.264,1.027,13.134,1,13,1H6C4.346,1,3,2.346,3,4v16c0,1.654,1.346,3,3," +
                "3h12c1.654,0,3-1.346,3-3V9 C21,8.867,20.973,8.736,20.922,8.615z M14,4.414L17.586,8H14V4.414z " +
                "M18,21H6c-0.551,0-1-0.448-1-1V4c0-0.551,0.449-1,1-1h6v6 c0,0.552,0.448,1,1,1h6v10C19,20.552," +
                "18.552,21,18,21z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "file-icon";
    }
}
