package UI;

import Model.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Set game logic
        Game game = new Game(5, 5);

        // Set UI
        GameController controller = new GameController(game);

        Scene scene = new Scene(controller.getRoot(), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Tactical RPG");
        stage.show();

        // Make sure the root has focus once the window appears and route the Scene's keys to the controller (in case the root doesn't have focus)
        controller.getRoot().requestFocus();
        scene.setOnKeyPressed(controller.getRoot().getOnKeyPressed());
    }

    public static void main(String[] args) {
        launch();
    }
}
