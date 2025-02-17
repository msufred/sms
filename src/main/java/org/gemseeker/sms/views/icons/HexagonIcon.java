package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class HexagonIcon extends SVGIcon {

    public HexagonIcon() {
        super(DEFAULT_SIZE * 0.85, DEFAULT_SIZE);
    }

    public HexagonIcon(double size) {
        super(size * 0.85, size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M20.496,5.402L13.5,1.404c-0.91-0.527-2.094-0.524-2.996-0.002L3.5,5.404C2.576,5.938,2,6.932," +
                "2,8v8.001 c0.001,1.067,0.576,2.062,1.504,2.598l6.997,3.998c0.455,0.263,0.973,0.401,1.499,0.401c0.525," +
                "0,1.044-0.139,1.496-0.399 l7.005-4.003C21.424,18.063,21.998,17.068,22,16V7.999C21.998,6.932,21.424," +
                "5.938,20.496,5.402z M20,15.999 c-0.001,0.355-0.192,0.688-0.496,0.863l-7.003,4.002c-0.305,0.174-0.694," +
                "0.176-1.005-0.002L4.5,16.865C4.192,16.687,4,16.354,4,16V8 c0-0.355,0.192-0.687,0.496-0.862L11.5," +
                "3.136c0.151-0.087,0.324-0.134,0.5-0.134s0.348,0.046,0.504,0.136L19.5,7.136 C19.808,7.314,19.999," +
                "7.646,20,8V15.999z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "hexagon-icon";
    }
}
