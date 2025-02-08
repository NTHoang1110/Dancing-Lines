package Presentation;

import Application.App;
import Business.SongPlayer;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javax.sound.midi.*;

public class GameViewController {
    MediaView mediaView;
    StackPane mainView;
    Slider musicSlider;
    Synthesizer synth;
    MidiChannel channel;
    GameView gameView;
    LineAnimationPane lineView;
    SongPlayer player;
    double PaneHeight;
    int prev = -1;

    public Pane getMainView() {
        return mainView;
    }

    public GameViewController(SongPlayer player) {
        PaneHeight = App.root.getHeight();
        this.player = player;
        mainView = new StackPane();
        mediaView = player.getSong().getBackgroundVideo();
        mainView.getChildren().add(mediaView);

        gameView = new GameView();
        lineView = new LineAnimationPane(player);

        mainView.getChildren().add(gameView);
        mainView.getChildren().add(lineView);

        musicSlider = gameView.getMusicSlider();
        musicSlider.setMin(0);
        musicSlider.setMax(PaneHeight);
        musicSlider.setValue(musicSlider.getMax()/2);

        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            synth.getReceiver().send(new javax.sound.midi.ShortMessage(252, 0, 0), -1);
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            MidiChannel[] channels = synth.getChannels();

            channel = channels[0];
            channel.programChange(player.getInstrument());

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
        player.play();
        initialize();
    }

    public void initialize() {
        mainView.setOnMouseMoved(e ->{
            double y = e.getSceneY();
            if (y >= 0 && y <= PaneHeight) {
                musicSlider.setValue(PaneHeight - y);
            }
        });

        mainView.setOnMousePressed(e ->{
            channel.noteOn(player.getTone() + (int)musicSlider.getValue()/40,100);
            System.out.println(e.getSceneY());
        });

        mainView.setOnMouseDragged(e -> {
            double y = e.getSceneY();
            musicSlider.setValue(PaneHeight - y);

            int currentNote = player.getTone() + (int) musicSlider.getValue() / 40;

            if (currentNote != prev) {
                channel.noteOff(prev);
                channel.noteOn(currentNote, 100);
                prev = currentNote;
            }
        });

        mainView.setOnMouseReleased(e -> {
            channel.allNotesOff();
        });
    }
}
