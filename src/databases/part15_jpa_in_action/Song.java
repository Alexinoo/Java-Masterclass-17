package databases.part15_jpa_in_action;

import jakarta.persistence.*;

@Entity
@Table(name="songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="song_id")
    private int songId;

    @Column(name="song_title")
    private String songTitle;

    @Column(name="track_number")
    private int trackNumber;

    public Song() {
    }

    public String getSongTitle() {
        return songTitle;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", songTitle='" + songTitle + '\'' +
                ", trackNumber=" + trackNumber +
                '}';
    }
}
