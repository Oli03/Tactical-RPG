package UI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CellBlinkAnimator {
    private final Background defaultBackground;
    // The interval in milliseconds at which the cell should blink or not
    private final Duration blinkInterval;
    // Store animation of blinking
    private final List<Timeline> timelines;

    public CellBlinkAnimator(Background defaultBackground, Duration blinkInterval) {
        this.defaultBackground = defaultBackground;
        this.blinkInterval = blinkInterval;
        this.timelines = new ArrayList<>();
    }

    public void stopAll() {
        // Delete all animation of blinking
        for (Timeline timeline : timelines) {
            timeline.stop();
        }
        timelines.clear();
    }

    public void startBlinking(StackPane cell, Background activeBackground) {
        // Mutable state used by the lambda to alternate between the "on" and "off" backgrounds.
        final AtomicBoolean state = new AtomicBoolean(true);

        // execute the action event every blinkInterval milliseconds to toggle the cell background
        KeyFrame keyFrame = new KeyFrame(blinkInterval, e -> {
            if (state.get()) {
                cell.setBackground(defaultBackground);
            } else {
                cell.setBackground(activeBackground);
            }
            state.set(!state.get());
        });

        // Create a new animation of blinking
        Timeline timeline = new Timeline(keyFrame);
        // set infinite loop
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        timelines.add(timeline);
    }
}
