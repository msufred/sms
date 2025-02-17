package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class BoxIcon extends SVGIcon {

    public BoxIcon() {
        super(DEFAULT_SIZE * 0.95, DEFAULT_SIZE);
    }

    public BoxIcon(double size) {
        super(size * 0.95, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m19.67,5.68c-.02-.07-.03-.15-.07-.22-.07-.11-.15-.21-.25-.28-.24-.3-.51-.58-.85-.78L11.5.4c-." +
                "93-.53-2.08-.54-3,0L1.5,4.4c-.35.2-.62.48-.86.79-.09.07-.17.16-.24.27-.04.07-.05.14-.07.21-.2.41-.33." +
                "86-.33,1.33v8c0,1.07.58,2.06,1.5,2.6l7,4c.32.18.66.28,1.01.34.15.09.31.15.49.15s.34-.06.49-.15c.35-.06" +
                ".69-.15,1.01-.34l7-4c.92-.53,1.5-1.53,1.5-2.59h0V7c0-.47-.13-.91-.33-1.32Zm-10.17-3.54c.15-.09.33-.13." +
                "5-.13s.35.05.5.14l6.46,3.69-6.96,4.03L3.04,5.83l6.46-3.69ZM2.5,15.86c-.31-.18-.5-.51-.5-.87v-7.46l7,4." +
                "05v7.99l-6.5-3.71Zm15,0l-6.5,3.72v-7.99l7-4.05v7.46c0,.36-.19.69-.5.86Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "box-icon";
    }
}
