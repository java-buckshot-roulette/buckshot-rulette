package game.domain.item;

import game.domain.bullet.Bullet;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import java.util.ArrayList;
import java.util.List;

public class Bear implements Item{
    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {
        GameStateDto gameStateDto = itemUsageRequestDto.gameDataDto();
        List<Bullet> newBullets = new ArrayList<>(gameStateDto.bullets());
        newBullets.removeFirst();
        return itemUsageRequestDto
                .changeGameData(gameStateDto.changeBullets(newBullets));
    }
}
