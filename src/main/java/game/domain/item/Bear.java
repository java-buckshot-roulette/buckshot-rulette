package game.domain.item;

import static game.domain.item.ItemType.BEAR;

import game.domain.bullet.Bullets;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import java.util.Objects;

public class Bear implements Item {
    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {
        GameStateDto gameStateDto = itemUsageRequestDto.gameDataDto();
        Bullets newBullets = gameStateDto.bullets().removeFirst();

        return itemUsageRequestDto
                .changeGameData(gameStateDto.changeBullets(newBullets))
                .reduceCasterItem(this);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }

    @Override
    public String toString() {
        return BEAR.getName();
    }
}
