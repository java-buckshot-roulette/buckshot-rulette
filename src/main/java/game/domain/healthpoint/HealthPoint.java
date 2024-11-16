package game.domain.healthpoint;

import java.util.Objects;

public class HealthPoint {
    private final int value;

    public HealthPoint(int value) {
        this.value = value;
    }

    public HealthPoint heal(HealthPoint healingPoint) {
        return new HealthPoint(this.value + healingPoint.value);
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
}
