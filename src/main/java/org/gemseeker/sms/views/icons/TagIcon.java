package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TagIcon extends SVGIcon {

    public TagIcon() {
        super();
    }

    public TagIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.297,9.883l-8.59-8.59C12.52,1.105,12.265,1,12,1H2C1.448,1,1,1.448,1,2v10c0,0.266," +
                "0.105,0.52,0.293,0.707l8.592,8.583 c0.566,0.565,1.319,0.877,2.12,0.877c0.802,0,1.556-0.313," +
                "2.122-0.88l7.172-7.172C22.458,12.949,22.458,11.051,21.297,9.883z M19.883,12.703l-7.171," +
                "7.17c-0.377,0.379-1.033,0.379-1.416,0L3,11.585V3h8.586l8.295,8.295 C20.267,11.684,20.267," +
                "12.316,19.883,12.703z M7.01,6H7C6.448,6,6.005,6.448,6.005,7S6.458,8,7.01,8s1-0.448," +
                "1-1S7.562,6,7.01,6z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "tag-icon";
    }
}
