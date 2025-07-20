package TilesGame;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * This is a tile composed of three different shapes
 * The outermost is the rectangle, middle circle and innermost triangle
 *
 */

public class Tile {
    private StackPane view;
    private Rectangle outerRectangle;
    private Circle middleCircle;
    private Polygon innerTriangle;

    // It tracks whether all the tiles are removed correctly
    private boolean outerRemoved = false;
    private boolean middleRemoved = false;
    private boolean innerRemoved = false;

    public Tile(Color outerColor, Color middleColor, Color innerColor, int size) {
        // Outermost layer which is a rectangle

        outerRectangle = new Rectangle(size * 0.9, size * 0.8, outerColor);
        outerRectangle.setStroke(Color.DARKGRAY);
        outerRectangle.setStrokeWidth(4);

        // Middle layer which is a circle
        double middleRadius = size * 0.3;
        middleCircle = new Circle(middleRadius, middleColor);
        middleCircle.setStroke(Color.DARKBLUE);
        middleCircle.setStrokeWidth(3);

        // Inner layer which is a triangle
        double innerSize = size * 0.4;
        innerTriangle = new Polygon();
        innerTriangle.getPoints().addAll(
                0.0, -innerSize/2,        // Top point
                innerSize/2, innerSize/2, // Right point
                -innerSize/2, innerSize/2 // Left point
        );
        innerTriangle.setFill(innerColor);
        innerTriangle.setStroke(Color.DARKCYAN);
        innerTriangle.setStrokeWidth(2);

        //Assemles layers in Stackpane, outer first, middle, and inner.
        view = new StackPane(outerRectangle, middleCircle, innerTriangle);
    }

    // Methods to remove elements
    public void removeOuter() {
        if (!outerRemoved) {
            outerRectangle.setVisible(false);
            outerRemoved = true;
        }
    }

    public void removeMiddle() {
        if (!middleRemoved) {
            middleCircle.setVisible(false);
            middleRemoved = true;
        }
    }

    public void removeInner() {
        if (!innerRemoved) {
            innerTriangle.setVisible(false);
            innerRemoved = true;
        }
    }

    // Getters for colors and presence
    public Color getOuterColor() { return (Color) outerRectangle.getFill(); }
    public Color getMiddleColor() { return (Color) middleCircle.getFill(); }
    public Color getInnerColor() { return (Color) innerTriangle.getFill(); }

    public boolean isOuterPresent() { return !outerRemoved; }
    public boolean isMiddlePresent() { return !middleRemoved; }
    public boolean isInnerPresent() { return !innerRemoved; }

    // Set click event on the entire tile
    public void setClickEvent(TileGameController controller) {
        view.setOnMouseClicked(event -> controller.handleTileClick(this));
    }
    // return the visual representations of the tiles

    public StackPane getView() { return view; }
}