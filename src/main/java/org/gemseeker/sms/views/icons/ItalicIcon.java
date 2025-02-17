package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ItalicIcon extends SVGIcon {

    public ItalicIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public ItalicIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19,3h-9C9.448,3,9,3.448,9,4s0.448,1,1,1h3.557l-5.25,14H5c-0.552,0-1,0.447-1,1s0.448,1,1,1h9c0.553,0,1-0.447,1-1 " +
                "s-0.447-1-1-1h-3.557l5.25-14H19c0.553,0,1-0.448,1-1S19.553,3,19,3z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "italic-icon";
    }
}
