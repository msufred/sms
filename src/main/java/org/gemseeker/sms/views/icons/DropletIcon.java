package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class DropletIcon extends SVGIcon {

    public DropletIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public DropletIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M18.367,7.643l-5.66-5.66C12.52,1.795,12.265,1.69,12,1.69h0c-0.265,0-0.52,0.106-0.707," +
                "0.293L5.657,7.629 C5.651,7.635,5.645,7.641,5.639,7.646c-3.508,3.511-3.506,9.221,0.004,12.728c1.7," +
                "1.699,3.959,2.635,6.362,2.635 c2.405,0,4.666-0.937,6.366-2.638C21.879,16.86,21.877,11.15,18.367," +
                "7.643z M16.957,18.957c-1.322,1.323-3.081,2.052-4.952,2.052 c-1.869,0-3.626-0.728" +
                "-4.948-2.049c-2.726-2.724-2.732-7.154-0.018-9.885c0.006-0.006,0.012-0.012,0.018-0.018L12," +
                "4.105l4.953,4.953 C19.684,11.786,19.685,16.227,16.957,18.957z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "droplet-icon";
    }
}
