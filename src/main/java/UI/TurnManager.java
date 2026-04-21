package UI;

import Model.CombatStats;
import Model.Game;
import Model.Position;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class TurnManager {
    private final Game game;
    private final Label statusLabel;
    private final MapView mapView;
    private final BorderPane root;
    private final GameOverHandler gameOverHandler;

    public TurnManager(Game game, Label statusLabel, MapView mapView, BorderPane root) {
        this.game = game;
        this.statusLabel = statusLabel;
        this.mapView = mapView;
        this.root = root;
        this.gameOverHandler = new GameOverHandler(game, statusLabel, root);
    }

    public void endTurn(Button attackBtn, Button continueBtn) {
        // End turn of the player. Time to wait the enemy's turn
        attackBtn.setDisable(true);
        continueBtn.setDisable(true);
        Position currentPos = game.getEntityPosition(false);
        CombatStats mainCombatStats = currentPos.combatStats;
        mainCombatStats.switchTurn();
    }

    public PauseTransition createEnemyTurnPause(Button attackBtn, Button continueBtn) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1.0));
        pause.setOnFinished(ev -> {
            if (game.isFinished()) {
                // The player wins
                gameOverHandler.handle();
                return;
            }

            // Starts the enemy's turn
            Position enemyPos = game.getEntityPosition(true);
            CombatStats enemyCombatStats = enemyPos.combatStats;
            if (enemyCombatStats == null) return;
            enemyCombatStats.switchTurn();

            game.moveEnemy();
            mapView.redraw(false);

            int damage = game.attack(false);
            if (damage >= 0) {
                statusLabel.setText("Enemy deals " + damage + " damage");
            }

            if (game.isFinished()) {
                // The enemy wins
                gameOverHandler.handle();
                return;
            }

            // End of the enemy's turn
            enemyCombatStats.switchTurn();
            CombatStats mainCombatStats = game.getEntityPosition(false).combatStats;
            if (mainCombatStats == null) return;
            mainCombatStats.switchTurn();

            // Reactivate the buttons for the player
            javafx.application.Platform.runLater(() -> {
                attackBtn.setDisable(false);
                continueBtn.setDisable(false);
                mapView.redraw(true);
                root.requestFocus();

                if (game.isFinished()) gameOverHandler.handle();
            });
        });
        return pause;
    }
}
