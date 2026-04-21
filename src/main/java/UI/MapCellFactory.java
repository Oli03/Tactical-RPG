package UI;

import Model.CombatStats;
import Model.Game;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class MapCellFactory {
    private final Background defaultBackground;
    private final Background playerBackground;
    private final Background enemyBackground;
    private final int cellSize;

    public MapCellFactory(int cellSize) {
        this.cellSize = cellSize;
        // Light Gray Default Background
        this.defaultBackground = new Background(new BackgroundFill(Color.web("#ddd"), CornerRadii.EMPTY, Insets.EMPTY));
        // Light Blue for Player Background
        this.playerBackground = new Background(new BackgroundFill(Color.web("#00aae4"), CornerRadii.EMPTY, Insets.EMPTY));
        // Light Red for Enemy Background
        this.enemyBackground = new Background(new BackgroundFill(Color.web("#FF746C"), CornerRadii.EMPTY, Insets.EMPTY));
    }

    public Background getDefaultBackground() {
        return defaultBackground;
    }

    public StackPane createCell(Game game, int x, int y) {
        // Set image of cell in the map
        StackPane cell = new StackPane();
        cell.setPrefSize(cellSize, cellSize);
        cell.setStyle("-fx-border-color: #444;");
        cell.setBackground(defaultBackground);

        CombatStats user = game.getEntityPosition(false).combatStats;
        CombatStats enemy = game.getEntityPosition(true).combatStats;

        if (game.isOccupiedByPlayer(x, y)) {
            // Blue Background for Player
            cell.setStyle("-fx-background-color: #0000ff;");
            return cell;
        }

        if (game.isOccupiedByEnemy(x, y)) {
            // Red Background for Enemy
            cell.setStyle("-fx-background-color: #ff0000;");
            return cell;
        }

        if (user != null && user.ownerZone(x, y)) {
            // Player playable zone
            cell.setBackground(playerBackground);
            return cell;
        }

        if (enemy != null && enemy.ownerZone(x, y)) {
            // Enemy playable zone
            cell.setBackground(enemyBackground);
        }

        return cell;
    }

    public boolean shouldBlink(Game game, int x, int y) {
        // Check if the cell is a playable zone of an entity
        if (game.isOccupiedByPlayer(x, y) || game.isOccupiedByEnemy(x, y)) {
            return false;
        }

        CombatStats user = game.getEntityPosition(false).combatStats;
        CombatStats enemy = game.getEntityPosition(true).combatStats;
        return (user != null && user.ownerZone(x, y)) || (enemy != null && enemy.ownerZone(x, y));
    }

    public Background getBlinkBackground(Game game, int x, int y) {
        CombatStats user = game.getEntityPosition(false).combatStats;
        if (user != null && user.ownerZone(x, y)) {
            return playerBackground;
        }

        return enemyBackground;
    }
}
