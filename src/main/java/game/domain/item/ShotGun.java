package game.domain.item;

import static game.domain.LifeAndDeath.DEATH;
import static game.domain.item.ItemType.SHOT_GUN;

import game.domain.Role;
import game.domain.healthpoint.HealthPoint;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;

public class ShotGun implements Item {

    private static final HealthPoint ZERO_DAMAGE = new HealthPoint(0);

    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {

        HealthPoint discountPoint = getFirstBulletDamage(itemUsageRequestDto);

        GameStateDto newGameStateData = removeFirstBullet(itemUsageRequestDto);

        // 시전자와 타겟이 같을 경우 공포탄이면 턴을 지속한다.
        if (itemUsageRequestDto.caster().equals(itemUsageRequestDto.target()) && discountPoint.equals(ZERO_DAMAGE)) {
            newGameStateData = newGameStateData.keepTurn();
        }

        return itemUsageRequestDto.changeTargetData(getShotTarget(itemUsageRequestDto, discountPoint))
                .changeGameData(newGameStateData);
    }

    private PlayerDataDto getShotTarget(ItemUsageRequestDto itemUsageRequestDto, HealthPoint discountPoint) {
        PlayerDataDto shotTarget = itemUsageRequestDto
                .target()
                .discountHealthPoint(discountPoint);

        if (discountPoint.isGreaterThanOne()) {
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

    @Override
    public String toString() {
        return SHOT_GUN.getName();
    }
}
