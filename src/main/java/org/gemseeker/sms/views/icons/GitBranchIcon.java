package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class GitBranchIcon extends SVGIcon {

    public GitBranchIcon() {
        super();
    }

    public GitBranchIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22,6c0-2.206-1.794-4-4-4s-4,1.794-4,4c0,1.842,1.258,3.381,2.955,3.844c-0.394,3.738-3.372," +
                "6.717-7.111,7.111 C9.468,15.578,8.383,14.502,7,14.142V3c0-0.552-0.448-1-1-1S5,2.448,5,3v11.142C3.28," +
                "14.589,2,16.141,2,18c0,2.206,1.794,4,4,4 c1.875,0,3.44-1.301,3.872-3.044c4.818-0.419,8.665-4.266," +
                "9.084-9.084C20.699,9.44,22,7.875,22,6z M6,20c-1.103,0-2-0.897-2-2 s0.897-2,2-2s2,0.897,2,2S7.103," +
                "20,6,20z M18,8c-1.103,0-2-0.897-2-2s0.897-2,2-2s2,0.897,2,2S19.103,8,18,8z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "git-branch-icon";
    }
}
