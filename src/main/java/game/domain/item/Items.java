package game.domain.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Items {
    private static final int MAX_SIZE = 8;
    private final List<Item> values;

    public Items(List<Item> items) {
        this.values = items;
    }

    public Items add(Items items) {
        List<Item> combined = new ArrayList<>(values);
        combined.addAll(
                items.values
                        .stream()
                        .limit(MAX_SIZE - values.size())
                        .toList()
        );
        return new Items(combined);
    }

    public boolean contains(Item item) {
        return values.contains(item);
    }

    public Items reduceItem(Item item) {
        List<Item> newValues = new ArrayList<>(values);
        newValues.remove(item);
        return new Items(newValues);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Items items = (Items) o;
        return Objects.equals(values, items.values);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values);
    }

    @Override
    public String toString() {
        return "[" + values.stream()
                .map(Item::toString)
                .collect(Collectors.joining(", ")) + "]";
    }
}
