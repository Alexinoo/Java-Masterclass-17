package databases.part15_jpa_in_action;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;

import java.util.List;

public class jpa_challenge_bonus {

    /* JPA Challenge Bonus
     * ...................
     * Create a () that use a CriteriaBuilder , that uses a CriteriaQuery to produce the same results
     *
     * Hint:
     * ....
     * use the multiselect and to research on join() on Root to perform your joins
     *
     * getMatchedSongsBuilder(EntityManager em, String matchedValue) : List<Object[]>
     *  - Create CriteriaBuilder instance
     *  - Create CriteriaQuery instance
     *      - Typed to Object[]
     *      - Pass Object[].class
     *  - Why return array of obj(s)
     *      - instead of returning every matching artist with all the albums and songs associated to the artists
     *      - this () return the song title, artist name and the album name as obj(s) in an obj array, which makes processing the results a lot
     *        simpler
     *      - also lets us know how to select a specific column, using criteria query
     *  - Next, get the root, which is ultimately Artist class
     *  - Join Artist to Album by creating a join variable, typed with artist and album
     *      - named albumJoin by calling join() on root and passing albums , the field name on Artist entity and specifying join type
     *  - The JoinType is a simple enum, with INNER,LEFT and RIGHT as your options
     *      - LEFT and RIGHT are different types of outer joins.
     *  - Create another Join with Album and Song
     *      - named songJoin by calling albumJoin() and not the root
     *      - pass the field name "playList" and pass INNER join
     *  - Now that we have root and joins setup, we can start chaining ()s on the criteria query variable
     *      - call multiselect() which lets us pass var args list of values
     *          - select the artist name from the root
     *          - select the album name from the albumJoin variable
     *          - select song title from the songJoin variable
     *      - chain where()
     *          - get a builder instance by calling like() on the builder instance and pass songTitle followed by the matched value
     *          - this sets up the like clause
     *      - chain orderBy()
     *          - get a builder instance by calling asc() on the builder, then pass the field we want the data to be sorted by i.e. artistName
     *      - use EntityManager to create a Typed Query passing it the Criteria query instance and chain getResultList() which is then returned
     *
     *
     * Call it from the main()
     *  - Print headers and dashes below each header
     *  - call forEch on the list we get back
     *      - use formatted string we used previously
     *      - use indices 0 through 2 to get each obj from the array in the list and cast each to a String
     *
     * Before running:
     *  - change the word variable to a different value, so from "Storm" to "Soul"
     *
     * Running this:
     *  - print the columns that we're interested in and pass back a list, where each element is an obj array
     *
     */

    public static void main(String[] args) {

        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                "dev.lpa.music");
            EntityManager em = emf.createEntityManager()
        ){
            String dashedString = "-".repeat(19);
            String word = "Soul";
            System.out.printf("%-30s %-65s %s%n","Artist","Album","Song Title");
            System.out.printf("%1$-30s %1$-65s %1$s%n",dashedString);
            var matches = getMatchedSongsBuilder(em, "%"+word+"%");
            matches.forEach(m -> {
                System.out.printf("%-30s %-65s %s%n",
                        (String) m[0],
                        (String) m[1],
                        (String) m[2]
                        );
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private static List<Object[]> getMatchedSongsBuilder(EntityManager em, String matchedValue){

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Artist> root = query.from(Artist.class);

        Join<Artist,Album> albumJoin = root.join("albums", JoinType.INNER);
        Join<Album,Song> songJoin = albumJoin.join("playList",JoinType.INNER);

        query
                .multiselect(
                        root.get("artistName"),
                        albumJoin.get("albumName"),
                        songJoin.get("songTitle")
                )
                .where(builder.like(songJoin.get("songTitle"),matchedValue))
                .orderBy(builder.asc(root.get("artistName")));
        return em.createQuery(query).getResultList();

    }
}
