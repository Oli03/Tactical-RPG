package UI;

import Model.Game;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ActionPanel {
    private final HBox panel;
    private final Button attackBtn;
    private final Button continueBtn;

    public ActionPanel(Game game, Label statusLabel, TurnManager turnManager) {
        this.attackBtn = new Button("Attack");
        this.continueBtn = new Button("Continue");

        // Place buttons in horizontal
        this.panel = new HBox(8);
        // Adds internal spacing so the buttons are not flush with the panel edges
        panel.setPadding(new Insets(8));

        attackBtn.setOnAction(e -> {
            // Deal damage
            int damage = game.attack(true);

            if (damage >= 0) {
                // the enemy is still alive
                statusLabel.setText("You deal " + damage + " damage");
                // Turn ended
                turnManager.endTurn(attackBtn, continueBtn);
            } else {
                // No enemy close
                statusLabel.setText("No enemy in range to attack");
            }

            // The turn of the enemy begins. The player waits
            PauseTransition pause = turnManager.createEnemyTurnPause(attackBtn, continueBtn);
            pause.play();
        });

        continueBtn.setOnAction(e -> {
            // The player can't interact while the enemy is taking its turn
            attackBtn.setDisable(true);
            continueBtn.setDisable(true);
            turnManager.endTurn(attackBtn, continueBtn);
            PauseTransition pause = turnManager.createEnemyTurnPause(attackBtn, continueBtn);
            pause.play();
        });

        // Add buttons to the panel
        panel.getChildren().addAll(attackBtn, continueBtn);
    }

    public HBox getPanel() {
        return panel;
    }
}
