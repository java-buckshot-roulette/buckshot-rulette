package game.domain.bullet;

import java.util.Objects;

public class Bullet {
    private final Type type;
    private final int damage;

    public Bullet(Type type) {
        this.type = type;
        this.damage = type.initialDamage();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bullet bullet = (Bullet) o;
        return damage == bullet.damage && type == bullet.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, damage);
    }
}
