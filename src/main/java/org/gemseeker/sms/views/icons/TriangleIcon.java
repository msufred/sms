package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class TriangleIcon extends SVGIcon {

    public TriangleIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.9);
    }

    public TriangleIcon(double size) {
        super(size, size * 0.9);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M23.037,17.486L14.564,3.341c-0.549-0.904-1.508-1.444-2.566-1.444c-0.547,0-1.085,0.151-1.553," +
                "0.435 c-0.411,0.249-0.76,0.598-1.013,1.015L0.954,17.5c-0.827,1.433-0.334,3.271,1.098,4.099C2.497," +
                "21.855,3.004,21.994,3.53,22h16.94 c0.004,0,0.007,0,0.011,0c1.654-0.018,2.985-1.378,2.968-3.033C23.442," +
                "18.453,23.303,17.946,23.037,17.486z M20.464,20H3.541 c-0.172-0.002-0.341-0.048-0.489-0.134c-0.478-" +
                "0.275-0.642-0.889-0.375-1.353l8.467-14.135c0.083-0.137,0.2-0.253,0.337-0.336 c0.157-0.095,0.335-0.146," +
                "0.517-0.146c0.353,0,0.673,0.18,0.854,0.477L21.313,18.5c0.086,0.149,0.133,0.318,0.135,0.489 C21.454," +
                "19.539,21.013,19.991,20.464,20z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "triangle-icon";
    }
}
