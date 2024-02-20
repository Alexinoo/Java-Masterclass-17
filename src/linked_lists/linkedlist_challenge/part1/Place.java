package linked_lists.linkedlist_challenge.part1;

public record Place(String name, int distance) {
    @Override
    public String toString() {
        return String.format("%s (%d) ",name,distance);
    }
}
