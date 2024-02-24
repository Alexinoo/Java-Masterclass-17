package generics_extra.generics_extra_challenge;

import generics_extra.generics_extra_challenge.util.QueryItem;

public record Employee(String name) implements QueryItem {
    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        return false;
    }
}
