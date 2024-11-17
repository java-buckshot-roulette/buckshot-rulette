package game.domain.bullet;

import game.util.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Bullets {
    private final List<Bullet> values;

    public Bullets(List<Bullet> values) {
        this.values = values;
    }

    public Bullets StrengthenFirstBullet() {
        List<Bullet> newBullets = getBullets();
        Bullet newBullet = newBullets.getFirst().doubleUpDamage();
        newBullets.addFirst(newBullet);

        return new Bullets(newBullets);
    }

    public Bullets removeFirst() {
        List<Bullet> newBullets = getBullets();
        newBullets.removeFirst();
        return new Bullets(newBullets);
    }

    public Bullets invertFirstBullet() {
        List<Bullet> newBullets = getBullets();
        Bullet first = newBullets.getFirst().invertType();
        newBullets.removeFirst();
        newBullets.addFirst(first);
        return new Bullets(newBullets);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public void reload(List<Bullet> bullets) {
        values.addAll(bullets);
    }

    private List<Bullet> getBullets() {
        return new ArrayList<>(values);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bullets bullets = (Bullets) o;
        return Objects.equals(values, bullets.values);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values);
    }

    public int getFirstBulletDamage() {
        return values.getFirst()
                .getDamage();
    }

    @Override
    public String toString() {
        return "생성된 탄알: [" + Randoms.shuffle(values)
                .stream()
                .map(Bullet::toString)
                .collect(Collectors.joining(", ")) + "]";
    }
}
