package collections.part11_set_operations_challenge;


import java.util.*;

public class Main {

    public static void main(String[] args) {
        Set<Task> tasks = TaskData.getTasks("all");
        sortAndPrint("All Tasks",tasks);

        //Comparator - sortByPriority
        Comparator<Task> sortByPriority = Comparator.comparing(Task::getPriority);

        //Ann's Task
        Set<Task> annsTasks = TaskData.getTasks("Ann");
        sortAndPrint("Ann's Tasks",annsTasks,sortByPriority);

        //Bob's , Carol Task
        Set<Task> bobsTasks = TaskData.getTasks("Bob");
        Set<Task> carolsTasks = TaskData.getTasks("Carol");

        //List of these sets
        List<Set<Task>> sets = List.of(annsTasks,bobsTasks,carolsTasks);

        //Assigned Tasks
        Set<Task> assignedTasks = getUnion(sets);
        sortAndPrint("Assigned Tasks",assignedTasks);

        //All Tasks.. Assigned and Unassigned
        Set<Task> everyTask = getUnion(List.of(tasks,assignedTasks));
        sortAndPrint("The True All Tasks",everyTask);

        Set<Task> missingTasks = getDifference(everyTask,tasks);
        sortAndPrint("Missing Tasks",missingTasks);

        //Unassigned Tasks
        Set<Task> unassignedTasks = getDifference(tasks,assignedTasks);
        sortAndPrint("Unassigned Tasks",unassignedTasks,sortByPriority);

        //Get Overlapping Tasks - task assigned to different members
        Set<Task> overlap = getUnion(List.of(
                getIntersect(annsTasks , bobsTasks),
                getIntersect(bobsTasks , carolsTasks),
                getIntersect(annsTasks , carolsTasks)
        ));
        sortAndPrint("Assigned to Multiples",overlap,sortByPriority);

        ////
        List<Task> overlapping = new ArrayList<>();
        for (Set<Task> set: sets ) {
            Set<Task> dupes = getIntersect(set,overlap);
            overlapping.addAll(dupes);
        }

        //Sort by Natural Order Comparator - Sort by priority, project then description
        Comparator<Task> priorityNatural = sortByPriority.thenComparing(Comparator.naturalOrder());
        sortAndPrint("Overlapping",overlapping,priorityNatural);

    }
    private static void sortAndPrint(String header, Collection<Task> collection){
        sortAndPrint(header,collection,null);
    }

    private static void sortAndPrint(String header, Collection<Task> collection,
                                     Comparator<Task> sorter){
        String lineSeparator = "_".repeat(90);
        System.out.println(lineSeparator);
        System.out.println(header);
        System.out.println(lineSeparator);

        //Use List
        List<Task> list = new ArrayList<>(collection);
        list.sort(sorter);
        list.forEach(System.out::println);
    }


    /////////////////////////////////
    // SET OPERATIONS CHALLENGE /////

    private static Set<Task> getUnion(List<Set<Task>> sets){
        Set<Task> union = new HashSet<>();
        for (var taskSet: sets ) {
            union.addAll(taskSet);
        }
        return union;
    }

    private static Set<Task> getIntersect(Set<Task> a , Set<Task> b){
        Set<Task> intersect = new HashSet<>(a);
        intersect.retainAll(b);
        return intersect;
    }

    private static Set<Task> getDifference(Set<Task> a , Set<Task> b){
        Set<Task> result = new HashSet<>(a);
        result.removeAll(b);
        return result;
    }
}
