package UI;

import Model.Game;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MapView extends GridPane {
    private final Game game;
    private final CellBlinkAnimator blinkAnimator;
    private final MapCellFactory cellFactory;
    private final int cellSize;

    public MapView(Game game) {
        this.game = game;
        this.cellSize = 60;
        this.cellFactory = new MapCellFactory(cellSize);
        this.blinkAnimator = new CellBlinkAnimator(cellFactory.getDefaultBackground(), Duration.millis(500));

        // Configure grid pane space
        setHgap(2);
        setVgap(2);
        setStyle("-fx-padding: 8;");
    }

    public void redraw(boolean sameTurn) {
        // Stop temporary the blinking cells
        blinkAnimator.stopAll();
        getChildren().clear();
        getColumnConstraints().clear();
        getRowConstraints().clear();

        if (!sameTurn) {
            // Recalculates entities playable zone
            game.spawner.calculateEntityZone(game.playerEntity, game.getEntityPosition(false).getX(), game.getEntityPosition(false).getY());
            game.spawner.calculateEntityZone(game.enemyEntity, game.getEntityPosition(true).getX(), game.getEntityPosition(true).getY());
        }

        int width = game.getWidth();
        int height = game.getHeight();

        // Add constraints for the size of the cells
        for (int x = 0; x < width; x++) {
            getColumnConstraints().add(new ColumnConstraints(cellSize));
        }
        for (int y = 0; y < height; y++) {
            getRowConstraints().add(new RowConstraints(cellSize));
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Creation of a new cell
                StackPane cell = cellFactory.createCell(game, x, y);
                if (cellFactory.shouldBlink(game, x, y)) {
                    // Set blinking animation for the cell
                    blinkAnimator.startBlinking(cell, cellFactory.getBlinkBackground(game, x, y));
                }
                add(cell, x, y);
            }
        }
    }
}
