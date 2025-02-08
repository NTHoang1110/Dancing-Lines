package Application;

/**
 @author    Tin Hoang Nguyen
Notiz: Nur Twinkle Twinkle little star funktioniert.
 **/

import Business.SongPlayer;
import Presentation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.*;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.util.Duration;

import java.io.File;

public class App extends Application{
    public static Scene scene;
    public static Pane root;
    @Override
    public void start(Stage stage) {
        SongPlayer player = new SongPlayer();
        root = new StartMenu(player);
        scene = new Scene(root,1700,960);
        scene.getStylesheets().add("Presentation/Style.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void switchRoot(String name, SongPlayer player){
        Parent newRoot = null;

        switch (name){
            case "StartView":
                newRoot = new StartMenu(player);
                break;
            case "GameView":
                newRoot = new GameViewController(player).getMainView();
                break;
            case "MusicSelectionView":
                newRoot = new MusicSelectionPane(player);
                break;
            case "GameOverView":
                newRoot = new GameOver(player);
                break;
        }

        if (newRoot != null) {
            newRoot.setOpacity(0);
            scene.setRoot(newRoot);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newRoot);
            fadeIn.setToValue(1);
            fadeIn.play();
        }
    }

    public static MediaView createBackgroundVideo(String filename) {
        File file = new File(filename);
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);

        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(1600);
        mediaView.setFitHeight(900);
        return mediaView;
    }
}