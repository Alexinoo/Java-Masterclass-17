package collections.part20_enum_colections;

import java.util.*;

public class Main {

    enum WeekDay {SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY}

    public static void main(String[] args) {

        List<WeekDay> annsWorkDays = new ArrayList<>(List.of(WeekDay.MONDAY,
                    WeekDay.TUESDAY,WeekDay.THURSDAY,WeekDay.FRIDAY));

        /*

        public static <E extends Enum<E>> EnumSet<E> copyOf(EnumSet<E> s)

        Description:
            Creates an enum set with the same element type as the specified enum set, initially containing the same elements (if any).

        Type Parameters:
             E - The class of the elements in the set

        Parameters:
             s - the enum set from which to initialize this enum set

        Returns:
             A copy of the specified enum set.

        Throws:
            NullPointerException - if s is null
         */

        var annsDaysSet = EnumSet.copyOf(annsWorkDays);
        System.out.println(annsDaysSet.getClass().getSimpleName()); //RegularEnumSet

        //print Ann Work Days
        annsDaysSet.forEach(System.out::println);

        /*

            public static <E extends Enum<E>> EnumSet<E> allOf(Class<E> elementType)

            Description:
                - Creates an enum set containing all of the elements in the specified element type.

            Type Parameters:
                - E - The class of the elements in the set

            Parameters:
                - elementType - the class object of the element type for this enum set

            Returns:
                - An enum set containing all the elements in the specified type.

            Throws:
                NullPointerException - if elementType is null
         */
        System.out.println("_".repeat(50));

        var allDaysSet = EnumSet.allOf(WeekDay.class);
        allDaysSet.forEach(System.out::println);

        /*

            public static <E extends Enum<E>> EnumSet<E> complementOf(EnumSet<E> s)

            Description:
            - Creates an enum set with the same element type as the specified enum set,
            initially containing all the elements of this type that are not contained in the specified set.

            Type Parameters: E - The class of the elements in the enum set

            Parameters: s - the enum set from whose complement to initialize this enum set

            Returns: The complement of the specified set in this set

            Throws: NullPointerException - if s is null
         */
        System.out.println("_".repeat(50));

        Set<WeekDay> newPersonDays = EnumSet.complementOf(annsDaysSet);
        newPersonDays.forEach(System.out::println);

        // separator
        System.out.println("_".repeat(50));

        // removeAll - inherited from class java.util.AbstractSet - Works as complemetOf()
        Set<WeekDay> anotherWay = EnumSet.copyOf(allDaysSet);
        anotherWay.removeAll(annsDaysSet);
        anotherWay.forEach(System.out::println);

        /*
            public static <E extends Enum<E>> EnumSet<E> range(E from,E to)

            Description:
                - Creates an enum set initially containing all of the elements in the range defined by the two specified endpoints.
                 The returned set will contain the endpoints themselves, which may be identical but must not be out of order.

            Type Parameters:
                E - The class of the parameter elements and of the set

            Parameters:
                from - the first element in the range
                to - the last element in the range
            Returns:
                an enum set initially containing all of the elements in the range defined by the two specified endpoints
            Throws:
                NullPointerException - if from or to are null
                IllegalArgumentException - if from.compareTo(to) > 0
         */
        // separator
        System.out.println("_".repeat(50));

        EnumSet<WeekDay> businessDays = EnumSet.range(WeekDay.MONDAY,WeekDay.FRIDAY);
        businessDays.forEach(System.out::println);

        /*
            public EnumMap(Class<K> keyType)

            Description:
                - Creates an empty enum map with the specified key type.

            Parameters:
                - keyType - the class object of the key type for this enum map

            Throws:
                NullPointerException - if keyType is null


         */
        // separator
        System.out.println("_".repeat(50));
        EnumMap<WeekDay, String[]> employeeSchedule = new EnumMap<>(WeekDay.class);
        employeeSchedule.put(WeekDay.FRIDAY,new String[]{"Ann","Mary","Bob"});
        employeeSchedule.put(WeekDay.MONDAY,new String[]{"Mary","Bob"});
        employeeSchedule.forEach((k,v)-> System.out.println(k + " : "+Arrays.toString(v)));


    }
}
