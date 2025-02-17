package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class BookOpenIcon extends SVGIcon {

    public BookOpenIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public BookOpenIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m21,0h-6c-1.64,0-3.09.81-4,2.03-.91-1.23-2.36-2.03-4-2.03H1C.45,0,0,.45,0,1v15c0,.55.45,1,1," +
                "1h7c1.1,0,2,.9,2,2,0,.55.45,1,1,1s1-.45,1-1c0-1.1.9-2,2-2h7c.55,0,1-.45,1-1V1c0-.55-.45-1-1-1Zm-11,15." +
                "54c-.59-.34-1.27-.54-2-.54H2V2h5c1.65,0,3,1.35,3,3v10.54Zm10-.54h-6c-.73,0-1.41.2-2,.54V5c0-1.65,1.35-" +
                "3,3-3h5v13Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "book-open-icon";
    }
}
