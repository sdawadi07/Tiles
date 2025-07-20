package TilesGame;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.List;

/**
 * Manages the tiles matching, scoring, and game management
 */
public class TileGameController {
    private final int PANEL_SPACE = 10;

    private final int row_index = 0;

    private final int column_index = 6;

    private int currentCombo = 0;
    private int longestCombo = 0;
    private Tile firstTile = null;
    private Text currentComboText;
    private Text longestComboText;
    private List<Tile> tiles;

    public TileGameController() {
        currentComboText = new Text("Current Combo: " + currentCombo);
        longestComboText = new Text("Longest Combo: " + longestCombo);
    }

    public void handleTileClick(Tile clickedTile) {
        if (firstTile == null) {
            firstTile = clickedTile;
            highlightTile(firstTile, true);
        } else {
            highlightTile(firstTile, false);
            boolean anyRemoved = processTiles(firstTile, clickedTile);
            updateCombo(anyRemoved);
            checkGameOver();

            if (anyRemoved) {
                firstTile = clickedTile;
                highlightTile(firstTile, true);
            } else {
                firstTile = null;
            }
        }
    }

    // Rest of the classes remains unchanged
    private boolean processTiles(Tile tile1, Tile tile2) {
        boolean anyRemoved = false;

        if (tile1.isOuterPresent() && tile2.isOuterPresent() &&
                tile1.getOuterColor().equals(tile2.getOuterColor())) {
            tile1.removeOuter();
            tile2.removeOuter();
            anyRemoved = true;
        }

        if (tile1.isMiddlePresent() && tile2.isMiddlePresent() &&
                tile1.getMiddleColor().equals(tile2.getMiddleColor())) {
            tile1.removeMiddle();
            tile2.removeMiddle();
            anyRemoved = true;
        }

        if (tile1.isInnerPresent() && tile2.isInnerPresent() &&
                tile1.getInnerColor().equals(tile2.getInnerColor())) {
            tile1.removeInner();
            tile2.removeInner();
            anyRemoved = true;
        }

        return anyRemoved;
    }

    private void updateCombo(boolean anyRemoved) {
        if (anyRemoved) {
            currentCombo++;
            longestCombo = Math.max(currentCombo, longestCombo);
        } else {
            currentCombo = 0;
        }
        updateScore();
    }

    private void highlightTile(Tile tile, boolean highlight) {
        tile.getView().setStyle(highlight ? "-fx-border-color: yellow; -fx-border-width: 3;" : "");
    }

    private void updateScore() {
        currentComboText.setText("Current Combo: " + currentCombo);
        longestComboText.setText("Longest Combo: " + longestCombo);
    }

    private void checkGameOver() {
        boolean allCleared = tiles.stream().noneMatch(tile ->
                tile.isOuterPresent() || tile.isMiddlePresent() || tile.isInnerPresent()
        );

        if (allCleared) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText("Congratulations!");
                alert.setContentText("All the tiles have been cleared!");
                alert.showAndWait();
            });
        }
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public void setScoreBoard(GridPane grid) {
        VBox scoreBox = new VBox(PANEL_SPACE, currentComboText, longestComboText);
        currentComboText.setStyle("-fx-font-size: 14;");
        longestComboText.setStyle("-fx-font-size: 14;");
        grid.add(scoreBox, column_index, row_index);
    }
}