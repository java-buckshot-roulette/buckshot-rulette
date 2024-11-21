package game.domain.healthpoint;

import static java.lang.Math.max;

import java.util.Objects;

public class HealthPoint {
    public static final int ZERO = 0;
    public static final int MAX = 6;

    private final int value;

    public HealthPoint(int value) {
        this.value = getVerifiedValue(value);
    }

    public static HealthPoint of(int value) {
        return new HealthPoint(value);
    }

    private int getVerifiedValue(int initialValue) {
        if(initialValue > MAX) {
            return MAX;
        }
        return max(initialValue, 0);
    }

    public HealthPoint heal(HealthPoint healingPoint) {
        return new HealthPoint(this.value + healingPoint.value);
    }

    public HealthPoint discount(HealthPoint discountPoint) {
        return new HealthPoint(this.value - discountPoint.value);
    }

    public boolean isGreaterThanOne() {
        return value >= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HealthPoint that = (HealthPoint) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "❤".repeat(value);
    }
}
