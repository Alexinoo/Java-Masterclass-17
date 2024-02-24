package generics_extra.staticmthds_multiupperbnds;

import generics_extra.staticmthds_multiupperbnds.util.QueryItem;

public record Employee(String name) implements QueryItem {
    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        return false;
    }
}
