package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class ZapIcon extends SVGIcon {

    public ZapIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public ZapIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.905,9.576C21.741,9.225,21.388,9,21,9h-7.867l0.859-6.876c0.055-0.44-0.187-" +
                "0.865-0.594-1.042 c-0.406-0.175-0.882-0.064-1.167,0.277l-10,12c-0.249,0.299-0.302,0.714-" +
                "0.138,1.064C2.259,14.775,2.612,15,3,15h7.867l-0.859,6.876 c-0.055,0.44,0.187,0.864,0.594," +
                "1.041C10.73,22.973,10.866,23,11,23c0.291,0,0.574-0.127,0.769-0.359l10-12 C22.017,10.342," +
                "22.07,9.927,21.905,9.576z M12.416,18.739l0.577-4.615c0.036-0.284-0.053-0.57-0.242-0.786 " +
                "C12.56,13.123,12.287,13,12,13H5.135l6.449-7.74l-0.577,4.616c-0.036,0.285,0.053,0.571," +
                "0.243,0.786C11.44,10.877,11.713,11,12,11 h6.865L12.416,18.739z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "zap-icon";
    }
}
