package databases.part15_jpa_in_action;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Jpa_criteria_builder {

    /*
     * JPA Criteria Builder
     * ....................
     *
     * The Criteria Builder Interface
     * The CriteriaBuilder interface describes a factory class, for creating various type-safe query components
     * It provides ()s for creating diff parts of he JPA query , each described as an obj and not just part of a string
     *
     *  CriteriaBuilder's factory method output types
     * ..............................................
     * The CriteriaBuilder's () are used to create specific instances, both the criteria query obj, and it's parts the
     *  specific criteria that make up the definition of the query
     * A query in it's simplest form consists of a command as well as a source of some sort
     * A command might be SELECT, UPDATE, or DELETE, and a source in SQL is usually a table or view, whereas in JPQL its
     *  an entity
     * A query may also have many other clauses and specifics, which defines what needs to be done
     * The CriteriaBuilder provides all the building blocks, as java obj(s), to fashion a specific query
     *  - It starts with the Criteria Query instance
     *      - You can think of this as scaffolding in which everything else is going to be placed
     *  - The scaffolding has specific slots and these translate to ()s on the Criteria Query
     * The building blocks fall into one of four categories which is:
     *  - Predicate
     *  - Expression
     *  - Selection
     *  - Order
     * The CriteriaBuilder isn't responsible for executing a query, or even putting all the parts together and is just
     *  responsible for creating customizable components you'll need
     * The Criteria Query obj will use building block types in its design to construct a specific query
     *
     * The CriteriaQuery
     * .................
     * The CriteriaQuery is a bit like scaffolding
     * Things get added and the instance mutates and grows
     * Unlike the StringBuilder though, the additions to the Criteria Query obj can be of multiple types, depending on the
     *  () selected
     * The ()s in the CriteriaQuery class rep specific clauses or expressions , in a SELECT query
     * These include:
     *  - select(SELECTION):CriteriaQuery
     *  - from(EntityType): Root
     *  - distinct(boolean): CriteriaQuery
     *  - orderBy(Order..):CriteriaQuery
     *  - where(Expression):CriteriaQuery
     *  - where(Predicate):CriteriaQuery
     *  - groupBy(Expression):CriteriaQuery
     * Also notice the args passed to these ()s which mostly consists of the four types above
     * When you pass instances of these types to ()s, you'll be using a CriteriaBuilder factory () to get specific
     *  instances of these types
     *  - A Predicate rep a condition in a WHERE clause
     *  - An Expression rep a computed value
     *      - Can be a mathematical expression, a function call, or a field reference
     *  - An Order is used to rep sorting criteria for the ORDER BY clause
     *  - A Selection rep an attribute expression , or result , used to define the selectable output of the query
     *
     * Finally, notice the from() on this class
     *  - from() is different from the rest and returns a root instance
     *
     * The Root is a special type and it's the bridge between the criteria query and your entity
     * It's the source of your query data
     * Other than the from(), all the other ()s returns an instance of Criteria Query
     * Like the String Builder obj, the Criteria Query obj is mutable, but each () returns a reference to itself
     * This means you can optionally chain these ()s together, which provides the advantage of more concise and readable
     *  code
     *
     * The Process to create an executable query, using CriteriaBuilder
     * ................................................................
     * Below is a summary of how we can use CriteriaBuilder and CriteriaQuery types to execute queries using JPA entities
     *
     *      Steps                                               Method
     *  - Get an instance of CriteriaBuilder   -     entityManager.getCriteriaBuilder()
     *  - Get an instance of CriteriaQuery     -    criteriaBuilder.createQuery()
     *  - Specify the entity (the query root)  -    criteriaQuery.from(Entity.class)
     *  - Select data                          -    criteriaQuery.select(root)
     *  - Add expressions, predicates, sorting -    invoke various ()s on criteriaQuery, using criteriaBuilder to
     *      criteria and other parts to the query    manufacture the appropriate components passed as args
     *  - Retrieve a query instance            -    entityManager.createQuery(criteriaQuery)
     *
     *  - Execute and get results              -    criteriaQuery.getResultsList()
     *                                              criteriaQuery.getResultsStream()
     *                                              criteriaQuery.getSingleResult()
     *
     * main()
     *  - Setup an EntityManager
     *
     * Add a static method : getArtistBuilder(EntityManager em, String matchedValue)
     *  - returns a Stream of Artist instances
     *  - Create a CriteriaBuilder variable builder by calling getCriteriaBuilder from EntityManager instance
     *  - Set a CriteriaQuery variable with a type arg of Artist
     *      - call createQuery() on the criteria builder instance and pass the Artist.class
     *  - Next, we need a query root
     *      - rep the starting point for constructing a query against a data set rep as an entity class
     *      - Has the Root Type with Artist as the type arg
     *          - call from() on criteriaQuery instance and pass Artist.class Entity
     *      - Think of the root as the FROM clause in JPQL, so if we add nothing to the root, the entire table associated
     *        with this entity is the data set that will be operated on
     *  - Next, define the operation that we want to execute on criteria Query obj which in this case is select()
     *      - pass it the root instance
     *  - Finally,
     *      - create the query and call getResultStream()
     *
     * Call getArtistsBuilder() from the main()
     *  - store the result in variable sArtists
     *  - Since we get a stream back , we can use stream operations to manipulate the data further
     *      - limit the results to 10
     *      - collect the data into a map using Collectors.toMap()
     *          - 1st arg , we pass the function to get the key, so it's keyed by the artist name
     *          - 2nd arg, we pass the function to get the value - just the no of albums
     *          - 3rd arg, we need to specify a merge function, and we can just use the method reference for Integer sum
     *      - finally, we want a tree back since we want it ordered
     *  - Print the artist name and the no of albums he got
     *  - Running this:
     *      - prints 10 artists , in artist name order and the no of albums each artist has
     *      - print the first 10 artists because of the limit operation
     *      - the artist were not ordered in the stream, though they are ordered here
     *
     * Next
     *  - Update getArtistsBuilder() and refine the query to include an Order by clause
     *      - call orderBy() on the criteriaQuery instance and pass
     *          - pass a factory obj, on the CriteriaBuilder instance by calling asc()
     *          - builder.asc() returns an Order Instance
     *              - pass to root.get() and the artist name as string , rep what you want to sort with
     *  - Rerunning this:
     *      - prints a diff set of artists, the first 10 alphabetically
     *
     * Next
     * - Update getArtistsBuilder() and add a WHERE clause
     *      - add it between the select() and the orderBy()
     *           (order does not matter - but just keep it in the logical order of an SQL statement)
     *      - call invoke() on the CriteriaQuery instance and pass a Predicate instance which we can get from ()s from
     *          the CriteriaBuilder - call like() and pass artist name and matched value
     *
     * main()
     *  - replace the empty string literal to "Bl%" (in my case, we'll just comment)
     *      - get artists whose names start with "Bl"
     *
     * Running this:
     *  - We get matching records - only artists whose names starts with Bl, so the where clause worked
     *
     * Next,
     *  We want to change the getArtistsBuilder()
     *  - The CriteriaQuery is a mutable obj & thus we don't need to actually assign instance, on each of the () calls
     *  - Each () outputs a () reference to itself which means we can chain ()s together
     *      - creates a chain of 3 ()s and reads like stream operations and makes it easier to read
     *  - In my case, we'll connect the old setup
     *  - Running this:
     *      - prints the same results as before
     *
     * Next, Let's see how we can execute a Native Query with the Entity manager
     *  - Add getArtistsSQL(EntityManager em, String matchedValue) : Stream<Artist>
            - call createNativeQuery() on EntityManager instance and assign to "query" variable
     *      - createNativeQuery() takes a String , which is a native SQL but with 1 minor exception
     *          - the string can contain placeholders, either named or numeric
     *          - we still pass the Artist.class as the 2nd arg , because the JPA provider will instantiate artist
     *            instances from the data
     *      - then call setParameter() and match parameter 1 set to the matchedValue
     *      - then return the result as a Stream
     *
     * main()
     *  - call getArtistsSQL instead of getArtistsBuilder() (comment out)
     *  - Running this:
     *      - prints the same output similar to Criteria Builder code
     *
     * Which type of query you use is dependent on a lot of diff factors
     *
     * Summary of JPA Queries
     * ......................
     * Below is a summary of the 3 types of queries you can execute , using JPA
     *
     *      Type                Description                         Advantages
     *  Native Query            Executes naive SQL queries          Full control over syntax
     *                                                              Performance db optimization for specific db
     *                                                              Access to vendor-specific features
     *
     * CriteriaBuilder query   Builds queries using java obj        Type-safe
     *                         & expressions                        Compile-time checking
     *                                                              Dynamic query construction
     *
     * JPQL query              Expresses queries using SQL-like     Portable across JPA implementations
     *                          syntax tailored for entities        Hides implementation-specific details
     *                                                              Intuitive for SQL-proficient developers
     *
     */

    private static Stream<Artist> getArtistsSQL(EntityManager em, String matchedValue){
        var query = em.createNativeQuery(
                "SELECT * FROM music.artists WHERE artist_name LIKE ?1", Artist.class);
        query.setParameter(1,matchedValue);
        return query.getResultStream();
    }

    public static void main(String[] args) {
        try(
                var sessionFactory = Persistence.createEntityManagerFactory("dev.lpa.music");
                EntityManager em = sessionFactory.createEntityManager()
                ){
            var transaction = em.getTransaction();
            transaction.begin();

           // Stream<Artist> sArtists = getArtistsBuilder(em,"");
           // Stream<Artist> sArtists = getArtistsBuilder(em,"Bl%");
            Stream<Artist> sArtists = getArtistsSQL(em,"Bl%");
            var map = sArtists
                    .limit(10)
                            .collect(
                                    Collectors.toMap(Artist::getArtistName,a ->a.getAlbums().size(),Integer::sum, TreeMap::new)
                            );
            map.forEach((key,value) -> System.out.println(key + " : "+value));

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Stream<Artist> getArtistsBuilder(EntityManager em , String matchedValue){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Artist> criteriaQuery = builder.createQuery(Artist.class);
        Root<Artist> root = criteriaQuery.from(Artist.class);
        //criteriaQuery.select(root);
        //criteriaQuery.where(builder.like(root.get("artistName"), matchedValue));
        //criteriaQuery.orderBy(builder.asc(root.get("artistName")));

        criteriaQuery
                .select(root)
                .where(builder.like(root.get("artistName"), matchedValue))
                .orderBy(builder.asc(root.get("artistName")));

        return em.createQuery(criteriaQuery).getResultStream();
    }
}
