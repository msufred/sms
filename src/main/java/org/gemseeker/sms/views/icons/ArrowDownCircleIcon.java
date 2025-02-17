package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArrowDownCircleIcon extends SVGIcon {

    public ArrowDownCircleIcon() {
        super();
    }

    public ArrowDownCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m11,0C4.93,0,0,4.93,0,11s4.93,11,11,11,11-4.93,11-11S17.07,0,11,0Zm0,20c-4.96,0-9-4.04-9-9S6.04" +
                ",2,11,2s9,4.04,9,9-4.04,9-9,9Z M14.29,10.29l-2.29,2.29v-5.59c0-.55-.45-1-1-1s-1,.45-1,1v5.59l-2.29" +
                "-2.29c-.39-.39-1.02-.39-1.41,0s-.39,1.02,0,1.41l4,4c.09.09.2.17.33.22.12.05.25.08.38.08s.26-.03.38" +
                "-.08c.12-.05.23-.12.33-.22l4-4c.39-.39.39-1.02,0-1.41s-1.02-.39-1.41,0Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "arrow-down-circle-icon";
    }
}
