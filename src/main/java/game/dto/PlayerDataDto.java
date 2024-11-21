package game.dto;

import game.domain.LifeAndDeath;
import game.domain.healthpoint.HealthPoint;
import game.domain.item.Item;
import game.domain.item.Items;
import java.util.Objects;

public record PlayerDataDto(HealthPoint healthPoint, Items items, LifeAndDeath lifeAndDeath) {
    public PlayerDataDto reduceItem(Item item) {
        return new PlayerDataDto(this.healthPoint, items.reduceItem(item), lifeAndDeath);
    }

    public PlayerDataDto healPlayer(HealthPoint healingPoint) {
        return new PlayerDataDto(this.healthPoint.heal(healingPoint), this.items, this.lifeAndDeath);
    }

    public PlayerDataDto discountHealthPoint(HealthPoint discountPoint) {
        return new PlayerDataDto(this.healthPoint.discount(discountPoint), this.items, this.lifeAndDeath);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlayerDataDto that = (PlayerDataDto) o;
        return this == that;
    }

    @Override
    public int hashCode() {
        return Objects.hash(healthPoint, items, lifeAndDeath);
    }
}
