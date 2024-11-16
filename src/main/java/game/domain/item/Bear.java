package game.domain.item;

import game.domain.bullet.Bullets;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;

public class Bear implements Item {
    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {
        GameStateDto gameStateDto = itemUsageRequestDto.gameDataDto();
        Bullets newBullets = gameStateDto.bullets()
                .removeFirst();

        return itemUsageRequestDto
                .changeGameData(gameStateDto.changeBullets(newBullets))
                .reduceCasterItem(this);
    }
}
