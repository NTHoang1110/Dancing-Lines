package Business;

import javafx.scene.media.MediaView;

public class SongToPlay {
    private String instrumentalFile;
    private String textFileForNotes;
    private MediaView backgroundVideo;

    public SongToPlay(String instrumentalFile, String textFileForNotes, MediaView backgroundVideo) {
        this.instrumentalFile = instrumentalFile;
        this.textFileForNotes = textFileForNotes;
        this.backgroundVideo = backgroundVideo;
    }

    public String getInstrumentalFile() {
        return instrumentalFile;
    }

    public String getTextFileForNotes() {
        return textFileForNotes;
    }

    public MediaView getBackgroundVideo() {
        return backgroundVideo;
    }

}
