package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class HardDriveIcon extends SVGIcon {

    public HardDriveIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public HardDriveIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22.901,11.581c-0.004-0.009-0.002-0.02-0.007-0.028l-3.449-6.888C18.936,3.639,17.906," +
                "3.001,16.76,3H7.24 C6.093,3.001,5.064,3.639,4.556,4.662l-3.45,6.89c-0.004,0.009-0.002,0.019-0.007," +
                "0.028C1.039,11.709,1,11.849,1,12v6 c0,1.654,1.346,3,3,3h16c1.654,0,3-1.346,3-3v-6C23,11.849," +
                "22.961,11.709,22.901,11.581z M6.346,5.555C6.516,5.213,6.858,5,7.24,5 h9.519c0.383,0,0.726,0.213," +
                "0.896,0.558L20.381,11H3.619L6.346,5.555z M20,19H4c-0.551,0-1-0.448-1-1v-5h18v5 C21,18.552,20.552," +
                "19,20,19z M6.01,15H6c-0.552,0-0.995,0.447-0.995,1s0.453,1,1.005,1s1-0.447,1-1S6.562,15,6.01,15z " +
                "M10.01,15H10c-0.552,0-0.995,0.447-0.995,1s0.453,1,1.005,1s1-0.447,1-1S10.562,15,10.01,15z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "hard-drive-icon";
    }
}
