package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FolderPlusIcon extends SVGIcon {

    public FolderPlusIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public FolderPlusIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,5h-8.465L9.832,2.445C9.646,2.167,9.334,2,9,2H4C2.346,2,1,3.346,1,5v14c0,1.654,1.346," +
                "3,3,3h16c1.654,0,3-1.346,3-3V8 C23,6.346,21.654,5,20,5z M21,19c0,0.552-0.448,1-1,1H4c-0.551," +
                "0-1-0.448-1-1V5c0-0.551,0.449-1,1-1h4.465l1.703,2.555 C10.354,6.833,10.666,7,11,7h9c0.552,0,1," +
                "0.449,1,1V19z M15,13h-2v-2c0-0.552-0.447-1-1-1c-0.552,0-1,0.448-1,1v2H9c-0.552,0-1,0.447-1," +
                "1s0.448,1,1,1h2v2c0,0.553,0.448,1,1,1 c0.553,0,1-0.447,1-1v-2h2c0.553,0,1-0.447,1-1S15.553,13,15,13z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "folder-plus-icon";
    }
}
