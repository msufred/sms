package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class GitCommitIcon extends SVGIcon {

    public GitCommitIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.45);
    }

    public GitCommitIcon(double size) {
        super(size, size * 0.45);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22.96,11h-5.95c-0.039,0-0.071,0.018-0.109,0.022C16.445,8.732,14.422,7,12,7c-2.421,0-4.444," +
                "1.731-4.901,4.02 C7.065,11.016,7.035,11,7,11H1.05c-0.552,0-1,0.448-1,1c0,0.553,0.448,1,1,1H7c0.035," +
                "0,0.065-0.016,0.099-0.02 C7.556,15.269,9.579,17,12,17c2.422,0,4.445-1.732,4.901-4.022C16.938,12.982," +
                "16.971,13,17.01,13h5.95c0.553,0,1-0.447,1-1 C23.96,11.448,23.513,11,22.96,11z M12,15c-1.654," +
                "0-3-1.346-3-3s1.346-3,3-3s3,1.346,3,3S13.654,15,12,15z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "git-commit-icon";
    }
}
