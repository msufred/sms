package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ShoppingCartIcon extends SVGIcon {

    public ShoppingCartIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.9);
    }

    public ShoppingCartIcon(double size) {
        super(size, size * 0.9);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M7,21a2,2 0 1,0 4,0a2,2 0 1,0 -4,0z M18,21a2,2 0 1,0 4,0a2,2 0 1,0 -4,0z M23.771," +
                "5.362C23.58,5.133,23.298,5,23,5H6.82L5.98,0.804C5.887,0.336,5.477,0,5,0H1C0.448,0,0,0.448," +
                "0,1s0.448,1,1,1h3.18 l0.832,4.153c0.004,0.03,0.01,0.06,0.018,0.088l1.67,8.343c0.279,1.409," +
                "1.511,2.417,2.924,2.417C9.642,17.001,9.661,17,9.68,17 h9.701c0.02,0,0.039,0,0.059,0c1.429," +
                "0,2.661-1.007,2.943-2.423l1.6-8.39C24.038,5.895,23.961,5.592,23.771,5.362z M20.42,14.194 " +
                "C20.325,14.67,19.868,14.992,19.4,15H9.661c-0.503-0.004-0.906-0.329-1-0.807L7.221,7h14.57L20.42,14.194z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "shopping-cart-icon";
    }
}
