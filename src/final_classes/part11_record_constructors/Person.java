package final_classes.part11_record_constructors;

public record Person(String name,String dob) {

    /*
        Record Constructor comes in 3 flavours
        ......................................

     * Canonical -
        - or Long constructor is the implicitly generated constructor
        - you can explicitly declare your own which means the implicit one wont be generated
        - If you declare your own, you must make sure all fields get assigned a value

     * Custom -
        - is just an overloaded constructor
        - It must explicitly call the canonical constructor as it's first statement

     * Compact -
        - is declared with no parenthesis /no-args
        - or Short constructor is a special kind of constructor used, only on records
        - It's a succinct way of explicitly declaring a canonical constructor
        - We cannot assign argument values to fields in this constructor type
        - However, we can handle validations or normalization
        - You can't have both a compact constructor and a canonical constructor
        - Has access to all the arguments of the canonical constructor (don't confuse the arg with instance fields)
        - You can't do assignments to the instance fields in this constructor
     */

    //Canonical constructor

//    public Person(String name, String dob) {
//        this.name = name;
//        this.dob = dob.replace('-','/');
//    }

    //Copy constructor
    public Person(Person p) {
        this(p.name,p.dob);
    }

    /*
      Compact constructor

     - Handle validations
        - if dob is null throw error
        - Otherwise, trim - to /

     */

    public Person {
        if(dob == null) throw new IllegalArgumentException("Bad data");
        dob = dob.replace('-','/');
    }
}
