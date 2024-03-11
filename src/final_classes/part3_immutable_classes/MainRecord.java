package final_classes.part3_immutable_classes;

public class MainRecord {

    public static void main(String[] args) {

        // Creating with the args constructor
        PersonRecord jane = new PersonRecord("Jane","01/01/1930");
        PersonRecord jim = new PersonRecord("Jim","02/02/1932");
        PersonRecord joe = new PersonRecord("Joe","03/03/1934");

        PersonRecord[] johnKids = {jane,jim,joe};
        PersonRecord john = new PersonRecord("John","05/05/1900",johnKids);

        System.out.println(john); //John, dob = 05/05/1900, kids = Jane, Jim, Joe

        //Add John's Copy
        PersonRecord johnCopy = new PersonRecord("John","05/05/1900");
        System.out.println(johnCopy); //John, dob = 05/05/1900, kids = , , , , , , , , , , , , , , , , , , ,

        //Create a local variable kids of Person[] and assign it to johnCopy's kids
        // set 1st elmnt to Jim instance
        // set 2nd elmnt to Ann instance
        PersonRecord[] kids = johnCopy.kids();
        kids[0] = jim;
        kids[1] = new PersonRecord("Ann","04/04/1936");
        System.out.println(johnCopy); //John, dob = 05/05/1900, kids = Jim, Ann, , , , , , , , , , , , , , , , , ,

        //Trying to mutate john's kids
        johnKids[0] = new PersonRecord("Ann","04/04/1936");
        System.out.println(john);

    }
}
