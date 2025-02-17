package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class AlertOctagonIcon extends SVGIcon {

    public AlertOctagonIcon() {
        super();
    }

    public AlertOctagonIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m21.71,6.15L15.85.29c-.19-.19-.44-.29-.71-.29H6.86c-.27,0-.52.11-.71.29L.29,6.15c-.19.19-.29." +
                "44-.29.71v8.28c0,.27.11.52.29.71l5.86,5.86c.19.19.44.29.71.29h8.28c.27,0,.52-.11.71-.29l5.86-5.86c.19-" +
                ".19.29-.44.29-.71V6.86c0-.27-.11-.52-.29-.71Zm-1.71,8.57l-5.27,5.27h-7.45l-5.27-5.27v-7.45L7.27,2h7." +
                "45l5.27,5.27v7.45Z M11,12c.55,0,1-.45,1-1v-4c0-.55-.45-1-1-1s-1,.45-1,1v4c0,.55.45,1,1,1Z " +
                "M11.01,14h0c-.55,0-1,.45-1,1s.45,1,1,1,1-.45,1-1-.45-1-1-1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "alert-octagon-icon";
    }
}
