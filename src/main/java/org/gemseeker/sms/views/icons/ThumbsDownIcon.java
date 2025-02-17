package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ThumbsDownIcon extends SVGIcon {

    public ThumbsDownIcon() {
        super();
    }

    public ThumbsDownIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22.991,3.866C22.769,2.222,21.364,1,19.728,1c-0.019,0-0.038,0-0.058,0H5.731c-1.485," +
                "0.02-2.777,1.08-3,2.549l-1.381,9.002 c-0.248,1.635,0.881,3.167,2.513,3.415c0.16,0.025,0.32," +
                "0.03,0.476,0.034H9v3c0,2.206,1.794,4,4,4c0.396,0,0.753-0.232,0.914-0.594 L17.649,14h2.003c1.668," +
                "0.002,3.113-1.203,3.339-2.866C22.997,11.09,23,11.045,23,11V4C23,3.955,22.997,3.91,22.991,3.866z " +
                "M16,12.788l-3.608,8.117C11.585,20.647,11,19.891,11,19v-4c0-0.553-0.448-1-1-1H4.331c-0.049-" +
                "0.004-0.107-0.002-0.163-0.011 c-0.545-0.083-0.921-0.594-0.839-1.138L4.709,3.85C4.783,3.359," +
                "5.242,3.028,5.72,3H16V12.788z M21,10.923 c-0.113,0.628-0.676,1.079-1.33,1.077H18V3h1.688c0.008," +
                "0,0.016,0,0.023,0C20.346,3,20.888,3.456,21,4.077V10.923z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "thumbs-down-icon";
    }
}
