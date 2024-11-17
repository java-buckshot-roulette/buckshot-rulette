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

    @Override
    public String toString() {
        if(this.equals(RED)) {
            return "빨강";
        }
        return "파랑";
    }
}
