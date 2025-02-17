package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CoffeeIcon extends SVGIcon {

    public CoffeeIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public CoffeeIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m18,7H1c-.55,0-1,.45-1,1v9c0,2.76,2.24,5,5,5h8c2.76,0,5-2.24,5-5,2.76,0,5-2.24,5-5s-2.24-5-5-" +
                "5Zm-2,10c0,1.65-1.35,3-3,3H5c-1.65,0-3-1.35-3-3v-8h14v8Zm2-2v-6c1.65,0,3,1.35,3,3s-1.35,3-3,3Z M5,5c." +
                "55,0,1-.45,1-1V1c0-.55-.45-1-1-1s-1,.45-1,1v3c0,.55.45,1,1,1Z M9,5c.55,0,1-.45,1-1V1c0-.55-.45-1-1-1s" +
                "-1,.45-1,1v3c0,.55.45,1,1,1Z M13,5c.55,0,1-.45,1-1V1c0-.55-.45-1-1-1s-1,.45-1,1v3c0,.55.45,1,1,1Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "coffee-icon";
    }
}
