package game.util;

import game.domain.item.Item;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randoms {
    private static final Random defaultRandom = ThreadLocalRandom.current();

    private Randoms() {
    }

    public static Optional<Item> pickItemInList(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return Optional.empty();
        }
        int randomIndex = defaultRandom.nextInt(items.size());
        return Optional.ofNullable(items.get(randomIndex));
    }

}
