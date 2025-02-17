package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class GlobeIcon extends SVGIcon {

    public GlobeIcon() {
        super();
    }

    public GlobeIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C5.935,1,1,5.935,1,12s4.935,11,11,11s11-4.935,11-11S18.065,1,12,1z M20.941,11h-4.006 " +
                "c-0.224-2.762-1.145-5.407-2.671-7.7C17.819,4.226,20.526,7.268,20.941,11z M9.061,13h5.877c-0.251," +
                "2.714-1.267,5.298-2.938,7.449 C10.324,18.29,9.307,15.698,9.061,13z M9.063,11C9.313,8.286,10.33," +
                "5.702,12,3.551C13.677,5.71,14.694,8.302,14.94,11H9.063z M9.736,3.3c-1.52,2.286-2.44,4.925-2.669," +
                "7.7H3.059C3.474,7.268,6.181,4.226,9.736,3.3z M3.059,13h4.006 c0.224,2.762,1.145,5.407,2.67," +
                "7.7C6.181,19.773,3.474,16.732,3.059,13z M14.264,20.7c1.52-2.286,2.44-4.925,2.669-7.7h4.008 " +
                "C20.526,16.732,17.819,19.774,14.264,20.7z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "globe-icon";
    }
}
