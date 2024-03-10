package final_classes;

import java.util.*;

public class MainMailer {

    public static void main(String[] args) {
        /*
            Create an array of names using Array initializer {}
         */
        String[] names = {"Ann Jones","Ann Jones Ph.D.","Bob Jones M.D.",
                          "Carol Jones","Ed Green Ph.D.","Ed Green M.D.","Ed Black"};

        /*
         * create a List of StringBuilder and assign it to the result of
           calling getNames() passing it array of names
         *
         * returns a long list of names with duplicates
         */
        List<StringBuilder> populations = getNames(names);

        /*
         *  Set up a Map with a count of duplicates names with StringBuilder(key) and Integer as count(value)
         *  Calling the variable map counts and set to a TreeMap instance
         *  Not that the above map will be sorted
         *  Loop through populationArr and using merge() allows us to add a new name with a value of 1
            if it's a name not yet in the map or increment the value if it is there
         *
         * Then print the map
         */
        Map<StringBuilder,Integer> counts = new TreeMap<>();
        populations.forEach(population -> counts.merge(population,1,Integer::sum));

        System.out.println(counts);

         /*
         * Then print out the number of PHD Ann Jones in the map
         * Use get() to get the count of how many ann jones with Ph.D. are there in
           our population
         */
        StringBuilder annJonesPHD = new StringBuilder("Ann Jones Ph.D.");
        System.out.println("There are "+counts.get(annJonesPHD)+ " records for "+annJonesPHD);

        /*
         * Suppose it's our job to mail a flyer to this population & it's a company policy
           to remove suffixes b4 printing the name of the envelope for privacy purposes

         * Let's create a method for standardizing the names
         *
         * Loop through the list in the method argument
         *
         * Then loop through a list of possible suffixes .. now we only have Ph.D. and M.D.
           but you can imagine others could be added
         *
         * Create a local variable to hold an index
         *
         * Check if the suffix is in the name and pass that index back - if > -1 there's a matching
           suffix
         *
         * Strip it out with the replace() and replace it with an empty string
         *
         * Then add the cleaned up name to the newList variable
         * Returns a newList of standardized names back - initialized to an empty arrayList
         *
         * Call it from main() and print out the cleaned names
         */
        List<StringBuilder> cleanedNames = standardizeNames(populations);
        System.out.println(cleanedNames);

        /*
         * Let's print out the number of PHD Ann Jones again in the map
         * Use get() to get the count of how many ann jones with Ph.D. are there in
           our population
         */
        System.out.println("There are "+counts.get(annJonesPHD)+ " records for "+annJonesPHD);

       /*
        * Then print the map again -- Kind weird to have the counts yet we have removed the suffixes
        */
        System.out.println(counts); //{Ann Jones=3, Ann Jones=4, Bob Jones=5, Carol Jones=6, Ed Black=9, Ed Green=8, Ed Green=7}

       /*
        * let's such for annJones only
        */
        StringBuilder annJones = new StringBuilder("Ann Jones");
        System.out.println("There are "+counts.get(annJones)+ " records for "+annJones); //There are 4 records for Ann Jones

      /*
       * loop thrugh the map entry and see what we get
       *
       */
        System.out.println("_________________________________");
        counts.forEach((k,v)-> System.out.println(k + " : "+v));

      /*
       * loop through the keySet instead
       *
       */
        System.out.println("_________________________________");
        counts.keySet().forEach(k-> System.out.println(k + " : " + counts.get(k)));

    }

    /*
    * getNames(String[] names) - creates a random list of these names which takes an arr of Strings
    *
    * Use an index to determine how many names to add to my list for each distinct name
    *
    * Then return the list which is initially initialized to an arrayList

     */

    private static List<StringBuilder> getNames(String[] names){
        List<StringBuilder> list = new ArrayList<>();
        int index = 3;
        for (String name:names) {
            for (int i = 0; i < index; i++) {
                list.add(new StringBuilder(name));
            }
            index++;
        }
        return list;
    }

    private static List<StringBuilder> standardizeNames(List<StringBuilder> list){
        List<StringBuilder> newList = new ArrayList<>();
        for (var name: list) {
            for (String suffix : new String[]{"Ph.D.","M.D."} ) {
                int startIndex = -1;
                startIndex = name.indexOf(suffix);
                if( startIndex > -1){
                    name.replace(startIndex - 1,startIndex + suffix.length(),"");
                }

            }
            newList.add(name);
        }
        return newList;
    }
}
