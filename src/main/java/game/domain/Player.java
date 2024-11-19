package game.domain;

import game.domain.healthpoint.HealthPoint;
import game.domain.item.Item;
import game.domain.item.Items;
import game.dto.PlayerDataDto;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private final Role role;
    private final Items items;
    private final HealthPoint healthPoint;
    private final LifeAndDeath lifeAndDeath;


    public Player(String name, Role role, Items items, HealthPoint healthPoint, LifeAndDeath lifeAndDeath) {
        this.name = name;
        this.role = role;
        this.items = items;
        this.healthPoint = healthPoint;
        this.lifeAndDeath = lifeAndDeath;
    }

    public Player name(String name) {
        return new Player(name, role, items, healthPoint, lifeAndDeath);
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
        return new Player(name, role, newPlayerData.items(), newPlayerData.healthPoint(), newPlayerData.lifeAndDeath());
    }

    public Player addItem(List<Item> items) {
        Items addingItems = new Items(items);
        return new Player(name, role, this.items.add(addingItems), healthPoint, lifeAndDeath);
    }

    public Player initializeHealthPoint(HealthPoint initialValue) {
        return new Player(name, role, items, initialValue, lifeAndDeath);
    }

    public Player initializeItems() {
        return new Player(name, role, new Items(new ArrayList<>()), healthPoint, lifeAndDeath);
    }

    public String getName() {
        return name;
    }


}
