package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class NavigationIcon extends SVGIcon {

    public NavigationIcon() {
        super();
    }

    public NavigationIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M22.707,1.293c-0.299-0.3-0.754-0.377-1.135-0.197l-19,9c-0.381,0.181-0.608," +
                "0.582-0.567,1.002s0.343,0.769,0.752,0.872 l7.418,1.854l1.854,7.418c0.103,0.41,0.451," +
                "0.711,0.872,0.753C12.935,21.998,12.968,22,13,22c0.384,0,0.737-0.221,0.903-0.572l9-19 " +
                "C23.085,2.046,23.006,1.592,22.707,1.293z M13.292,18.047l-1.322-5.289c-0.09-0.358-" +
                "0.37-0.638-0.728-0.728l-5.29-1.322 l13.944-6.605L13.292,18.047z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "navigation-icon";
    }
}
