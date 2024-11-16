package game.domain.bullet;

public enum Type {
    RED(1),
    BLUE(0);

    private final int initialDamage;

    Type(int damage) {
        this.initialDamage = damage;
    }

    public int initialDamage() {
        return this.initialDamage;
    }
}
