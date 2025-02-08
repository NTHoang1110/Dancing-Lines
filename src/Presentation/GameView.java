package Presentation;

import Application.App;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;

public class GameView extends StackPane {
    Layout layout = new Layout();
    Slider musicSlider;
    MediaView mediaView = new MediaView();

    public GameView(){
        this.getChildren().add(mediaView);
        this.getChildren().add(layout);

        Pane sliderPane = new Pane();

        musicSlider = new Slider();
        musicSlider.setOrientation(Orientation.VERTICAL);
        musicSlider.setMinHeight(App.root.getHeight());
        musicSlider.setLayoutX(100);
        musicSlider.setLayoutY(0);
        sliderPane.getChildren().add(musicSlider);
        this.getChildren().add(sliderPane);
    }

    public Slider getMusicSlider() {
        return musicSlider;
    }
}
