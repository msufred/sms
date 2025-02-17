package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class KeyIcon extends SVGIcon {

    public KeyIcon() {
        super();
    }

    public KeyIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22.707,6.293L20.414,4l1.293-1.293c0.391-0.391,0.391-1.023,0-1.414s-1.023-0.391-1.414," +
                "0l-8.968,8.967 C8.81,8.467,5.343,8.678,3.052,10.892c-2.577,2.49-2.649,6.613-0.09,9.261c1.264," +
                "1.247,2.915,1.869,4.564,1.869 c1.679,0,3.358-0.645,4.627-1.932c2.277-2.308,2.471-5.901," +
                "0.6-8.43L15.5,8.914l2.293,2.293c0.391,0.391,1.023,0.391,1.414,0 l3.5-3.5C23.098,7.316," +
                "23.098,6.684,22.707,6.293z M10.73,18.686c-1.743,1.767-4.597,1.784-6.399,0.008 " +
                "c-1.723-1.785-1.673-4.639,0.111-6.363c0.876-0.847,2.001-1.27,3.127-1.27c1.123,0,2.247," +
                "0.422,3.123,1.265 C12.454,14.068,12.471,16.921,10.73,18.686z M18.5,9.086L16.914,7.5L19," +
                "5.414L20.586,7L18.5,9.086z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "key-icon";
    }
}
