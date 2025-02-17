package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class RewindIcon extends SVGIcon {

    public RewindIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.75);
    }

    public RewindIcon(double size) {
        super(size, size * 0.75);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M11.439,4.102c-0.342-0.166-0.751-0.125-1.053,0.109l-9,7C1.143,11.4,1,11.691,1,12s0.143," +
                "0.6,0.386,0.789l9,7 C10.565,19.929,10.782,20,11,20c0.149,0,0.3-0.033,0.439-0.102C11.782,19.73,12," +
                "19.382,12,19v-7V5 C12,4.618,11.782,4.27,11.439,4.102z M10,16.955L3.629,12L10,7.044V16.955z " +
                "M22.439,4.102c-0.344-0.167-0.751-0.126-1.054,0.109l-9,7C12.143,11.4,12,11.691,12,12s0.143,0.6," +
                "0.386,0.789l9,7 C21.565,19.929,21.782,20,22,20c0.149,0,0.3-0.033,0.439-0.102C22.782,19.73,23," +
                "19.382,23,19V5C23,4.618,22.782,4.27,22.439,4.102z M21,16.955L14.629,12L21,7.044V16.955z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "rewind-icon";
    }
}
