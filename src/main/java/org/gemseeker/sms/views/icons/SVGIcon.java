package org.gemseeker.sms.views.icons;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public abstract class SVGIcon extends Region {

    protected static final double DEFAULT_SIZE = 14;

    public SVGIcon() {
        this(DEFAULT_SIZE);
    }

    public SVGIcon(double size) {
        this (size, size);
    }

    public SVGIcon(double width, double height) {
        super();
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);
        getStyleClass().add("feather-icon");
        getStyleClass().add(getIconStyleClass());
        setShape(createShape());
        setBackground(new Background(new BackgroundFill(Color.gray(0.25), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    protected abstract SVGPath createShape();

    protected abstract String getIconStyleClass();
}
