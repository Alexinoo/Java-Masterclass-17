package databases.part15_jpa_in_action;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;

import java.util.List;
import java.util.stream.Stream;

public class Jpa_queries_joins {

    /*
     * JPA Queries, JPQL Joins
     * .......................
     * Suppose we know want a list of artist names and not all the data for an artist , e.g. not artist_id or the album_names
     * Like SQL, we can specify the data to be returned to a subset of data available
     * Instead of specifying each column name in the query, We specify the field name on the entity
     *
     * Demo
     * ....
     * Copy code from Jpql_queries class and update accordingly
     *  - update "getArtistsJPQL" to "getArtistsNames"
     * Next,
     *  - Instead of just selecting "a" in the query
     *      - update to a.artistName
     *
     * Call getArtistsNames() from the main() and assign the result of this () to names variable
     *  - print each element in the List
     *
     * Running this:
     *  - Prints the 6 artists as expected.
     *
     * We can also return names as String
     *  - update List<Artist> to List<String>
     *  - update Artist.class to String.class
     * In my case, we'll duplicate getArtistsNames() and update the above respectively
     * Running this:
     *  - Returns a list of plain strings, however they are not mapped to the Artist Entity
     *
     *
     * We can also use a Tuple as an alternative to achieve this
     *
     * JPA Tuple
     * .........
     * A JPA Tuple is an ordered collection of values retrieved from a database query
     * It's like a lightweight data container, offering more flexibility , than simply returning entire entities,
     *  or raw obj(s) arrays
     * Think of it as a min-record , holding just the specific data you need from the db, without the overhead of full
     *  entity obj(s)
     * The Tuple class is part of the jakarta.persistence package
     * Implementing a Tuple
     *  - update return type in the method signature
     *      - From List<Artist> to List<Tuple>
     *  - update 2nd arg in the createQuery(jpql,Artist.class)
     *      - From Artist.class to Tuple.class
     *
     * Next,
     *  - Add a.artistId in the SELECT list
     *
     * Running this
     *  - Prints the artist id and artist name in an array-like form
     *
     * We could have simply used the array of obj(s) as the return type but the Tuple has some built in functionality
     * It's toString() that will print each element in the Tuple
     * In addition, each element in a Tuple has a name, that allows you to access elements using meaningful names
     *  , improving code clarity
     * Tuples can seamlessly be integrated with the Stream API for efficient data processing and transformations
     *  leveraging Java functional programming capabilities
     *
     * Next,
     * Let's take this example a little bit further and return a Stream from the getArtistNames()
     *  - This requires just 2 changes to getArtistNames()
     *      - update return type in the method signature
     *          - From List<Tuple> to Stream<Tuple>
            - update return statement
     *          - From query.getResultList(); to query.getResultStream();
     *  - Running this:
     *      - Works just the same as if we had returned a List because forEach is a terminal operation on stream
     *
     * Next,
     * Let's map this Tuple to an Artist entity in the main()
     *  - comment out forEach
     *  - start with a stream variable "names"
     *      - call map() on the stream and for each Tuple on the Stream, instantiate a new Artist
     *          - we've a constructor on Artist that takes artist id and an artist name
     *          - we can get elements from a Tuple using the get() passing an index and specific class that shd be returned
     *              - so pass 0, and then Integer.class for the id
     *              - If we don't specify the class an obj is returned, and we can cast that
     *                  - do something similar with the artist name
     *      - print each artist instance
     *
     *  - Running this:
     *      - Prints values mapped to the Artist Entity & we don't have any albums in this case
     *
     * If you want to retrieve data from a Tuple using a name, your JPQL query needs to include field aliases
     *  - Add these next to the jpql variable in getArtistNames()
     *      - use id as the alias for artist id
     *      - use name as the alias for artist name
     *  - You can specify an alias simply by including it directly after the field name - use id
     *  - Alternatively, you can use the keyword AS to specify an alias - do for artist name
     *  - In the main(), we can then use the aliases , Id and name , in the get()
     *      - replace the no 0 with literal text "id"
     *      - replace the no 1 with literal text "name"
     *  - This makes the code easier to understand
     *  - Running this:
     *      - We get the same result as before
     *
     * The Tuple is a simple collection mechanism, that can be used with JPQL, to return data without relying on
     *  a specific entity class
     * We've seen that elements can be named, data types of the elements can also be queried through ()s on each Tuple
     *  element if need be
     *
     *
     * Next,
     *  Comment on the code that's calling getArtistNames()
     * We want to get back to querying a full artist record - copy getArtistsJPQL() and paste here
     * This example:
     *  - We want to get all the artists who have a "Greatest Hits album"
     *  - update the regex in the main() from %Stev% to %Greatest Hits%
     * Since the JPQL is trying to match on artist name and not album name, need to update the statement in the
     *  getArtistsJPQL()
     * We need to include a join to query by album
     * Importantly, this is a JPQL Join and not a SQL join
     * First
     *  - Comment out on the jpql statement in getArtistsJPQL()
     *  - Add a jpql string variable and start the SELECT statement the same way
     *      - But now adda JOIN, joining not to the table, but to the field declared on the Artist Entity which we called
     *        albums
     *      - Then use album in singular as an alias for this collection
     *      - Next, the WHERE clause will match up on an album name
     *          - so, album is the Album Entity and albumName is a field on that class
     *          - at no time do we directly reference db tables or columns
     *  - The join condition is setup in the annotations for the albums field in the Artist class, so we don't have
     *    to include the joined fields here
     *  - Running this
     *      - Five artists were found that have a Greatest Hits album in this query
     *
     * For good measure, we'll change this query slightly, so that next we'll find any artist that has either a
     *  "Greatest Hits", or a Best of collection album
     *  - We can do this by adding/appending an OR statement in the WHERE clause after the first parameter placeholder
     *      - OR album.albumName LIKE ?2
     *      - ?2 is the second numeric placeholder
     *  - Instead of passing the 2nd matched value as another arg to getArtistsJPQL(), we'll just hard code it here
     *    for simplicity
     *      - call setParameter() on query and pass
     *          - the first arg which needs to be no 2 - will substitute the 2nd placeholder specified by ?2
     *          - then pass a String literal "%Best of%"
     *      - This will match any album with the text "Best of" in any part of the album name
     *
     *  - Rerunning this:
     *      - we get 15 matches for artists who've either got a "Greatest Hits" album or a "Best of" collection
     *          of songs
     *
     * This was a short introduction to JPQL,
     * We can see why JPA and JPQL have become popular, in many java frameworks
     * They certainly simplify some of the mechanisms needed to query data from a database, with a java application
     *
     *
     * ////////////
     *      REFERENCE links to learn more about JPQL
     *      Also listed in the resources section
     * /////////////
     *
     * JPQL is still a String that has to be interpreted and parsed into executable code
     * The JPA Criteria Builder on the other hand is a more Object-Oriented approach to querying with entities
     */

    private static List<Artist> getArtistsJPQL(EntityManager em, String matchedValue){
        //String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE ?1";
        String jpql = "SELECT a FROM Artist a JOIN albums album " +
                "WHERE album.albumName LIKE ?1 OR album.albumName LIKE ?2";
        var query = em.createQuery(jpql, Artist.class);
        query.setParameter(1,matchedValue);
        query.setParameter(2,"%Best of%");
        return query.getResultList();
    }

    public static void main(String[] args) {

        List<Artist> artists = null;

        try(
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("dev.lpa.music");
                EntityManager em = emf.createEntityManager()) {

            var transaction = em.getTransaction();
            transaction.begin();


            // var names = getArtistsNames(em,"");
//            var names = getArtistsNames(em,"%Stev%");
            //names.forEach(System.out::println);
//            names.map(a -> new Artist(
//                            a.get(0, Integer.class),
//                            (String)a.get(1)))
//                    .forEach(System.out::println);
//            names.map(a -> new Artist(
//                            a.get("id", Integer.class),
//                            (String)a.get("name")))
//                    .forEach(System.out::println);

            artists = getArtistsJPQL(em,"%Greatest Hits%");
            artists.forEach(System.out::println);


            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
     * Returns List<Artist>
     * ....................
        private static List<Artist> getArtistsNames(EntityManager em, String matchedValue){
            String jpql = "SELECT a.artistName FROM Artist a WHERE a.artistName LIKE ?1";
            var query = em.createQuery(jpql, Artist.class);
            query.setParameter(1,matchedValue);
            return query.getResultList();
        }
    */


    /*
     * Returns Strings
     * ...............
    private static List<Artist> getArtistsNames(EntityManager em, String matchedValue){
        String jpql = "SELECT a.artistName FROM Artist a WHERE a.artistName LIKE ?1";
        var query = em.createQuery(jpql, Artist.class);
        query.setParameter(1,matchedValue);
        return query.getResultList();
    }
    */

     /*
     * Returns Tuple
     * ...............

    private static List<Tuple> getArtistsNames(EntityManager em, String matchedValue){
        String jpql = "SELECT a.artistId,a.artistName FROM Artist a WHERE a.artistName LIKE ?1";
        var query = em.createQuery(jpql, Tuple.class);
        query.setParameter(1,matchedValue);
        return query.getResultList();
    }
    */

    private static Stream<Tuple> getArtistsNames(EntityManager em, String matchedValue){
        String jpql = "SELECT a.artistId id,a.artistName AS name FROM Artist a WHERE a.artistName LIKE ?1";
        var query = em.createQuery(jpql, Tuple.class);
        query.setParameter(1,matchedValue);
        return query.getResultStream();
    }

}
