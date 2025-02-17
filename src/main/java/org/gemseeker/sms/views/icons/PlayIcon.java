package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class PlayIcon extends SVGIcon {

    public PlayIcon() {
        super(DEFAULT_SIZE * 0.75, DEFAULT_SIZE);
    }

    public PlayIcon(double size) {
        super(size * 0.75, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19.541,11.159l-14-9c-0.308-0.197-0.7-0.212-1.02-0.037C4.2,2.297,4,2.634,4,3v18c0," +
                "0.366,0.2,0.702,0.521,0.878 C4.67,21.959,4.835,22,5,22c0.188,0,0.376-0.054,0.541-0.159l14-" +
                "9C19.827,12.657,20,12.34,20,12S19.827,11.343,19.541,11.159z M6,19.168V4.832L17.15,12L6,19.168z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "play-icon";
    }
}
