package Presentation;

import Application.App;
import Business.SongPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LineAnimationPane extends Pane {
    public static int score = 0;

    private static final int WINDOW_WIDTH = 1700;
    private static final int TARGET_X = 110;
    private final Map<Rectangle, Long> activeRectangles = new HashMap<>();

    private long lastNoteTime = 0;
    private long startTime;
    private boolean checking = true;

    public LineAnimationPane(SongPlayer player) {
        this.startTime = System.currentTimeMillis(); // Store when the animation starts

        String filename = player.getSong().getTextFileForNotes();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(lastNoteTime == Long.MIN_VALUE) break;
                String[] parts = line.split(",");
                if (parts.length != 4) continue;

                int time = Integer.parseInt(parts[0]);
                int height = Integer.parseInt(parts[1]);
                int length = Integer.parseInt(parts[2]);
                int duration = Integer.parseInt(parts[3]);

                long absoluteNoteTime = startTime + time + duration;
                lastNoteTime = Math.max(lastNoteTime, absoluteNoteTime);

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time), e -> {
                    createAndAnimateRectangle(height, length, duration);
                }));
                timeline.play();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        startCompletionMonitor(player);
        setOnMousePressed(this::handleMouseClick);
    }

    private void createAndAnimateRectangle(int height, int length, int duration) {
        Rectangle rect = new Rectangle(WINDOW_WIDTH, height - 40, length, 40);
        rect.setId("rect");
        rect.setArcWidth(20);
        rect.setArcHeight(20);

        Random rand = new Random();
        Color randomColor = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        rect.setFill(randomColor);
        getChildren().add(rect);

        activeRectangles.put(rect, System.currentTimeMillis());

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(30), e -> {
            rect.setX(rect.getX() - (WINDOW_WIDTH / (double) duration) * 16);

            if (rect.getX() + rect.getWidth() < 0) {
                getChildren().remove(rect);
                activeRectangles.remove(rect);
            }
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void handleMouseClick(MouseEvent event) {
        double mouseY = event.getY();
        long currentTime = System.currentTimeMillis();

        for (Rectangle rect : activeRectangles.keySet()) {
            if (rect.getX() <= TARGET_X && rect.getX() + rect.getWidth() >= TARGET_X) {
                if (mouseY >= rect.getY() && mouseY <= rect.getY() + rect.getHeight()) {
                    long elapsedTime = currentTime - activeRectangles.get(rect);
                    int points = (int) (elapsedTime / 100) * 2;
                    score += points;
                }
            }
        }
    }

    private void startCompletionMonitor(SongPlayer player) {
        Timeline monitor = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            if (System.currentTimeMillis() >= lastNoteTime && activeRectangles.isEmpty() && checking) {
                App.switchRoot("GameOverView", player);
                checking = false;
            }
        }));
        monitor.setCycleCount(Timeline.INDEFINITE);
        monitor.play();
    }
}
