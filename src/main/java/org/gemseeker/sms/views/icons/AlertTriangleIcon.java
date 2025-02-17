package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class AlertTriangleIcon extends SVGIcon {

    public AlertTriangleIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public AlertTriangleIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m22.48,15.59L14.01,1.45c-.42-.69-1.07-1.17-1.85-1.36-.78-.19-1.58-.07-2.27.35-.41.25-.76.6-1." +
                "01,1.01,0,0,0,0,0,0L.4,15.6c-.83,1.43-.33,3.27,1.1,4.1.45.26.95.4,1.48.4h16.95c.8,0,1.55-.33,2.11-.9." +
                "56-.57.87-1.33.86-2.13,0-.51-.15-1.02-.41-1.48Zm-1.87,2.21c-.19.19-.44.3-.69.3H2.99c-.17,0-.34-.05-." +
                "49-.13-.48-.28-.64-.89-.37-1.35L10.59,2.48c.08-.14.2-.25.34-.33.47-.29,1.09-.14,1.37.33l8.46,14.13c." +
                "09.15.13.32.13.49,0,.27-.1.52-.29.71Z M11.45,12.1c.55,0,1-.45,1-1v-4c0-.55-.45-1-1-1s-1,.45-1,1v4c0," +
                ".55.45,1,1,1Z M11.46,14.1h0c-.55,0-1,.45-1,1s.45,1,1,1,1-.45,1-1-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "alert-triangle-icon";
    }
}
