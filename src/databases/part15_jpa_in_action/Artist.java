package databases.part15_jpa_in_action;

/*
 * Artist
 * To make this an Entity - simply add @Entity
 *  - IntelliJ should be able to solve this annotation and include an import
 *  - This is part of the package, jakarta.persistence which is included in the hibernate-core package
 *  - jakarta.persistence consists of all the annotations for the JPA specification
 *
 * An Entity usually has a relationship with a table, so will set that next
 *
 * Our artists table has only 2 columns , so we'll set up 2 fields on this class to rep those columns
 * Add @Column to both respectively
 * Add @Id to the PK which is artist_id
 *  - artistId
 *  - artistName
 *
 * Next,
 * Need constructors and getters & setters so we'll generate those starting with the constructors
 * All JPA entities are required to have a no-args constructor, so let's add that first
 *
 * So Generate
 *  - no args constructor
 *  - 1 arg constructor with artistName
 *  - 2 args constructor with both fields
 *
 * Next Generate getter and setter
 *  - Pick just the artistName since we don't need ()s for the id
 *
 * Finally, include a toString()
 *  - Select String(concat+)
 *
 * And we're done
 *  - We have created our first JPA Entity
 *
 * It's really just an annotated POJO
 *
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="artists")
public class Artist {

    @Id
    @Column(name="artist_id")
    private int artistId;

    @Column(name="artist_name")
    private String artistName;

    public Artist() {
    }

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    public Artist(int artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", artistName='" + artistName + '\'' +
                '}';
    }
}
