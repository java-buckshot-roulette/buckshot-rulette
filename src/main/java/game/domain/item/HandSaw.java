package game.domain.item;

import static game.domain.item.ItemType.HAND_SAW;

import game.domain.bullet.Bullets;
import game.dto.ItemUsageRequestDto;

public class HandSaw implements Item {
    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {
        Bullets newBullets = strengthenFirstBullet(itemUsageRequestDto);

        return itemUsageRequestDto.changeGameData(itemUsageRequestDto
                        .gameDataDto()
                        .changeBullets(newBullets))
                .reduceCasterItem(this);
    }

    private static Bullets strengthenFirstBullet(ItemUsageRequestDto itemUsageRequestDto) {
        return itemUsageRequestDto
                .gameDataDto()
                .bullets()
                .strengthenFirstBullet();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }

    @Override
    public String toString() {
        return HAND_SAW.getName();
    }
}
