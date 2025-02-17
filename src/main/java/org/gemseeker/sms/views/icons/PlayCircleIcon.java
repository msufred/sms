package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class PlayCircleIcon extends SVGIcon {

    public PlayCircleIcon() {
        super();
    }

    public PlayCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z " +
                "M12,21c-4.962,0-9-4.037-9-9 c0-4.962,4.038-9,9-9c4.963,0,9,4.038,9,9C21,16.963," +
                "16.963,21,12,21z M16.555,11.168l-6-4c-0.308-0.205-0.703-0.224-1.026-0.05C9.203," +
                "7.292,9,7.631,9,8v8c0,0.369,0.203,0.708,0.528,0.882 C9.676,16.961,9.838,17,10," +
                "17c0.194,0,0.387-0.057,0.555-0.168l6-4C16.833,12.646,17,12.334,17,12S16.833," +
                "11.354,16.555,11.168z M11,14.132V9.869L14.197,12L11,14.132z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "play-icon-icon";
    }
}
