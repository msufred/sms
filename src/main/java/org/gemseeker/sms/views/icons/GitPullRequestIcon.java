package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class GitPullRequestIcon extends SVGIcon {

    public GitPullRequestIcon() {
        super();
    }

    public GitPullRequestIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,14.142V8c0-1.654-1.346-3-3-3h-3c-0.552,0-1,0.448-1,1s0.448,1,1,1h3c0.552,0,1,0.449,1," +
                "1v6.142 c-1.72,0.447-3,1.999-3,3.858c0,2.206,1.794,4,4,4s4-1.794,4-4C22,16.141,20.72,14.589,19,14.142z " +
                "M18,20c-1.103,0-2-0.897-2-2 s0.897-2,2-2s2,0.897,2,2S19.103,20,18,20z M6,2C3.794,2,2,3.794,2,6c0,1.858," +
                "1.28,3.411,3,3.858V21c0,0.553,0.448,1,1,1s1-0.447,1-1V9.858c1.72-0.447,3-2,3-3.858 C10,3.794,8.206,2,6,2z " +
                "M6,8C4.897,8,4,7.103,4,6s0.897-2,2-2s2,0.897,2,2S7.103,8,6,8z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "git-pull-request-icon";
    }
}
