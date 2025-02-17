package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class OctagonIcon extends SVGIcon {

    public OctagonIcon() {
        super();
    }

    public OctagonIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22.707,7.153l-5.86-5.86C16.659,1.105,16.405,1,16.14,1H7.86C7.595,1,7.34,1.105,7.153," +
                "1.293l-5.86,5.86 C1.105,7.34,1,7.595,1,7.86v8.28c0,0.266,0.105,0.52,0.293,0.707l5.86,5.86C7.34," +
                "22.895,7.595,23,7.86,23h8.28 c0.266,0,0.52-0.105,0.707-0.293l5.86-5.86C22.895,16.659,23,16.405," +
                "23,16.14V7.86C23,7.595,22.895,7.34,22.707,7.153z M21,15.726 L15.726,21H8.274L3,15.726V8.274L8.274," +
                "3h7.452L21,8.274V15.726z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "octagon-icon";
    }
}
