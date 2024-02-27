package lambda_expressions.part7_method_references;

public class PlainOld {

    private static int last_id = 1;

    private int id;

    public PlainOld() {
        id = PlainOld.last_id++;
        System.out.println("Creating a plainOld Object: id = "+id);
    }
}
