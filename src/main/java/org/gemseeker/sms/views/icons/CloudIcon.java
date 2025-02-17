package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CloudIcon extends SVGIcon {

    public CloudIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.75);
    }

    public CloudIcon(double size) {
        super(size, size * 0.75);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m9,18c-4.1,0-7.69-2.77-8.71-6.75C-.31,8.93.03,6.5,1.25,4.43,2.47,2.36,4.43.89,6.75.29c2.33-.6," +
                "4.75-.26,6.82.96,1.84,1.09,3.21,2.76,3.92,4.75h.52c3.31,0,6,2.69,6,6s-2.69,6-6,6h-9.01Zm0-16c-.58,0-1." +
                "16.07-1.75.22-1.81.47-3.33,1.61-4.28,3.22s-1.21,3.49-.75,5.31c.8,3.09,3.58,5.25,6.78,5.25h9.01c2.21,0," +
                "4-1.79,4-4s-1.79-4-4-4h-1.26c-.46,0-.85-.31-.97-.75-.81-3.15-3.66-5.25-6.78-5.25Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "cloud-icon";
    }
}
