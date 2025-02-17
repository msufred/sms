package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ActivityIcon extends SVGIcon {

    public ActivityIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public ActivityIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m14,20c-.43,0-.81-.28-.95-.68l-5.05-15.15-2.05,6.15c-.14.41-.52.68-.95.68H1c-.55,0-1-.45-1-1s." +
                "45-1,1-1h3.28L7.05.68c.14-.41.52-.68.95-.68s.81.28.95.68l5.05,15.15,2.05-6.15c.14-.41.52-.68.95-.68h4c." +
                "55,0,1,.45,1,1s-.45,1-1,1h-3.28l-2.77,8.32c-.14.41-.52.68-.95.68Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "activity-icon";
    }
}
