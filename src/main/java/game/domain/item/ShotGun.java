package game.domain.item;

import static game.domain.LifeAndDeath.DEATH;

import game.domain.healthpoint.HealthPoint;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import game.dto.PlayerDataDto;

public class ShotGun implements Item{

    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {

        HealthPoint discountPoint = getFirstBulletDamage(itemUsageRequestDto);

        GameStateDto newGameStateData = removeFirstBullet(itemUsageRequestDto);

        return itemUsageRequestDto.changeTargetData(getShotTarget(itemUsageRequestDto, discountPoint))
                .changeGameData(newGameStateData);
    }

    private PlayerDataDto getShotTarget(ItemUsageRequestDto itemUsageRequestDto, HealthPoint discountPoint) {
        PlayerDataDto shotTarget = itemUsageRequestDto
                .target()
                .discountHealthPoint(discountPoint);

        if(discountPoint.isGreaterThanOne()) {
            return new PlayerDataDto(shotTarget.healthPoint(), shotTarget.items(), DEATH);
        }

        return shotTarget;
    }

    private HealthPoint getFirstBulletDamage(ItemUsageRequestDto itemUsageRequestDto) {
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
