package final_classes.part4_immutable_classes;

import final_classes.part2_external.util.LivingPerson;
import final_classes.part5_hacker.PersonOfInterest;

public class MainImmutable {

    public static void main(String[] args) {

        // Creating with the args constructor
        PersonImmutable jane = new PersonImmutable("Jane","01/01/1930");
        PersonImmutable jim = new PersonImmutable("Jim","02/02/1932");
        PersonImmutable joe = new PersonImmutable("Joe","03/03/1934");

        PersonImmutable[] johnKids = {jane,jim,joe};
        PersonImmutable john = new PersonImmutable("John","05/05/1900",johnKids);

        System.out.println(john); //John, dob = 05/05/1900, kids = Jane, Jim, Joe



        //Create a local variable kids of Person[] and assign it to johnCopy's kids
        // set 1st elmnt to Jim instance
        // set 2nd elmnt to Ann instance
        PersonImmutable[] kids = john.getKids();
        kids[0] = jim;
        kids[1] = new PersonImmutable("Ann","04/04/1936");
        System.out.println(john); //John, dob = 05/05/1900, kids = Jane, Jim, Joe

        //Trying to mutate John's kids
        johnKids[0] = new PersonImmutable("Ann","04/04/1936");
        System.out.println(john); //John, dob = 05/05/1900, kids = Jane, Jim, Joe


        //Living Person Class
        LivingPerson johnLiving = new LivingPerson(john.getName(),john.getKids());
        System.out.println(johnLiving); //John, dob = null, kids = Jane, Jim, Joe, , , , , , ,

        //Add Anne and pass to addKid()
        LivingPerson anne = new LivingPerson("Anne",null);
        johnLiving.addKid(anne);
        System.out.println(johnLiving); //John, dob = null, kids = Jane, Jim, Joe, Anne, , , , , ,


        // Person Of Interest Class
        PersonOfInterest johnCopy = new PersonOfInterest(john);
        System.out.println(johnCopy); //John, dob = 05/05/1900, kids = Jane, Jim, Joe

        //  mutates johnCopy and john
        // Means PersonImmutable and PersonOfInterest are mutable
        kids = johnCopy.getKids();
        kids[1] = anne;
        System.out.println(johnCopy); //John, dob = 05/05/1900, kids = Jane, Anne, Joe
        System.out.println(john); //John, dob = 05/05/1900, kids = Jane, Anne, Joe


    }
}
