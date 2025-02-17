package org.gemseeker.sms.views.icons;

import javafx.scene.shape.SVGPath;

public class FilmIcon extends SVGIcon {

    public FilmIcon() {
        super();
    }

    public FilmIcon(double size) {
        super(size);
    }

    @Override
    protected SVGPath createShape() {
        SVGPath path = new SVGPath();
        path.setContent("M19.82,1H4.18C2.427,1,1,2.427,1,4.18v15.64C1,21.573,2.427,23,4.18,23h15.64c1.753,0," +
                "3.18-1.427,3.18-3.18V4.18 C23,2.427,21.573,1,19.82,1z M18,8h3v3h-3V8z M16,11H8V3h8V11z " +
                "M6,11H3V8h3V11z M3,13h3v3H3V13z M8,13h8v8H8V13z M18,13h3v3h-3V13z " +
                "M21,4.18V6h-3V3h1.82C20.471,3,21,3.529,21,4.18z M4.18,3H6v3H3V4.18C3,3.529,3.529,3,4.18,3z " +
                "M3,19.82V18h3v3H4.18 C3.529,21,3,20.471,3,19.82z M19.82,21H18v-3h3v1.82C21,20.471,20.471,21,19.82,21z");
        return path;
    }

    @Override
    protected String getIconStyleClass() {
        return "film-icon";
    }
}
