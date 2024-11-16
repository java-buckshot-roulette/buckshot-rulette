package game.domain.bullet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
}