package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ArchiveIcon extends SVGIcon {

    public ArchiveIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public ArchiveIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m23,0H1C.45,0,0,.45,0,1v5c0,.55.45,1,1,1h1v12c0,.55.45,1,1,1h18c.55,0,1-.45,1-1V7h1c.55,0,1" +
                "-.45,1-1V1c0-.55-.45-1-1-1Zm-3,18H4V7h16v11Zm2-13H2v-3h20v3Z M10,11h4c.55,0,1-.45,1-1s-.45-1-1-1h-4c" +
                "-.55,0-1,.45-1,1s.45,1,1,1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "archive-icon";
    }
}
