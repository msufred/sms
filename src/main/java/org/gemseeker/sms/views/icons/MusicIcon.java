package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MusicIcon extends SVGIcon {

    public MusicIcon() {
        super();
    }

    public MusicIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.646,2.237c-0.224-0.189-0.52-0.271-0.811-0.223l-12,2C8.354,4.094,8,4.511," +
                "8,5v9.556C7.409,14.212,6.732,14,6,14 c-2.206,0-4,1.794-4,4s1.794,4,4,4s4-1.794," +
                "4-4V5.847l10-1.667v8.376C19.409,12.212,18.732,12,18,12c-2.206,0-4,1.794-4,4 " +
                "s1.794,4,4,4s4-1.794,4-4V3C22,2.706,21.871,2.427,21.646,2.237z M6,20c-1.103,0-2-0.897-2-2s0.897-2," +
                "2-2s2,0.897,2,2S7.103,20,6,20 z M18,18c-1.103,0-2-0.897-2-2s0.897-2,2-2s2,0.897,2,2S19.103,18,18,18z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "music-icon";
    }
}
