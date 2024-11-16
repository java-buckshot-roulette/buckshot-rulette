package game.domain.bullet;

public class Bullet {
    private final Type type;
    private final int damage;

    public Bullet(Type type) {
        this.type = type;
        this.damage = type.initialDamage();
    }

}
