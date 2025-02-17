package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class HelpCircleIcon extends SVGIcon {

    public HelpCircleIcon() {
        super();
    }

    public HelpCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M12,23A11,11,0,1,1,23,12,11,11,0,0,1,12,23ZM12,3a9,9,0,1,0,9,9A9,9,0,0,0,12,3Z " +
                "M11.92,14a1,1,0,0,1-.32-1.95c.6-.2,2.32-1,2.32-2.05A2,2,0,0,0,10,9.33a1,1,0,1,1-1.89-.66A4,4,0,0,1," +
                "15.92,10c0,2.64-3.31,3.82-3.68,3.95A1,1,0,0,1,11.92,14Z M12,18a1,1,0,0,1,0-2h0a1,1,0,0,1,0,2Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "help-circle-icon";
    }
}
