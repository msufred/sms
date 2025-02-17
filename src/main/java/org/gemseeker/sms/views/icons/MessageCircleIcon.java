package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class MessageCircleIcon extends SVGIcon {

    public MessageCircleIcon() {
        super();
    }

    public MessageCircleIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M21.998,10.945C21.731,6.113,17.887,2.268,13,2h-0.521c-1.463,0-2.925," +
                "0.348-4.226,1.005C5.015,4.624,3.001,7.878,3,11.498 c-0.003,1.329,0.28,2.657," +
                "0.824,3.869l-1.772,5.317c-0.12,0.359-0.026,0.756,0.242,1.023C2.483,21.897," +
                "2.739,22,3,22 c0.106,0,0.213-0.017,0.316-0.052l5.318-1.772C9.837,20.716," +
                "11.159,21,12.5,21c3.621-0.001,6.876-2.014,8.492-5.249 c0.664-1.313,1.012-2.783," +
                "1.008-4.251L21.998,10.945z M20,11.503c0.004,1.156-0.271,2.313-0.795,3.35 c-1.277," +
                "2.558-3.847,4.146-6.73,4.147c-1.149,0-2.298-0.274-3.324-0.793c-0.237-0.119-" +
                "0.515-0.143-0.768-0.056l-3.803,1.268 l1.268-3.803c0.084-0.253,0.064-0.529-" +
                "0.056-0.768C5.271,13.817,4.997,12.66,5,11.5c0.001-2.859,1.59-5.428,4.151-" +
                "6.708 C10.177,4.274,11.328,4,12.5,4l0.445-0.001C16.758,4.209,19.791,7.243,20,11V11.503z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "message-circle-icon";
    }
}
