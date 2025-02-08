package Presentation;

import Application.App;
import Business.SongPlayer;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MusicSelectionPane extends VBox {

    private final ListView<String> instrumentList;
    private final ListView<String> songList;
    private final ToggleGroup toneGroup;
    private final Button playButton;

    public MusicSelectionPane(SongPlayer player) {
        this.getStyleClass().add("music-selection-pane");

        Label instrumentLabel = new Label("Select Instrument:");
        instrumentLabel.getStyleClass().add("label");

        instrumentList = new ListView<>();
        instrumentList.getItems().addAll("Piano", "Guitar", "Organ","Bagpipe","Trombone");
        instrumentList.setPrefHeight(100);
        instrumentList.getStyleClass().add("list-view");

        Label songLabel = new Label("Select Song:");
        songLabel.getStyleClass().add("label");

        songList = new ListView<>();
        songList.getItems().addAll("Am I Dreaming", "A Thousand Miles", "Twinkle Twinkle Little Star");
        songList.setPrefHeight(100);
        songList.getStyleClass().add("list-view");

        Label toneLabel = new Label("Select Tone:");

        toneGroup = new ToggleGroup();
        ToggleButton lowTone = new ToggleButton("Low");
        ToggleButton mediumTone = new ToggleButton("Medium");
        ToggleButton highTone = new ToggleButton("High");
        lowTone.setToggleGroup(toneGroup);
        mediumTone.setToggleGroup(toneGroup);
        highTone.setToggleGroup(toneGroup);

        HBox toneBox = new HBox(10, lowTone, mediumTone, highTone);
        toneBox.setAlignment(Pos.CENTER);
        toneBox.getStyleClass().add("tone-box");

        playButton = new Button("Play 🎵");
        playButton.setOnAction(e -> {
            String instrument = instrumentList.getSelectionModel().getSelectedItem();
            String song = songList.getSelectionModel().getSelectedItem();
            ToggleButton selectedTone = (ToggleButton) toneGroup.getSelectedToggle();
            String tone = (selectedTone != null) ? selectedTone.getText() : "No Tone Selected";

            if (instrument == null || song == null || selectedTone == null) {
                showAlert("Please select an instrument, a song, and a tone!");
            } else {
                switch (instrument) {
                    case "Piano":
                        player.setInstrument(0);
                        break;
                    case "Guitar":
                        player.setInstrument(24);
                        break;
                    case "Organ":
                        player.setInstrument(18);
                        break;
                    case "Bagpipe":
                        player.setInstrument(109);
                        break;
                    case "Trombone":
                        player.setInstrument(57);
                        break;
                }
                player.loadSong(player.getSongs().get(song));
                player.setTone(tone);
                App.switchRoot("GameView",player);
            }
        });

        this.getChildren().addAll(instrumentLabel, instrumentList, songLabel, songList, toneLabel, toneBox, playButton);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(15);

        lowTone.getStyleClass().add("toggle-button");
        mediumTone.getStyleClass().add("toggle-button");
        highTone.getStyleClass().add("toggle-button");
        playButton.getStyleClass().add("play-button");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Music Player");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


