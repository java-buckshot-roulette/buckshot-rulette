package game.service.item;

import game.domain.bullet.Bullet;
import game.dto.GameDataDto;

public interface UsingItemService {
    void loadBullet(Bullet bullet);
    GameDataDto useItem(String itemName);

}
