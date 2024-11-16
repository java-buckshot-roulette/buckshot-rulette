package game.domain.item;

import game.domain.bullet.Bullet;
import game.domain.bullet.Bullets;
import game.dto.ItemUsageRequestDto;

public class Inverter implements Item{
    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {
        Bullets newBullets = itemUsageRequestDto.gameDataDto().bullets().invertFirstBullet();
        return itemUsageRequestDto
                .changeGameData(itemUsageRequestDto
                        .gameDataDto()
                        .changeBullets(newBullets))
                .reduceCasterItem(this);
    }
}
