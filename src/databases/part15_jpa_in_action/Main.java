package databases.part15_jpa_in_action;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Main {
    /*
     * JPA In Action
     * .............
     * We have created an Artist entity but before we use it, we need to do a little setup with JPA Provider
     * The Hibernate doc provides samples for diff types of setups
     * In this case, we want to use JPA version of Hibernate.
     *  - Let's create a persistence.xml file in a META-INF folder which needs to be on a class path
     *      - The xml props set in this file will define info , to create a persistent context
     *      - This context gets created by Hibernate, and you can think of it as a special managed storage place for entities that includes a connection
     *         to the db
     *  - The META INF folder is usually at the root of the application's folder
     *      - Will add it under the src > META-INF folder of this project
     *          - Update the password to reflect you database password in persistence.xml
     *
     *  - This file needs to be on class path, so we'll edit the run configuration for the Main class
     *      - GoTo Edit configurations
     *          - modify options > Modify class path > Include > select src/META-INF
     *          - Click OK
     *
     * Putting all pieces together
     * Set up try-with resources statement
     *  - Create an EntityManager instance by calling createEntityManagerFactory on Persistence Class and pass the persistence unit name that
     *     we declared in the META-INF/persistence.xml
     *      - This process creates the persistence context that establishes database connections, and manages connection pools for efficient resource
     *        usage
     *  - Next, we have the factor class, we can get an EntityManager from that obj by calling createEntityManager
     *  - Both the EntityManagerFactory and the EntityManager are autocloseable, so putting them in the try-with-resources block means they will be
     *    closed automatically
     * Catch Exception and print the stack trace of any exception
     *
     * Inside the try {}
     *  - Get a transaction from the entityManager instance
     *      - Its a good idea to include any changes to a managed entity, within a transaction giving you the opportunity to test the results and rollback
     *        if the results aren't what you expect
     *  - Once we get a transaction instance, we can call begin()
     *
     *  - Next, call persist() on the entityManager obj and pass it a new instance of the Artist class, using the constructor that takes Artist name
     *      - Pass Muddy Water (Instead of Muddy Waters to do an update later)
     *
     *  - Next, commit the transaction
     *
     *  Before running
     *  - Delete artists whose id > 201
     *  - Reset AUTO_INCREMENT to 202 (setting this when the last id > 202 won't work ,so that's why we deleted records whose artist_id > 201)
     *
     * Running this ; (deleted Artist Class in part14_intro_jpa_orm because of naming conflicts )
     *  - We get a bunch of Hibernate messages printed out
     *      - mid-way down , prints the connection props with the password masked with asterisks and the username
     *      - Autocommit is also set to false
     *      - connection pool size is set to 20
     *  - All this is automatically created and setup for us by hibernate
     *  - Why don't we see any confirmation that data was inserted ?
     *  - Checking the SQL workbench, we see that the code executed an insert and the artist "Muddy Water" was persisted successfully
     *  - If we changed databases, we'd just need to update the persistence.xml and that would be it
     *
     * Next,
     *  - Open the persistence.xml and add another property in the persistence-unit section
     *      - Set the property name as hibernate.show_sql and set the value as true which will show the sql code in the output
     *
     * Rerunning the code:
     *  - Notice the 3rd from the last statement
     *      - print the text in a different color from the others
     *      - starts with Hibernate: then shows the insert statement that the persist() used
     *  - Now we have 2 "Muddy Water" in the artists table
     *      - 203 and 203
     *
     * Delete artist with id - 203
     *  - This time though use Hibernate's JPA code to do it
     *
     * Comment out on the persist() statement
     *  - Retrieve the artist from the db whose id = 203
     *  - do this with the find() on entityManager
     *  - find()
     *      - Takes at least 2 args
     *          - first is the Entity Class
     *          - second is the id
     *
     *  - Running this:
     *      - There are a couple of output statements , near the last lines of the Hibernate output
     *      - Prints the SQL statement that was used to retrieve the data followed by the artist entity printed out
     *      - First is a SELECT statement
     *          - uses an alias which is just a temporary name given to the table and is selecting artist_id and artist_name from the table based
     *            on the artist_id parameter
     *      - Second is the result of the toString()
     *          - confirms that we were able to read artist 203 data from the db using the find()
     *
     * - Next, delete this artist using remove()
     *      - Pass the artist instance to remove()
     *
     *  - Running this:
     *      - We get the additional delete sql statement printed out after the artist toString()
     *      - Confirmed from MySQL Workbench that we now have only 1 "Muddy Water"
     *
     *  - Next, update artist 202 "Muddy Water" to "Muddy Waters"
     *      - Change the find() to find artist 202
     *      - Update the name via setName() on artist
     *
     * - Running this:
     *      - Prints the update statement in the output
     *      - Confirmed from MySQL Workbench that "202 Muddy Water" was updated to "202 Muddy Waters"
     *
     * How did the Update work by just calling a setter on a java obj ?
     * How did it get persisted to the database ?
     * ................................................................
     * The find(), not only retrieved the record from the db, it populated the entity artist, but importantly, it also set it to a managed state
     *  - This means changes made to the entity, the java obj, will get persisted on a commit statement
     *  - The entity manager identifies state changes between what's in the database and the data in the entity object and builds appropriate SQL
     *    queries to keep the database in sync with the java obj
     *  - That's the beauty of using a JPA provider, which monitors and manages the entity's state, and keeps the data synchronized to the data layer
     *  - It's all done seamlessly for us
     *
     * How would we do this If we didn't want to first query the database next ?
     * .........................................................................
     *  - Create a new instance of Artist, using the constructor that takes id and artist name
     *      - Pass "202 Muddy Water"
     *
     *  - Running this code the way it is
     *      - Notice there's no generated SQL statements in the output
     *      - Nothing was persisted, we only have output statement printed there with the new name "Muddy Water" but this wasn't saved to the database
     *      - Because we created the artist entity manually, it's not managed by the EntityManager until we call either a persist() or merge()
     *      - We use merge if we are specifying an id, as we're doing here
     *          - call merge() on entityManager and pass the artist just before the commit
     *
     * - Re-running the code:
     *      - Prints a SELECT statement generated by Hibernate as well as an UPDATE statement
     *      - Hibernate figured out that the current state of the managed entity when compared to the data in the persistence layer was different and
     *        determined an update was needed
     *      - This update took place on the COMMIT statement
     *      - Confirmed from MySQL Workbench that "202 Muddy Waters" was updated/merged to "202 Muddy Water"
     *
     * - Reverting back to "202 Muddy Waters" from "202 Muddy Water"
     *      - But this time round using find and setter
     *      - comment on merge and uncomment on find()
     *      - Run it to update the changes
     *          - confirmed changes update to "202 Muddy Waters"
     *
     * Summary
     * .......
     * We've used Hibernate as our JPA provider to:
     *  - insert a new record into the artists table - using entityManager.persists()
     *  - retrieved a record with entityManager.find()
     *      - requires the Entity class as the first arg
     *      - entity id as the second arg
     *  - deleted a record with entityManager.remove()
     *  - updating data was done though automatically (if the entity was managed) when the transaction ended with a commit
     *  - We could use merge to use an existing entity with a valid id , to have it managed and persisted, after a manual creation of the entity
     */

    public static void main(String[] args) {

        try(var sessionFactory = Persistence.createEntityManagerFactory("dev.lpa.music");
            EntityManager entityManager = sessionFactory.createEntityManager()){

            var transaction = entityManager.getTransaction();

            ///// Insert //////
            // transaction.begin();
            // entityManager.persist(new Artist("Muddy Water"));
            // transaction.commit();

            ///// Find /////
            // transaction.begin();
            // Artist artist = entityManager.find(Artist.class,203);
            // System.out.println(artist);
            // transaction.commit();

            ///// Remove /////
            //transaction.begin();
            //Artist artist = entityManager.find(Artist.class,203);
            //entityManager.remove(artist);
            //transaction.commit();


            ///// Update /////
             transaction.begin();
             Artist artist = entityManager.find(Artist.class,202);
             System.out.println(artist);
             artist.setArtistName("Muddy Waters");
             transaction.commit();

            //Suppose we don't want to query first ? ///
            // transaction.begin();
            // Artist artist = new Artist(202, "Muddy Water");
            // System.out.println(artist);
            // transaction.commit();

            ////// merge ///////
            // transaction.begin();
            // Artist artist = new Artist(202, "Muddy Water");
            // System.out.println(artist);
            // entityManager.merge(artist);
            // transaction.commit();






        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
