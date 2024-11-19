package game.domain.item;

import static game.domain.item.ItemType.INVERTER;

import game.domain.bullet.Bullets;
import game.dto.ItemUsageRequestDto;

public class Inverter implements Item {
    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {
        Bullets newBullets = itemUsageRequestDto.gameDataDto().bullets().invertFirstBullet();
        return itemUsageRequestDto
                .changeGameData(itemUsageRequestDto
                        .gameDataDto()
                        .changeBullets(newBullets))
                .reduceCasterItem(this);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }

    @Override
    public String toString() {
        return INVERTER.getName();
    }
}
