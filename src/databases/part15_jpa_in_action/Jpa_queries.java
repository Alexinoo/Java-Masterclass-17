package databases.part15_jpa_in_action;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Jpa_queries {

    /*
     * JPA Queries
     * ...........
     * In JPA, you have 3 options to execute a query, all of which still rely on the existence of an entity class
     *  - You can use JPA Query using a special language, named JPQL
     *  - Criteria Builder, which is a more programmatic way to put together the selection request
     *  - Native Query which relies on SQL
     *
     * JPA Query (JPQL)
     * ................
     * The Java Persistence Query Language , or JPQL is an object-oriented query language
     * It's specifically designed to work with entities, in java applications that uses JPA
     * It provides a way to query data stored in relational db, without a need to understand the details of how the
     *  data is actually structured there
     * To use JPQL, you actually query and not tables specifically
     *
     * Implementations
     * ...............
     * Create getArtistsJPQL()
     *  - Takes 2 params
     *      - em : EntityManager
     *      - matchedValue : String
     *  - Create a string variable for the query statement
     *      - Looks a lot like SQL
     *          - The letter "a" is a variable, as well as a table alias
     *      - Notice we're querying from Artist Entity or the Artist class in other words and not from artists table
     *  - Execute this query using createQuery() on the EntityManager instance that takes 2 args
     *      - a String that contains JPQL
     *      - the class name , of the entity that's being queried
     *      - returns a TypedQuery, a generic type, an interface in this case and has a type arg of Artist
     *  - The query obj we get back has a () called getResultList
     *      - It return a List<Artist> and so we can just return that directly
     *  - Returns a List<Artist> - a list of Artist Entity types based on some query
     *  - Call this in the main()
     *
     * main()
     *  - Create a List<Artist> and initialize it to null
     *  - Set up an EntityManager and catch any Exception by printing the stack trace
     *  - the variable artists will be set to the result of calling getArtistsJPQL()
     *  - When using JPA Queries, it's a good idea to wrap the queries in a transaction
     *      - get a transaction from entityManager obj
     *      - call begin() on transaction instance
     *      - assign the result of calling getArtistsJPQL() to artists
     *          - pass em instance
     *          - pass an empty string
     *      - print results of artist with forEach
     *      - call commit() on transaction instance
     *
     * But why do we need a commit here when we're just querying records?
     * ..................................................................
     *  - This is kind of a complicated subject and has to do with lazy loading vs eager fetching of dependent tables
     *    as well as transactional integrity btwn related tables
     *  - If you do have problems with your queries, and you're not including them in a transaction block, the instructor
     *    recommends try to start doing that
     *
     * Running this:
     *  - Print all artists in the table
     *  - Each record contains all the row data in each artist record but also includes artist album's records
     *
     * In my JPQL statement, when we specified SELECT a FROM Artist, we din't specify the data that we wanted , meaning
     *  what fields that mapped to columns
     *  - So it returned all the data for all the fields on this entity
     *  - You recall that we set up the entities by declaring a relationship on the albums field, a list, declared in
     *    artist , to the album entity
     *  - All of the underlying database querying with the appropriate joins was done seamlessly by the JPA Provider
     *    in this case Hibernate
     *  - We're able to write a simple generic query against an Artist Entity class to get related data, in this case
     *    albums for each artist
     *  - This doesn't require developer retrieving data, to know anything about inner or outer joins or foreign keys or
     *    any other specific implementation details of the db
     *  - It also doesn't require views , on the db server for simpler querying of a normalized schema
     *
     * ADDING WHERE CONDITIONS
     * ///////////////////////
     *
     * Alter the query slightly, by just getting artist whose names start with "Stev"
     *  - Change the SELECT statement and add a WHERE clause
     *      - a.artistName like :partialName
     *      - notice we're using an attribute name on the Artist entity, and not the column name in the table , artist_name
     *      - placeholder parameter which is called a named parameter is specified by a colon followed by any variable name
     *        you want
     *          - here, we're calling it partialName since we'll match on part of the artist's name
     *      - Next, we need to pass a value to this placeholder parameter
     *      - Add a call to setParameter() on the query variable
     *      - One version of setParameter() takes 2 Strings
     *          - first is the named parameter - pass string literal "partialName" - should match the named parameter
     *              used in your JPQL statement
     *          - second argument is the value to be used, pass the matchedValue that contains the criteria to match on
     *
     * Back to main()
     *  - Instead of passing an empty literal "", pass an SQL pattern "%Stev%"
     *      - The % is the SQL wildcard specifier, in a pattern that's used by a LIKE clause in SQL
     *      - It means match on zero, or many char(s)
     *      - Because we're matching with a % sign, this will match the contiguous letters "Stev" in any part of the album name
     *
     *  - Java regex to do something similar is a dot (.)
     *
     * Running this:
     *  - Prints only 6 artists that matched that
     *
     * We can change the pattern if we only wanted artists whose names start with "Stev" by removing the first % sign
     *
     * In addition to named parameters, you can use placement parameters in the query string
     *  - similar to the ? placeholder , that we looked at in the callable and prepared statements
     *  - In this case, in addition to the ?, you have to include a numeric value
     *  - Numeric placeholders must start at 1 , but they don't have to be in sequential order and you can re-use a numeric
     *    placeholder, in a single JPQL string
     *      - update set ":partialName" to "?1" in the jpql string
     *      - change the setParameter to take integer no 1 as the first arg
     *
     *  - Running this:
     *      - We get the same results as we did with a named parameter
     *
     * Named placeholders make more readable code, but it's up to you or maybe the placeholders you're using which
     *  kind of placeholders type you use
     *
     *
     */

    public static void main(String[] args) {
        List<Artist> artists = null;
        try(
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("dev.lpa.music");
                EntityManager em = emf.createEntityManager()) {

            var transaction = em.getTransaction();
            transaction.begin();
            //artists = getArtistsJPQL(em, "");
            artists = getArtistsJPQL(em, "%Stev%");
            artists.forEach(System.out::println);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static List<Artist> getArtistsJPQL(EntityManager em, String matchedValue){
       // String jpql = "SELECT a FROM Artist a";
        // String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE :partialName";
         String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE ?1";
        var query = em.createQuery(jpql, Artist.class);
        // query.setParameter("partialName",matchedValue);
        query.setParameter(1,matchedValue);
        return query.getResultList();
    }


}
