/**
 * TilesGame is a JavaFX application for tile puzzle game
 *
 * @author Swastika Dawadi
 */

package TilesGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TilesGame extends Application {
    private final int PANEL_WIDTH = 800; // this is the width of the tile game
    private final int PANEL_HEIGHT = 600; // this is the height of the tile game

    private static final Color[] GAME_COLORS = {  // Available colors
            Color.RED, Color.BLUE,
            Color.GREEN, Color.YELLOW,
            Color.PURPLE
    };

    public void start(Stage primaryStage) {
        int rows = 5;
        int columns = 6;
        int totalTiles = rows * columns; // total number of tiles is 30
        int tileSize = 100;

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Created colors of the tile
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN,
                Color.YELLOW, Color.PURPLE};
        int numColors = colors.length;

        // making sure there is even number of colors, so we can clear all the tiles
        if (totalTiles % (2 * numColors) != 0) {
            throw new IllegalArgumentException("Board size must allow even color pairs!");
        }

        // Generate colors for each layer
        List<Color> outerColors = generateLayerColors(colors, totalTiles);
        List<Color> middleColors = generateLayerColors(colors, totalTiles);
        List<Color> innerColors = generateLayerColors(colors, totalTiles);

        // Create and add tiles
        List<Tile> tiles = new ArrayList<>();
        TileGameController controller = new TileGameController();
        int index = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Tile tile = new Tile(
                        outerColors.get(index),
                        middleColors.get(index),
                        innerColors.get(index),
                        tileSize

                );
                tiles.add(tile);
                grid.add(tile.getView(), col, row);
                tile.setClickEvent(controller);
                index++;
            }
        }

        controller.setTiles(tiles);
        controller.setScoreBoard(grid);

        Scene scene = new Scene(grid, PANEL_WIDTH, PANEL_HEIGHT);
        primaryStage.setTitle("Tiles Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Generates a layer's colors with even distribution
    private List<Color> generateLayerColors(Color[] colors, int totalTiles) {
        List<Color> layer = new ArrayList<>();
        int pairsPerColor = totalTiles / (colors.length * 2); // Each color appears 2*pairs

        for (Color color : colors) {
            for (int i = 0; i < pairsPerColor * 2; i++) { // Add even occurrences
                layer.add(color);
            }
        }

        Collections.shuffle(layer); // Shuffle for randomness
        return layer.subList(0, totalTiles); // Truncate to exact tile count
    }

    public static void main(String[] args) {
        launch(args);
    }
}