package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ShareIcon extends SVGIcon {

    public ShareIcon() {
        super(DEFAULT_SIZE * 0.8, DEFAULT_SIZE);
    }

    public ShareIcon(double size) {
        super(size * 0.8, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20,11c-0.553,0-1,0.448-1,1v8c0,0.552-0.448,1-1,1H6c-0.551,0-1-0.448-1-1v-8c0-0.552-" +
                "0.448-1-1-1s-1,0.448-1,1v8 c0,1.654,1.346,3,3,3h12c1.654,0,3-1.346,3-3v-8C21,11.448,20.553,11," +
                "20,11z M8.707,6.707L11,4.414V15c0,0.553,0.448,1,1,1c0.553,0,1-0.447,1-1V4.414l2.293,2.293C15.488," +
                "6.902,15.744,7,16,7 s0.512-0.098,0.707-0.293c0.391-0.391,0.391-1.023,0-1.414l-3.999-3.999c-" +
                "0.093-0.093-0.203-0.166-0.326-0.217 c-0.244-0.101-0.52-0.101-0.764,0c-0.122,0.051-0.233," +
                "0.124-0.325,0.216l-4,4c-0.391,0.391-0.391,1.023,0,1.414 S8.316,7.098,8.707,6.707z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "share-icon";
    }
}
