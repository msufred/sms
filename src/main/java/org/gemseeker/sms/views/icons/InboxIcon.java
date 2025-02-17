package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class InboxIcon extends SVGIcon {

    public InboxIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public InboxIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22.901,11.581c-0.004-0.009-0.002-0.02-0.007-0.028l-3.449-6.888C18.936,3.639,17.906,3.001," +
                "16.76,3H7.24 C6.093,3.001,5.064,3.639,4.556,4.662l-3.45,6.89c-0.004,0.009-0.002,0.019-0.007," +
                "0.028C1.039,11.709,1,11.849,1,12v6 c0,1.654,1.346,3,3,3h16c1.654,0,3-1.346,3-3v-6C23,11.849,22.961," +
                "11.709,22.901,11.581z M6.346,5.555C6.516,5.213,6.858,5,7.24,5 h9.519c0.383,0,0.726,0.213,0.896," +
                "0.558L20.381,11H16c-0.334,0-0.646,0.167-0.832,0.445L13.465,14h-2.93l-1.703-2.555 C8.646,11.167," +
                "8.334,11,8,11H3.619L6.346,5.555z M20,19H4c-0.551,0-1-0.448-1-1v-5h4.465l1.703,2.555C9.354,15.833," +
                "9.666,16,10,16h4 c0.334,0,0.646-0.167,0.832-0.445L16.535,13H21v5C21,18.552,20.552,19,20,19z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "inbox-icon";
    }
}
