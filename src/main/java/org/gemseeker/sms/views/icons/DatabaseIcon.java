package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class DatabaseIcon extends SVGIcon {

    public DatabaseIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public DatabaseIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,1C7.184,1,2,2.252,2,5v14c0,2.748,5.184,4,10,4s10-1.252,10-4V5C22,2.252,16.816,1,12,1z " +
                "M20,12c0,0.466-2.494,2-8,2 c-5.506,0-8-1.534-8-2V7.524C5.949,8.525,9.042,9,12,9s6.051-0.475,8-1.476" +
                "V12z M12,3c5.249,0,8,1.486,8,2s-2.751,2-8,2 C6.75,7,4,5.514,4,5S6.75,3,12,3z M12,21c-5.506,0-8-1.53" +
                "4-8-2v-4.476C5.949,15.525,9.042,16,12,16s6.051-0.475,8-1.476V19 C20,19.466,17.506,21,12,21z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "bookmark-icon";
    }
}
