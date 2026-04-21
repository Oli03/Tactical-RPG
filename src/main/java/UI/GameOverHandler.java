package UI;

import Model.Game;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GameOverHandler {
    private final Game game;
    private final Label statusLabel;
    private final BorderPane root;

    public GameOverHandler(Game game, Label statusLabel, BorderPane root) {
        this.game = game;
        this.statusLabel = statusLabel;
        this.root = root;
    }

    public void handle() {
        root.setDisable(true);

        boolean enemyDead = (game.enemyEntity.position.combatStats == null);
        boolean playerDead = (game.playerEntity.position.combatStats == null);

        String message = "";
        String color = "";
        if (enemyDead && !playerDead) {
            message = "¡You win!";
            color = "green";
        } else if (playerDead && !enemyDead) {
            message = "Game Over!";
            color = "red";
        }

        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
    }
}
