package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CloudLightningIcon extends SVGIcon {

    public CloudLightningIcon() {
        super();
    }

    public CloudLightningIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m19.01,17.9c-.47,0-.88-.33-.98-.8-.11-.54.24-1.07.78-1.18,2.16-.44,3.56-2.56,3.12-4.72-." +
                "38-1.86-2.03-3.2-3.92-3.2h-1.26c-.46,0-.85-.31-.97-.75-.97-3.74-4.79-5.99-8.53-5.03-1.81.47-3.33,1" +
                ".61-4.28,3.22-.95,1.61-1.21,3.49-.75,5.31.48,1.85,1.71,3.45,3.39,4.37.48.27.66.88.39,1.36-.27.48-." +
                "88.66-1.36.39-2.15-1.19-3.74-3.24-4.36-5.62C-.31,8.92.03,6.5,1.25,4.43,2.47,2.36,4.43.89,6.75.29c4." +
                "55-1.18,9.2,1.36,10.74,5.71h.53c2.84,0,5.31,2.02,5.88,4.81.66,3.24-1.45,6.42-4.69,7.07-.07.01-.13." +
                "02-.2.02Z M11.01,24c-.19,0-.38-.05-.55-.17-.46-.31-.58-.93-.28-1.39l2.96-4.45h-4.13c-.37,0-.71-.2-" +
                ".88-.53-.17-.33-.15-.72.05-1.03l4-6c.31-.46.92-.58,1.39-.28.46.31.58.93.28,1.39l-2.96,4.45h4.13c.37" +
                ",0,.71.2.88.53s.15.72-.05,1.03l-4,6c-.19.29-.51.45-.83.45Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "cloud-lightning-icon";
    }
}
