package final_classes.part10_constructors_review;

public class Parent {

      /*
        Static initializer
        .....................
     *
     *  Java supports a special block, called a static block (also called static clause)
        that can be used for static initialization of a class.
     *
     *  This code inside the static block is executed only once: the first time the class is loaded into memory.
     * Enable us initialize static variables and is called as part of the class construction
     */

   static {
       System.out.println("Parent static initializer : class being constructed");
    }

    private final String name;
    private final String dob;

    protected final int siblings;

    /*
        Instance initializer
        .....................

     * A block of code declared directly in a class body     *
     * gets executed when an instance of the class is created
     * Are executed b4 any code in class constructors is executed
     * You can have multiple initializer blocks and are executed in the order they are declared
     */
    {
        //name = "John Doe";
        //dob = "01/01/1990";
        System.out.println("In Parent Initializer");
    }

   //Adding implicit/default constructor
   // public Parent() {
  //      System.out.println("In Parent's No Args Constructor");
  //  }

    //Adding args constructor
    public Parent(String name, String dob,int siblings) {
        this.name = name;
        this.dob = dob;
        this.siblings = siblings;
        System.out.println("In Parent constructor");
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    @Override
    public String toString() {
        return "name='" + getName()+ '\'' + ", dob= '"+ getDob()+ '\'';
    }
}
