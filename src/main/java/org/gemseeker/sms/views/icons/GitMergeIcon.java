package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class GitMergeIcon extends SVGIcon {

    public GitMergeIcon() {
        super();
    }

    public GitMergeIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M18,14c-1.842,0-3.381,1.258-3.844,2.954c-3.739-0.394-6.717-3.372-7.111-7.111C8.742,9.381,10," +
                "7.842,10,6 c0-2.206-1.794-4-4-4S2,3.794,2,6c0,1.858,1.28,3.411,3,3.858V21c0,0.553,0.448,1,1,1s1-0.447," +
                "1-1v-6.006 c1.661,2.211,4.219,3.699,7.126,3.953C14.554,20.695,16.122,22,18,22c2.206,0,4-1.794," +
                "4-4S20.206,14,18,14z M4,6 c0-1.103,0.897-2,2-2s2,0.897,2,2S7.103,8,6,8S4,7.103,4,6z M18,20c-1.103," +
                "0-2-0.897-2-2s0.897-2,2-2s2,0.897,2,2S19.103,20,18,20z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "git-merge-icon";
    }
}
