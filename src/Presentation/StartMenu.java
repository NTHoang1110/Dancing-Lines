package Presentation;

import Application.App;
import Business.SongPlayer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.*;

public class StartMenu extends BorderPane {
    Label gameTitle;
    Button start;
    Button exit;
    SongPlayer songPlayer;
    MediaView mediaView;

    public StartMenu(SongPlayer player) {
        songPlayer = player;
        gameTitle = new Label("Dancing Lines");
        gameTitle.setId("gameTitle");
        start = new Button("Start");
        start.getStyleClass().add("button");
        start.setId("start");
        exit = new Button("Exit");
        exit.getStyleClass().add("button");
        exit.setId("exit");

        VBox vBox = new VBox(gameTitle, start, exit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);

        mediaView = App.createBackgroundVideo("src/Assets/Dance.mp4");

        StackPane stack = new StackPane(mediaView,vBox);
        stack.setAlignment(Pos.CENTER);
        this.setCenter(stack);
        initialize();
    }

    public void initialize(){
        start.setOnAction(event -> {
            App.switchRoot("MusicSelectionView", songPlayer);
        });
        exit.setOnAction(event -> {
            System.exit(0);
        });
    }

}