package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class PieChartIcon extends SVGIcon {

    public PieChartIcon() {
        super();
    }

    public PieChartIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.6,14.969c-0.508-0.215-1.096,0.022-1.311,0.531c-1.934,4.57-7.224,6.716-11.794,4.785 " +
                "C3.924,18.352,1.777,13.061,3.71,8.49C4.612,6.357,6.278,4.672,8.4,3.747c0.506-0.221,0.737-0.81," +
                "0.517-1.316 C8.696,1.925,8.108,1.693,7.6,1.914c-2.593,1.131-4.629,3.19-5.732,5.797c-2.362,5.587," +
                "0.261,12.054,5.848,14.416 c1.394,0.59,2.842,0.868,4.268,0.868c4.29,0,8.375-2.523,10.147-6.716C22.346," +
                "15.771,22.108,15.184,21.6,14.969z M12,1c-0.552,0-1,0.448-1,1v10c0,0.553,0.448,1,1,1h10c0.553,0," +
                "1-0.447,1-1C23,5.935,18.065,1,12,1z M13,11V3.055 c4.165,0.462,7.482,3.78,7.944,7.945H13z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "pie-chart-icon";
    }
}
