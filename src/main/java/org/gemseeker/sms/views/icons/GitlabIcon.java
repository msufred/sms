package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class GitlabIcon extends SVGIcon {

    public GitlabIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE * 0.95);
    }

    public GitlabIcon(double size) {
        super(size, size * 0.95);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M23.947,13.13L20.248,1.893c-0.07-0.232-0.199-0.444-0.394-0.631c-0.528-0.481-1.394-0.489-1.932,0.002 " +
                "c-0.162,0.149-0.289,0.336-0.384,0.587L15.323,8.67H8.676L6.468,1.893C6.398,1.662,6.27,1.451,6.074,1.262 " +
                "C5.547,0.78,4.675,0.779,4.147,1.26c-0.162,0.147-0.29,0.333-0.388,0.591L0.1,13.137c-0.25,0.759,0.014," +
                "1.587,0.662,2.062 l10.65,7.74c0.175,0.128,0.381,0.191,0.588,0.191c0.206,0,0.413-0.063,0.588-0.191l10.605-7.709 " +
                "C23.881,14.786,24.188,13.958,23.947,13.13z M12,20.894l-9.964-7.242l3.071-9.48L7,9.979c0.134,0.412,0.518,0.69,0.951,0.69h8.1 " +
                "c0.434,0,0.817-0.279,0.951-0.691l1.886-5.805l1.896,5.836l1.221,3.613L12,20.894z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "gitlab-icon";
    }
}
