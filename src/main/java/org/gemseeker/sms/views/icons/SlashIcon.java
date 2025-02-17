package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class SlashIcon extends SVGIcon {

    public SlashIcon() {
        super();
    }

    public SlashIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19.792,19.755C21.773,17.765,23,15.023,23,12c0-6.065-4.935-11-11-11C8.975,1,6.232,2.228,4.241," +
                "4.211 C4.236,4.216,4.229,4.218,4.223,4.223S4.216,4.236,4.211,4.241C2.228,6.232,1,8.975,1,12c0,6.065," +
                "4.935,11,11,11 c3.023,0,5.765-1.227,7.755-3.208c0.007-0.006,0.016-0.008,0.022-0.015S19.786,19.762," +
                "19.792,19.755z M21,12 c0,2.122-0.742,4.071-1.975,5.611L6.389,4.975C7.929,3.742,9.878,3,12,3C16.963," +
                "3,21,7.038,21,12z M3,12 c0-2.122,0.742-4.071,1.975-5.611l12.636,12.636C16.071,20.258,14.122,21,12," +
                "21C7.038,21,3,16.963,3,12z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "slash-icon";
    }
}
