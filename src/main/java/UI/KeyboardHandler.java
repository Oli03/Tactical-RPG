package UI;

import Model.CombatStats;
import Model.Game;
import Model.Position;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public class KeyboardHandler {
    private final Game game;
    private final Label statusLabel;
    private final BorderPane root;
    // callback called after a successful player move to update the UI
    private final Runnable onMoved;
    // callback called when the game is finished, to show the end game screen
    private final Runnable onFinished;

    public KeyboardHandler(Game game, Label statusLabel, BorderPane root,
                           Runnable onMoved) {
        this.game = game;
        this.statusLabel = statusLabel;
        this.root = root;
        this.onMoved = onMoved;
        // initialize onFinished with an empty lambda to avoid null checks later
        this.onFinished = () -> {};
    }

    public void setup() {
        root.setOnKeyPressed(e -> {
            if (game.isFinished()) {
                // if the game is already finished, just call the onFinished callback to show the end game screen
                onFinished.run();
                return;
            }

            Position currentPos = game.getEntityPosition(false);
            CombatStats mainCombatStats = currentPos.combatStats;

            // check if it's the player's turn, if not, ignore the key press
            if (!mainCombatStats.hisTurn()) return;

            // determine the direction based on the arrow keys
            KeyCode code = e.getCode();
            int directionX = 0, directionY = 0;

            if (code == KeyCode.UP) directionY = -1;
            else if (code == KeyCode.DOWN) directionY = 1;
            else if (code == KeyCode.LEFT) directionX = -1;
            else if (code == KeyCode.RIGHT) directionX = 1;
            else return;

            // consume the event to prevent it from being handled by other handlers
            e.consume();

            int x = currentPos.getX();
            int y = currentPos.getY();

            if (!game.movePlayer(x + directionX, y + directionY)) {
                statusLabel.setText("Invalid move");
                return;
            }

            statusLabel.setText("Move to " + (x + directionX) + "," + (y + directionY));
            // time to update the UI
            onMoved.run();

            if (game.isFinished()) onFinished.run();
        });
    }
}
