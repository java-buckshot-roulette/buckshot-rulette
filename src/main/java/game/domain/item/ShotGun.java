package game.domain.item;

import game.domain.healthpoint.HealthPoint;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;

public class ShotGun implements Item{

    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {

        HealthPoint discountPoint = getDamage(itemUsageRequestDto);

        GameStateDto newGameStateData = removeFirstBullet(itemUsageRequestDto);

        return itemUsageRequestDto.changeTargetData(itemUsageRequestDto
                        .target()
                        .discountHealthPoint(discountPoint))
                .changeGameData(newGameStateData)
                .reduceCasterItem(this);
    }

    private HealthPoint getDamage(ItemUsageRequestDto itemUsageRequestDto) {
        return new HealthPoint(itemUsageRequestDto
                .gameDataDto()
                .bullets()
                .getFirstBulletDamage());
    }

    private GameStateDto removeFirstBullet(ItemUsageRequestDto itemUsageRequestDto) {
        return itemUsageRequestDto.gameDataDto()
                .changeBullets(itemUsageRequestDto
                        .gameDataDto()
                        .bullets()
                        .removeFirst());
    }
}
