package game.dto;

import game.domain.LifeAndDeath;
import game.domain.healthpoint.HealthPoint;
import game.domain.item.Item;
import game.domain.item.Items;

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
}
