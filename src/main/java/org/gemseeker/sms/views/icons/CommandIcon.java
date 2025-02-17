package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CommandIcon extends SVGIcon {

    public CommandIcon() {
        super();
    }

    public CommandIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m16,12h-2v-4h2c2.21,0,4-1.79,4-4s-1.79-4-4-4-4,1.79-4,4v2h-4v-2C8,1.79,6.21,0,4,0S0,1.79,0," +
                "4s1.79,4,4,4h2v4h-2c-2.21,0-4,1.79-4,4s1.79,4,4,4,4-1.79,4-4v-2h4v2c0,2.21,1.79,4,4,4s4-1.79,4-4-1." +
                "79-4-4-4Zm-2-8c0-1.1.9-2,2-2s2,.9,2,2-.9,2-2,2h-2v-2Zm-8,12c0,1.1-.9,2-2,2s-2-.9-2-2,.9-2,2-2h2v2Zm0" +
                "-10h-2c-1.1,0-2-.9-2-2s.9-2,2-2,2,.9,2,2v2Zm6,6h-4v-4h4v4Zm4,6c-1.1,0-2-.9-2-2v-2h2c1.1,0,2,.9,2,2s-" +
                ".9,2-2,2Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "command-icon";
    }
}
