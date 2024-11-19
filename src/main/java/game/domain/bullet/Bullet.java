package game.domain.bullet;

import java.util.Objects;

public class Bullet {
    private final Type type;
    private int damage;

    public Bullet(Type type) {
        this.type = type;
        this.damage = type.initialDamage();
    }

    public Bullet(Type type, int damage) {
        this.type = type;
        this.damage = damage;
    }

    public Bullet doubleUpDamage() {
        return new Bullet(this.type, this.damage *= 2);
    }

    public Bullet invertType() {
        if (type.equals(Type.RED)) {
            return new Bullet(Type.BLUE);
        }
        return new Bullet(Type.RED);
    }

    public int getDamage() {
        return damage;
    }

    public boolean isRed() {
        return type.equals(Type.RED);
    }

    public boolean isBlue() {
        return type.equals(Type.BLUE);
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

    @Override
    public String toString() {
        return type.toString();
    }
}
