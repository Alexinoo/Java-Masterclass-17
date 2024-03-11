package final_classes.part4_immutable_classes;

import java.util.Arrays;

public class PersonImmutable {

    //Variables need to be strictly private and final
    // No setter methods
    // Defensive copies on both getters & constructors
    private final String name;
    private final String dob;
    protected final PersonImmutable[] kids;

    public PersonImmutable(String name, String dob) {
        this(name,dob,null);
    }

    public PersonImmutable(String name, String dob, PersonImmutable[] kids) {
        this.name = name;
        this.dob = dob;
        this.kids = kids == null ? null: Arrays.copyOf(kids,kids.length); //defensive copies
    }

    // Copy Constructor
    protected PersonImmutable(PersonImmutable person){
        // this.name = person.name;
        // this.dob = person.dob;
        // this.kids = person.kids;
        this(person.getName(),person.getDob(),person.getKids());
    }


    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public final PersonImmutable[] getKids() {

        return kids == null ? null : Arrays.copyOf(kids,kids.length); // defensive copies
    }

    @Override
    public String toString() {
        String kidString = "N/A";

        if(kids != null) {
            String[] names = new String[kids.length];
            Arrays.setAll(names, i ->
                    names[i] = (kids[i] == null)? "": kids[i].name);

            kidString = String.join(", ",names);
        }
        return name + ", dob = "+ getDob() + ", kids = "+kidString;

    }
}
