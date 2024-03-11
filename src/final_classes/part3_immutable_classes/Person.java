package final_classes.part3_immutable_classes;

import java.util.Arrays;

public class Person {

    private String name;
    private String dob;
    private Person[] kids;

    public Person(String name, String dob) {
        this(name,dob,null);
    }

    public Person(String name, String dob, Person[] kids) {
        this.name = name;
        this.dob = dob;
        this.kids = kids;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public Person[] getKids() {
        return kids;
    }


    public void setKids(Person[] kids) {
        this.kids = kids;
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
        return name + ", dob = "+ dob + ", kids = "+kidString;

    }
}
