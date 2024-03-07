package collections.part19_treemap_navigablemap_mthds;

import java.time.LocalDate;
import java.util.*;

public class Main {

    private static Map<String, Purchase> purchases = new LinkedHashMap<>();
    private static NavigableMap<String, Student> students = new TreeMap<>();

    public static void main(String[] args) {
        Course jmc = new Course("jmc101","Java Master Class","Java");
        Course python = new Course("pyt101","Python Master Class","Python");

        addPurchase("Mary Martin",jmc,129.99);
        addPurchase("Andy Martin",jmc,139.99);
        addPurchase("Mary Martin",python,149.99);
        addPurchase("Joe Jones",jmc,149.99);
        addPurchase("Bill Brown",python,119.99);

        //Added more students
        addPurchase("Chuck Cheese",python,119.99);
        addPurchase("Davey Jones",jmc,139.99);
        addPurchase("Eva East",python,139.99);
        addPurchase("Fred Forker",jmc,139.99);
        addPurchase("Greg Brady",python,129.99);

        purchases.forEach((key,value)-> System.out.println(key + ": "+value));


        ///Separator
        System.out.println("_".repeat(30));


        students.forEach((key,value)-> System.out.println(key + ": "+value));

        NavigableMap<LocalDate,List<Purchase>> datedPurchases = new TreeMap<>();
        for (Purchase p:purchases.values() ) {
            datedPurchases.compute(p.purchaseDate(),
                    (pdate,plist)->{
                        List<Purchase> list = (plist == null)? new ArrayList<>():plist;
                        list.add(p);
                        return list;
                    });
        }

        ///Separator
        System.out.println("_".repeat(50));


        datedPurchases.forEach((key,value)-> System.out.println(key + ": "+value));

        ///Separator
        System.out.println("_".repeat(50));


        /*
        *
        *
        *
        *
        *  Working With TreeMap (NavigableMap) methods
         */

        ///// Get current Year ////////
        int currentYear = LocalDate.now().getYear(); //2024

        //create a day with current year and first day of the year
        LocalDate firstDay = LocalDate.ofYearDay(currentYear,1); //2024-01-01

        /////last_date of week 1 on Jan
        LocalDate week1 = firstDay.plusDays(7); //2024-01-08

        /*
            public NavigableMap<K,V> headMap(K toKey,boolean inclusive)

                - Returns a view of the portion of this map whose keys are less than (or equal to, if inclusive is true) toKey.
                  The returned map is backed by this map, so changes in the returned map are reflected in this map, and vice-versa.
                  The returned map supports all optional map operations that this map supports.
         */
        ///Separator
        System.out.println("_".repeat(50));

        Map<LocalDate,List<Purchase>> week1Purchases = datedPurchases.headMap(week1);
        week1Purchases.forEach((key,value)-> System.out.println(key + ": "+value));


         /*
            public NavigableMap<K,V> tailMap(K fromKey,boolean inclusive)

                - Returns a view of the portion of this map whose keys are greater than
                  (or equal to, if inclusive is true) fromKey.

                - The returned map is backed by this map, so changes in the returned map are reflected in this map, and vice-versa.
                  The returned map supports all optional map operations that this map supports.
         */

        ///Separator
        System.out.println("_".repeat(50));
        Map<LocalDate,List<Purchase>> week2Purchases = datedPurchases.tailMap(week1);
        week2Purchases.forEach((key,value)-> System.out.println(key + ": "+value));

        // Find how many of each course - sold for each week
        System.out.println("Week 1 Purchases");
        displayStats(1,week1Purchases);


        ///Separator
        System.out.println("_".repeat(50));

        System.out.println("Week 2 Purchases");
        displayStats(2,week2Purchases);

        /*

            public K lastKey()
            ....................

            Description copied from interface: SortedMap

                - Returns the last (highest) key currently in this map.

            Specified by:
                - lastKey in interface SortedMap<K,V>
            Returns:
                - the last (highest) key currently in this map

            Throws:
            NoSuchElementException - if this map is empty
         */



        ///Separator
        System.out.println("_".repeat(50));
        LocalDate lastDate = datedPurchases.lastKey(); //2024-01-19

         /*
           public Map.Entry<K,V> lastEntry()
           .................................

            Description copied from interface: NavigableMap

             - Returns a key-value mapping associated with the greatest key in this map, or null if the map is empty.

            Specified by:
             - lastEntry in interface NavigableMap<K,V>

            Returns:
            an entry with the greatest key, or null if this map is empty

            Since:
            1.6
         */

        var previousEntry = datedPurchases.lastEntry();
        List<Purchase> lastDaysData = previousEntry.getValue();
        System.out.println("purchases - "+lastDaysData.size()); // purchases - 1


        /*

           public K lowerKey(K key)

            Description copied from interface: NavigableMap
                - Returns the greatest key strictly less than the given key, or null if there is no such key.

            Specified by:
                - lowerKey in interface NavigableMap<K,V>

            Parameters:
            key - the key

            Returns:
            the greatest key less than key, or null if there is no such key

            Throws:
            ClassCastException - if the specified key cannot be compared with the keys currently in the map
            NullPointerException - if the specified key is null and this map uses natural ordering, or its comparator does not permit null keys

            Since:
            1.6
         */
        ///Separator
        System.out.println("_".repeat(50));

        while(previousEntry != null){
            List<Purchase> lastDayData = previousEntry.getValue();
            System.out.println(lastDate +" purchases - "+lastDayData.size());

            LocalDate prevDate = datedPurchases.lowerKey(lastDate);
            previousEntry = datedPurchases.lowerEntry(lastDate);
            lastDate = prevDate;
        }

        /*
            public NavigableMap<K,V> descendingMap()

            Description copied from interface: NavigableMap
                - Returns a reverse order view of the mappings contained in this map.
                  The descending map is backed by this map, so changes to the map are reflected in the descending map, and vice-versa.
                  If either map is modified while an iteration over a collection view of either map is in progress (except through the iterator's own remove operation), the results of the iteration are undefined.
                - The returned map has an ordering equivalent to Collections.reverseOrder(comparator()).
                  The expression m.descendingMap().descendingMap() returns a view of m essentially equivalent to m.

            Specified by:
                descendingMap in interface NavigableMap<K,V>

            Returns:
                a reverse order view of this map

            Since:1.6
         */

        ///Separator
        System.out.println("_".repeat(50));
        var reversed = datedPurchases.descendingMap();
        reversed.forEach((k,v)-> System.out.println("key = "+ k + " value = "+v));


        /*

        public K firstKey()

            Description copied from interface: SortedMap
                - Returns the first (lowest) key currently in this map.

            Specified by:
                - firstKey in interface SortedMap<K,V>

            Returns:
                - the first (lowest) key currently in this map

            Throws:
            NoSuchElementException - if this map is empty
         */
        ///Separator
        System.out.println("_".repeat(50));

        LocalDate firstDate = reversed.firstKey(); //2024-01-19

        /*
            public Map.Entry<K,V> firstEntry()
            ...................................

            Description copied from interface: NavigableMap
                - Returns a key-value mapping associated with the least key in this map, or null if the map is empty.

            Specified by:
                - firstEntry in interface NavigableMap<K,V>

            Returns:
                - an entry with the least key, or null if this map is empty

            Since: 1.6

            ////////////////////////////////////////////////////////


            public K higherKey(K key)
            ............................

            Description copied from interface: NavigableMap
                 - Returns the least key strictly greater than the given key, or null if there is no such key.

            Specified by:
                - higherKey in interface NavigableMap<K,V>

            Parameters:
                - key - the key

            Returns:
                - the least key greater than key, or null if there is no such key

            Throws:
            ClassCastException - if the specified key cannot be compared with the keys currently in the map
            NullPointerException - if the specified key is null and this map uses natural ordering, or its comparator does not permit null keys

            Since:1.6

            /////////////////////////////////////////////////////////////


            public Map.Entry<K,V> higherEntry(K key)
            ...........................................

            Description copied from interface: NavigableMap
                - Returns a key-value mapping associated with the least key strictly greater than the given key, or null if there is no such key.

            Specified by:
                - higherEntry in interface NavigableMap<K,V>

            Parameters:
                - key - the key

            Returns:
                - an entry with the least key greater than key, or null if there is no such key

            Throws:
                ClassCastException - if the specified key cannot be compared with the keys currently in the map
                NullPointerException - if the specified key is null and this map uses natural ordering, or its comparator does not permit null keys

            Since: 1.6

        */
        ///Separator
        System.out.println("_".repeat(50));
        var nextEntry = reversed.firstEntry();
        while(nextEntry != null){

            List<Purchase> lastDayData = nextEntry.getValue();
            System.out.println(firstDate +" purchases - "+lastDayData.size());

            LocalDate nextDate = reversed.higherKey(firstDate);
            nextEntry = reversed.higherEntry(firstDate);
            firstDate = nextDate;
        }


        ///Separator
        System.out.println("_".repeat(50));

        //Print Original map
        datedPurchases.forEach((key,value)-> System.out.println(key + ": "+value));


    }

    private static void addPurchase(String name, Course course, double price){
        Student existingStudent = students.get(name);
        if(existingStudent == null){
            existingStudent = new Student(name,course);
            students.put(name,existingStudent);
        }else{
            existingStudent.addCourse(course);
        }

        int day = new Random().nextInt(1,20);
        String key = course.courseId()+ "_"+ existingStudent.getId();
        int year = LocalDate.now().getYear();
        Purchase purchase = new Purchase(course.courseId(), existingStudent.getId(),
                price,year,day);
        purchases.put(key,purchase);
    }

    private static void displayStats(int period , Map<LocalDate,List<Purchase>> periodData){
        System.out.println("_".repeat(50));

        //Keeps track of course iDS and the no. of purchases for each
        // Types are
            //  String for courseID
            //  Integer for course counts
        // use TreeMap() since we want this sorted
        Map<String,Integer> weeklyCounts = new TreeMap<>();

        //loop through each period data
        periodData.forEach((key,value)-> {
            System.out.println(key + ": "+value);
            //loop through purchases since it's a list variable
            for (Purchase p: value) {
                weeklyCounts.merge(p.courseId(),
                                1,
                                (prev,curr)->{
                    return prev + curr;
                });
            }
        });

        //Print Weekly Counts
        System.out.printf("Week %d Purchases = %s%n",period,weeklyCounts);

    }
}
