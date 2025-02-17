package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArrowUpCircleIcon extends SVGIcon {

    public ArrowUpCircleIcon() {
        super();
    }

    public ArrowUpCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m11,0C4.93,0,0,4.93,0,11s4.93,11,11,11,11-4.93,11-11S17.07,0,11,0Zm0,20c-4.96,0-9-4.04-9-9S6." +
                "04,2,11,2s9,4.04,9,9-4.04,9-9,9Z M11.71,6.29c-.09-.09-.2-.17-.33-.22-.24-.1-.52-.1-.76,0-.12.05-.23." +
                "12-.33.22l-4,4c-.39.39-.39,1.02,0,1.41s1.02.39,1.41,0l2.29-2.29v5.59c0,.55.45,1,1,1s1-.45,1-1v-5.59l2" +
                ".29,2.29c.2.2.45.29.71.29s.51-.1.71-.29c.39-.39.39-1.02,0-1.41l-4-4Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "arrow-up-circle-icon";
    }
}
