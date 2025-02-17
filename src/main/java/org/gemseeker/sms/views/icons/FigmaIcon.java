package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FigmaIcon extends SVGIcon {

    public FigmaIcon() {
        super(DEFAULT_SIZE * 0.75, DEFAULT_SIZE);
    }

    public FigmaIcon(double size) {
        super(size * 0.75, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M18.293,9C19.325,8.175,20,6.921,20,5.5C20,3.019,17.981,1,15.5,1H12H8.5C6.019,1,4,3.019," +
                "4,5.5 C4,6.921,4.675,8.175,5.707,9C4.675,9.825,4,11.079,4,12.5s0.675,2.675,1.707,3.5C4.675,16.825," +
                "4,18.079,4,19.5 C4,21.981,6.019,24,8.5,24s4.5-2.019,4.5-4.5v-3.262C13.716,16.718,14.575,17," +
                "15.5,17c2.481,0,4.5-2.019,4.5-4.5 C20,11.079,19.325,9.825,18.293,9z M18,5.5C18,6.878,16.879," +
                "8,15.5,8H13V3h2.5C16.879,3,18,4.122,18,5.5z M6,5.5 C6,4.122,7.122,3,8.5,3H11v5H8.5C7.122,8,6," +
                "6.878,6,5.5z M6,12.5C6,11.122,7.122,10,8.5,10H11v5H8.5C7.122,15,6,13.879,6,12.5z M11,19.5c0," +
                "1.379-1.122,2.5-2.5,2.5S6,20.879,6,19.5S7.122,17,8.5,17H11V19.5z M15.5,15c-1.379,0-2.5-1.121-2.5-2.5 " +
                "c0-1.378,1.121-2.5,2.5-2.5s2.5,1.122,2.5,2.5C18,13.879,16.879,15,15.5,15z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "figma-icon";
    }
}
