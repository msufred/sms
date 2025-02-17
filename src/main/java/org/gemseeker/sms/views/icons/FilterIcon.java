package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FilterIcon extends SVGIcon {

    public FilterIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.85);
    }

    public FilterIcon(double size) {
        super(size, size * 0.85);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22.907,2.579C22.743,2.226,22.39,2,22,2H2C1.611,2,1.257,2.226,1.093,2.579c-0.164,0.353-0.108," +
                "0.77,0.144,1.066L9,12.826 V19c0,0.379,0.214,0.725,0.553,0.895l4,2C13.694,21.965,13.847,22,14,22c0.183," +
                "0,0.365-0.05,0.525-0.149 C14.82,21.668,15,21.347,15,21v-8.174l7.764-9.181C23.015,3.349,23.071,2.932," +
                "22.907,2.579z M13.236,11.814 C13.084,11.995,13,12.224,13,12.46v6.922l-2-1V12.46c0-0.236-0.084-0.465-" +
                "0.236-0.646L4.155,4h15.689L13.236,11.814z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "filter-icon";
    }
}
