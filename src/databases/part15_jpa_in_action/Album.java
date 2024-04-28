package databases.part15_jpa_in_action;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * Add additional Annotation to the id field
 *  - This is the GeneratedValue annotation which requires a strategy to be specified
 *      - set it to GenerationType.IDENTITY
 *      - informs the provider that the db is going to be assigning the id value
 *
 * Why didn't we need it on the ArtistId ?
 * ........................................
 * This is a specific JPA Provider specific requirement
 * It's here not really to ensure that the album id gets a valid auto-incremented id
 *  - This happens in MySQL remember because of the way we configured that table, in the database server
 *  - confirmed with the Artist Entity , when a new artist was added, it was given the id in the auto increment field for that table
 *
 * So why did we add it here?
 *  - This annotation is required by this JPA Provider, to ensure that the parent id , the artist_id , the foreign key is properly saved to the child
 *    during the insertion of a new record
 *  - There are other means to enforce this, but this is the simplest way for this example
 *
 * Relationship
 * ............
 * To establish the relationship between the Artist and Album , switch to the Artist Class and add a field for the Albums after the artist_name and before
 *  the first constructor
 *  - List<Album> albums - initialize it to a new arrayList
 *  - Map it with annotations
 *      - relationship description and in this case - this will be one To many
 *      - join column which is artist_id (foreign key column in the album table - this is how the 2 tables are joined together)
 *  - generate a getter for albums above the getArtistName()
 *  - No need for a setter
 *  - Finally, update toString() to print out the album list
 *
 * With this minor edits
 *  - we've now got 2 related Entities related by the album's list on Artist
 *
 * Back to the main class and the main()
 */

@Entity
@Table(name="albums")
public class Album implements Comparable<Album>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="album_id")
    private int albumId;

    @Column(name="album_name")
    private String albumName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="album_id")
    private List<Song> playList = new ArrayList<>();

    public Album() {
    }

    public Album(String albumName) {
        this.albumName = albumName;
    }

    public Album(int albumId, String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<Song> getPlayList() {
        return playList;
    }

    @Override
    public String toString() {
        playList.sort(Comparator.comparing(Song::getTrackNumber));
        StringBuilder sb = new StringBuilder();
        for (Song s:playList ) {
            sb.append("\n\t").append(s);
        }
        sb.append("\n");
        return "Album{" +
                "albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                ", songs='" + sb +
                '}';
    }

    @Override
    public int compareTo(Album o) {
        return this.albumName.compareTo(o.getAlbumName());
    }
}
