package array_lists.array_lists_part1;

public record GroceryItem(String name,String type, int count) {

    public GroceryItem(String name) {
        this(name, "DAIRY", 1);
    }
}
