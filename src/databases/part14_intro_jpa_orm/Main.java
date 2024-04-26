package databases.part14_intro_jpa_orm;

public class Main {

    /*
     * Introduction to JPA and ORM
     * ...........................
     * Other techniques for DB Interactions includes
     *  - Java Persistence API, or JPA in short
     *
     * What is JPA
     * ...........
     * JPA is a specification
     * There's no default implementation of the specification. provided in Java Standard Edition
     * There is a very good chance you'll be working on an application that uses some form of it
     * Some popular implementations are Hibernate, Spring JPA, and EclipseLink e.t.c
     * These are called JPA providers
     * In 2019, the Java Persistence API, officially changed its name to Jakarta Persistence API
     * It was previously part of the Java's Enterprise Edition which has become open source
     * THe Persistence API became one of the Jakarta's project
     *
     * Key Concepts of JPA
     * ....................
     * A few of the key concepts of JPA specification include:
     *  - Object Relational Mapping (ORM)
     *  - The Entity (which is the mapped obj) and the Entity Manager which manages lifecycles of these entities
     *  - The Persistence Context is a special cached area where the entities exist
     *
     * You can think JPA as an extra layer of functionality, between your application code and the JDBC code
     *
     * Object Relational Mapping (ORM)
     * ...............................
     * Object Relational Mapping (ORM) is a method of mapping database tables defined by columns, to a class with correlated
     *  fields, setters and getters
     * A record in a table then becomes an instance of one of these classes
     * ORM frameworks often automate the process of object relational mapping
     *  - Simplify code, reducing the need to write boilerplate classes
     *  - Simplify database operations, hiding the complexities of SQL queries , and handling db interactions for you
     *  - Provide code portability across different db systems
     *  - Encourage a more structured and more organized approach to data access
     *
     * The Entity
     * ..........
     * Typically, an Entity is a class that represents a table in a relational database
     * Each Entity instance corresponds to a row in that table
     * Each entity is annotated with metadata to instruct JPA providers how to map its fields to a table's columns,
     *  relationships to other tables and lifecycle management techniques to use
     *
     * Check the example on the slide which is a relatively simple one, of an annotated class Artist which would be an
     *  an entity for our artists table in the music db
     *
     *  @Entity
     *  @Table(name="artists")
     *  public class Artist {
     *      @Id
     *      @Column(name="artist_id")
     *      private int artistId;
     *
     *      @Column(name="artist_name")
     *      private String artistName;
     *
     *      @OneToMany
     *      @JoinColumn(name="artist_id")
     *      private List<Album> albums = new ArrayList<>();
     *   }
     *
     *  - The @Entity describes this class as the Entity type
     *  - The @Table describes the table name - artists in this case
     *  - The fields in this class also have annotations which describe their db correlated info
     *      - @Id - rep the artist_id
     *      - @Column(name="artist_id") - specifies Id's column name
     *  - Next
     *      - @Column(name="artist_name") - mapping to the artist_name column
     *
     *  - Declared a List<Album> for this artist
     *      - @OneToMany - specify info about this relationship
     *      - @JoinColumn(name="artist_id") - specifies the join column with the joined id
     *
     * A framework implementing JPA, will identify entities by this annotation
     *
     * The Entity Manager
     * ..................
     * The EntityManager is implemented by the JPA Provider
     * An entity can exist in a managed state , managed by the Entity Manager
     * Or it can exist in a detached state, outside an Entity Manager
     * A detached entity can then be merged into an EntityManager, if a commit is needed to a persistence layer
     * There are some key ()s on the EntityManager interface to know that line up with CRUD operations
     *  - persist() - create
     *      - Makes a detached instance managed, and it's this instance's state will get persisted on a commit
     *
     *  - find() - read
     *      - searches for an entity of the specified class using PK in the persistence layer
     *      - It returns an instance that will be managed and automatically persisted
     *
     *  - merge() - update
     *      - If an entity is managed, updates will be propagated automatically to the persisted layer , on a commit
     *      - If the entity is not yet managed, a merge() will make the entity managed
     *
     *  - remove() - delete
     *      - removes the entity instance from management, and executes delete on the persistence layer
     *
     * The Persistence Context
     * ........................
     * Has multiple purposes
     *  - It tracks the lifecycle state of managed entities
     *      - These states include
     *          - new, managed, detached and removed reflecting any changes you make to their properties
     *  - It synchronizes changes made to managed entities to the db
     *      - This happens automatically when a transaction is committed or flushed, ensuring the db state reflects
     *        the state of your in-memory obj(s)
     *  - It performs identity management, ensuring unique entity identity within a transaction
     *      - Even if you create duplicate obj(s) with the same identifier, the persistence context recognizes and manages
     *        them as a single entity
     *  - It acts as a cache , reducing db roundtrips by keeping frequently accessed entities in memory for faster retrieval
     *
     *
     *
     * Implementations
     * ...............
     * Include some additional libraries in this project
     *  - Include jdbc driver for MySQL (already done)
     *  - RightClick on projectName > Open Module Settings (F4) > Libraries
     *      - We need the Jakarta JPA Module
     *          - We can get this from Maven but we also need a JPA Provider
     *          - We'll use Hibernate and that comes packaged with JPA and so we can just include that 1 module
     *      - Click + button
     *          - Select From Maven
     *              - Type "org.hibernate.orm" - Press Enter
     *              - A List should popup
     *                  - Continue to type in this field "org.hibernate.orm:hibernate-core:6+"
     *                  - Select the latest version 6+
     *              - Ensure Transitive dependencies check box is checked in that dialog that appears
     *              - Click Ok
     *  - Examine the classes that you have picked to verify that you have the jakarta.persistence.api in the set of
     *    classes and its at least version 3.1
     *  - In addition, you want to verify that the module you pick has hibernate-core there and is at least version 6+
     *  - Apply the changes by selecting OK/Apply  button
     *
     * The purpose of Maven is to help us sort these dependencies out
     * Now that we've got the libraries that we need, we can proceed
     *
     * Implementation
     *  - Create a couple of entities for the music db
     *      - There are tools including a Hibernate Plugin for IntelliJ, that will generate entities for you based on
     *          your db schema
     *      - No available for the community edition that we're using
     *  - Therefore create them manually
     *
     *  - First Create Artist in this package
     *
     *
     */
    public static void main(String[] args) {

    }


}
