package game.service.player;

import game.domain.healthpoint.HealthPoint;
import game.domain.item.Items;

public interface PlayerService {
    void storeItems(Items items);
    void recoveryHealthPoint(HealthPoint healthPoint);
    void sufferDamage(HealthPoint healthPoint);
}
