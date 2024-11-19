package game.domain.item;

import static game.domain.LifeAndDeath.DEATH;

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
        // TODO: 리펙터링 필요함 수갑이랑 역할이 곂침, turnService 의 역할이 너무 없음 -> 차라리 일급 컬렉션?
        if (itemUsageRequestDto.caster().equals(itemUsageRequestDto.target()) && discountPoint.equals(ZERO_DAMAGE)) {
            Role first = newGameStateData.turns().getFirst();
            newGameStateData.turns().addFirst(first);
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


}
