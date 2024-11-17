package game.domain;

import game.domain.healthpoint.HealthPoint;
import game.domain.item.Item;
import game.domain.item.Items;
import game.dto.PlayerDataDto;

public class Player {
    private final Role role;
    private final Items items;
    private final HealthPoint healthPoint;
    private final LifeAndDeath lifeAndDeath;

    public Player(Role role, Items items, HealthPoint healthPoint, LifeAndDeath lifeAndDeath) {
        this.role = role;
        this.items = items;
        this.healthPoint = healthPoint;
        this.lifeAndDeath = lifeAndDeath;
    }

    public PlayerDataDto makePlayerDataDto() {
        return new PlayerDataDto(healthPoint, items, lifeAndDeath);
    }

    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    public Role getRole() {
        return role;
    }

    public Player applyEffect(PlayerDataDto newPlayerData) {
        return new Player(role, newPlayerData.items(), newPlayerData.healthPoint(), newPlayerData.lifeAndDeath());
    }

}
