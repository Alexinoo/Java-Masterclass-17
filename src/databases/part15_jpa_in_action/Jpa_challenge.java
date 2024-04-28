package databases.part15_jpa_in_action;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Jpa_challenge {

    /*
     * JPA Challenge
     * .............
     * Create a new Entity, named Song for the music.songs table
     * Edit the Album Entity , adding a list of your Song entity as a field and include this list, in the toString() output
     * Edit this class, retrieving a single artist by id, confirming songs are now part of the artist data, that's retrieved
     * Create a SongQuery class that's similar to the MainQuery class(jpa_queries)
     *  - Needs to include a () to query the music data, returning any artist, with an album
     *    with a song that contains matching text of your choice
     *      - e.g. Words "Storm" , "Dead" or "Soul" are in a variety of song titles, in albums for diff artists
     *  - Print the artist name, album name and the matching song title, that contained the text
     *
     * Part 1 - Create the Song Entity
     * ...............................
     *  Include fields that lie up with columns in the songs table
     *  Annotate the class and each field correctly
     *  Create at least 1 constructor
     *  Generate some getters for fields your app might need access to
     *  Override or generate a toString(), that prints the fields of interest
     *
     * Part 2 - Edit the Album Entity
     * ...............................
     * Add a list of songs to the album Entity
     * Use annotations to set up proper entity relationship for the new field
     *  - This should be the same type of relationship in the Artist class for the album's field
     * Since we're only reading data in this case, you can skip the cascade instructions in the annotation
     * Change toString() on albums to first sort songs by track no then output each song on its own line
     *
     * Part 3 - Edit the Main Class, main()
     * ....................................
     * Test with a diff artist other than 202, as it's set up now since this artist's only album has no songs
     * Remove or comment out any code that inserts, or adds data to the entities
     *
     * Part 4 - Create a SongQuery class
     * .................................
     * Create a SongQuery class with a main ()
     * Include another () that uses JPQL Query to select data from the music db
     * This () should use a JPQL query
     * This () should select song titles that contain a specific word or phrase, which you should pass as a () parameter
     * At a minimum, the data returned should include artist name , album name and song title
     * Call this () in the main() to print the artist name, the album name and the song title of all matches
     * Bonus Challenge
     *  - Create a second () , to use a criteria builder query to produce the same results
     *  - Try to use a multi-select () instead of select on criteria query and research the join () on Root
     *  - You'll want to use it to perform your joins
     *
     *
     * Solution
     * ........
     *
     * Create Song class
     *  - set it up as a simple POJO (Plain Old Java Object) with the following fields
     *      - Fields
     *          - song ID : int
     *          - song title : String
     *          - track no : int
     *      - Constructor
     *          - no args constructor (Mandatory for a JPA Entity)
     *      - Getters
     *          - Generate getters for songTitle and trackNumber
     *      - toString()
     *          - output all the fields
     *  - Add annotations respectively
     *
     * Update Album Entity
     *  - Add another attribute, a List<Song> playList
     *      - insert it before the no-args constructor on Album
     *      - set it up as private and initialize it to a new ArrayList
     *  - Add annotations
     *      - @OneToMany() - ignore cascade instructions included on Artist
     *      - @JoinColumn() - joined by album_id
     *  - Generate a getter of playlist attribute
     * - Update toString()
     *      - sort the playlist - call the sort() and pass the Comparator, derived from getTrackNumber() on Song
     *      - Initialize a new StringBuilder variable
     *      - Loop through each song in the playlist
     *          - each son will be on its own line indented with a tab then the song data will get printed
     *          - end the song list with a new line
     *      - Include the string builder's value in the text that's returned from toString()
     *      - The configuration above will just make it easier to read the songs
     *
     * main()
     *  - setup an Entity Manager
     *      - Read/find or retrieve artist with id 103 (Madonna)
     *      - Print artist
     *  - Running this:
     *      - Prints the songs on the Album 'Ray Of Light' listed
     *      - Also prints duplicate album with no songs listed
     *
     *
     * Final Part of the challenge
     * Create SongQuery class
     *  - Add a main()
     *  - copy getArtistsJPQL() and paste below
     *      - update getArtistsJPQL() to getMatchedSongs()
     *          - update the SELECT and JOIN with playList giving it an alias "p"
     *              - aliases are required because the JPA Provider uses them as variable names
     *          - remove OR statement with the 2nd param (join to the collection field "playList" and not the table)
     *              - match with p.songTitle instead
     *          - remove the 2nd call to setParameter() since it won't be necessary now
     *  - main()
     *      - Setup Entity manager
     *      - print in tabular form
     *          - print dashed String
     *          - set up a variable of the word that we want to match on and initialize it to "Storm"
     *      - call getMatchedSongs() and pass EntityManager instance and the matched pattern surrounded by % sign
     *      - print header columns out, formatted each lined up in left justified columns
     *      - print the dashes under these columns using the same placement
     *
     *      - query data gets returned and assigned to matches variable
     *          - this data returns the matching artist with all of their albums and songs in the data, not just
     *             the matched album or song
     *      - Use a series of for loops
     *          - loop through the list of artists we get back
     *              - set up a local variable to the artistName
     *              - loop through each of the album that matched
     *                  - set a variable for the albumName
     *                      - loop through the album playlist
     *                          - set another variable for the song title
     *                              - check if the song contains the word we used in the query
     *                                  - print artistName, albumName and the song title
     *      - Before running :
     *          - edit the persistence.xml file
     *              - remove the last line that we added - comment it out
     *                  - makes the output easier to read
     *      - Running this:
     *          - prints in tabular form, artist name, album name and song title
     *          - try with "Soul" next and check the results
     */

     public static void main(String[] args) {
        try(
                var sessionFactory = Persistence.createEntityManagerFactory(
                        "dev.lpa.music");
                EntityManager em = sessionFactory.createEntityManager()
                ){
            var transaction = em.getTransaction();
            transaction.begin();
            Artist artist = em.find(Artist.class,103);
            System.out.println(artist);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}







