package Presentation;

import Application.App;
import Business.SongPlayer;
import Business.SongToPlay;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GameOver extends BorderPane {
    Label gameOver;
    Label score;
    Button retry;
    Button exit;
    Button returnToMenu;
    SongToPlay songPlayed;
    SongPlayer player;

    public GameOver(SongPlayer player) {
        this.player = player;
        songPlayed = player.getSong();
        this.setId("game-over-pane");

        gameOver = new Label("Game Over");
        gameOver.setId("game-over");
        score = new Label("Your Score: " + LineAnimationPane.score);
        score.setId("score");
        retry = new Button("Retry");
        exit = new Button("Exit");
        returnToMenu = new Button("Return to Menu");
        player.startMenuSong();

        VBox vbox = new VBox();
        vbox.getChildren().addAll(gameOver, score, retry, exit, returnToMenu);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);

        this.setCenter(vbox);
        initialize();
    }
    private void initialize() {
        retry.setOnAction(e -> {
            player.loadSong(songPlayed);
            LineAnimationPane.score = 0;
            App.switchRoot("GameView",player);
        });
        exit.setOnAction(e -> {
            System.exit(0);
        });
        returnToMenu.setOnAction(e -> {
            LineAnimationPane.score = 0;
            App.switchRoot("StartView", player);
        });
    }
}
