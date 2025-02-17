package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ThumbsUpIcon extends SVGIcon {

    public ThumbsUpIcon() {
        super();
    }

    public ThumbsUpIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22.099,9.221c-0.476-0.645-1.174-1.066-1.967-1.187C19.973,8.009,19.815,8.004,19.66," +
                "8H15V5c0-2.206-1.794-4-4-4 c-0.395,0-0.753,0.233-0.914,0.594L6.35,10H4c-1.654,0-3,1.346-3," +
                "3v7c0,1.654,1.346,3,3,3h14.269c0.012,0,0.023,0,0.035,0 c1.473,0,2.744-1.091,2.965-2.549l1.38-" +
                "9.001C22.77,10.658,22.574,9.866,22.099,9.221z M6,21H4c-0.551,0-1-0.448-1-1v-7 c0-0.551," +
                "0.449-1,1-1h2V21z M19.292,20.15c-0.074,0.49-0.519,0.836-1.012,0.85H8v-9.788l3.608-" +
                "8.118C12.415,3.353,13,4.109,13,5v4 c0,0.552,0.447,1,1,1h5.674c0.057,0.001,0.106,0.003," +
                "0.158,0.011c0.265,0.04,0.498,0.181,0.656,0.396 c0.158,0.215,0.224,0.479,0.184," +
                "0.741L19.292,20.15z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "thumbs-up-icon";
    }
}
