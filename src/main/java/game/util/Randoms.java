package game.util;

import game.domain.bullet.Bullet;
import game.domain.item.HealOrDiscount;
import game.domain.item.Item;
import java.util.ArrayList;
import java.util.Collections;
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

    public static Optional<Bullet> pickBulletInList(List<Bullet> bullets) {
        if (bullets == null || bullets.isEmpty()) {
            return Optional.empty();
        }
        int randomIndex = defaultRandom.nextInt(bullets.size());
        return Optional.ofNullable(bullets.get(randomIndex));
    }

    public static int pickNumberInRange(int startInclusive, int endInclusive) {
        return startInclusive + defaultRandom.nextInt(endInclusive - startInclusive + 1);
    }


    public static HealOrDiscount pickHealOrDiscount() {
        int randomIndex = defaultRandom.nextInt(HealOrDiscount.values().length);
        return HealOrDiscount.values()[randomIndex];
    }

    public static <T> List<T> shuffle(List<T> list) {
        List<T> result = new ArrayList(list);
        Collections.shuffle(result);
        return result;
    }
}
