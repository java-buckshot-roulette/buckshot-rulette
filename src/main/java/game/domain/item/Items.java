package game.domain.item;

import java.util.ArrayList;
import java.util.List;

public class Items {
    private static final int MAX_SIZE = 8;
    private final List<Item> values;

    public Items(List<Item> items) {
        this.values = items;
    }

    public Items addItems(List<Item> items) {
        List<Item> combined = new ArrayList<>(values);
        combined.addAll(
                items.stream()
                        .limit(MAX_SIZE - values.size())
                        .toList()
        );
        return new Items(combined);
    }

    public boolean contains(Item item) {
        return values.contains(item);
    }
}
