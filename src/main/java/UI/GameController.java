package UI;

import Model.Game;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GameController {
    // Base layout divided into 5 regions (top, bottom, sides, and an expandable center)
    private final BorderPane root;
    // Map of the game
    private final MapView mapView;

    public GameController(Game game) {
        this.root = new BorderPane();
        this.mapView = new MapView(game);

        // Label to show the events in the game
        Label statusLabel = new Label("Game Status");

        // Manages the turns of the game and updates the UI accordingly
        TurnManager turnManager = new TurnManager(game, statusLabel, mapView, root);

        // Panel with buttons for actions
        ActionPanel actionPanel = new ActionPanel(game, statusLabel, turnManager);

        // Handles keyboard input for player's move
        KeyboardHandler keyboardHandler = new KeyboardHandler(
                game, statusLabel, root,
                () -> mapView.redraw(true)
        );

        // Top section of the layout to display game status
        VBox topBox = new VBox(statusLabel);
        topBox.setPadding(new Insets(8));
        root.setTop(topBox);

        // Center section to display the game map
        root.setCenter(mapView);

        // Bottom section for action buttons
        root.setBottom(actionPanel.getPanel());

        // Activate keyboard input handling
        keyboardHandler.setup();

        // Show map
        mapView.redraw(false);

        // Make sure the root pane can receive keyboard focus
        Platform.runLater(() -> {
            root.setFocusTraversable(true);
            root.requestFocus();
        });
    }

    public BorderPane getRoot() {
        return root;
    }
}
