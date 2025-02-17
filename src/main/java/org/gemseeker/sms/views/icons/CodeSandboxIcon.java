package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class CodeSandboxIcon extends SVGIcon {

    public CodeSandboxIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public CodeSandboxIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("m19.67,5.68c-.08-.16-.19-.34-.32-.5-.24-.3-.51-.58-.85-.78L11.5.4c-.93-.53-2.08-.54-3,0L1.5," +
                "4.4c-.35.2-.62.48-.86.79-.12.16-.22.31-.31.48-.2.41-.33.86-.33,1.33v8c0,1.07.58,2.06,1.5,2.6l7,4c.32" +
                ".18.66.28,1.01.34.15.09.31.15.49.15s.34-.06.49-.15c.35-.06.69-.15,1.01-.34l7-4c.92-.53,1.5-1.53,1.5-2" +
                ".59h0V7c0-.47-.13-.91-.33-1.32Zm-9.67,4.18L3.04,5.83l2.51-1.43,3.95,2.28c.15.09.33.13.5.13s.35-.04.5-" +
                ".13l3.95-2.28,2.51,1.43-6.96,4.03Zm-.5-7.72c.15-.09.33-.13.5-.13s.35.05.5.14l1.94,1.11-2.44,1.41-2.44" +
                "-1.41,1.94-1.11ZM2,15v-2.27l2.5,1.44v2.83l-2-1.14c-.31-.18-.5-.51-.5-.87Zm4.5,3.15v-4.55c0-.36-.19-.69" +
                "-.5-.87l-4-2.31v-2.89l7,4.05v7.99l-2.5-1.43Zm4.5,1.43v-7.99l7-4.05v2.89l-4,2.31c-.31.18-.5.51-.5.87v4." +
                "55l-2.5,1.43Zm6.5-3.72l-2,1.15v-2.83l2.5-1.44v2.27c0,.36-.19.69-.5.86Z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "code-sandbox-icon";
    }
}
