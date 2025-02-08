package Business;

import Application.App;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import java.util.HashMap;
import java.util.Map;

public class SongPlayer {
    private final SimpleMinim minim;
    private SimpleAudioPlayer player;
    private SongToPlay currentSong;
    private int instrument;
    private int tone;
    private Map<String,SongToPlay> songs;

    public SongToPlay getSong() {
        return currentSong;
    }
    public SongPlayer(){
        minim = new SimpleMinim();
        songs = new HashMap<>();
        SongToPlay song = new SongToPlay("src/Assets/Songs/First song/Metro Boomin - Am I Dreaming Instrumental (Official Audio).mp3", "src/Assets/Songs/First song/lines.txt", App.createBackgroundVideo("src/Assets/Songs/First song/background.mp4") );
        SongToPlay song2 = new SongToPlay("src/Assets/Songs/Second song/Vanessa Carlton - A Thousand Miles (Official Instrumental).mp3","src/Assets/Songs/Second song/lines2.txt", App.createBackgroundVideo("src/Assets/Songs/Second song/Vanessa Carlton - A Thousand Miles (1).mp4") );
        SongToPlay song3 = new SongToPlay("src/Assets/Songs/Third song/Twinkle Twinkle Little Star - Nursery Rhymes for Kids - Super Simple Songs (mp3cut.net).mp3","src/Assets/Songs/Third song/list.txt",App.createBackgroundVideo("src/Assets/Songs/Third song/Twinkle Twinkle Little Star - Nursery Rhymes for Kids - Super Simple Songs (1).mp4") );
        songs.put("Am I Dreaming",song);
        songs.put("A Thousand Miles",song2);
        songs.put("Twinkle Twinkle Little Star",song3);
        startMenuSong();
    }
    public void loadSong(SongToPlay song){
        player.pause();
        player = minim.loadMP3File(song.getInstrumentalFile());
        player.setGain(0 + (-60) * (1 - (float)(80) / 100));
        this.currentSong = song;
    }

    public void startMenuSong(){
        if(player != null){
            player.pause();
        }
        player = minim.loadMP3File("src/Assets/Startview.mp3");
        play();
    }
    public void play(){
        Thread playThread = new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            player.play();
        });
        playThread.setDaemon(true);
        playThread.start();
    }

    public int getInstrument() {
        return instrument;
    }
    public void setInstrument(int instrument) {
        this.instrument = instrument;
    }
    public int getTone() {
        return tone;
    }
    public void setTone(String tone) {
        switch (tone){
            case "High":
                this.tone = 60;
                break;
            case "Medium":
                this.tone = 48;
                break;
            case "Low":
                this.tone = 36;
                break;
        }
    }
    public Map<String,SongToPlay> getSongs() {
        return songs;
    }
}
