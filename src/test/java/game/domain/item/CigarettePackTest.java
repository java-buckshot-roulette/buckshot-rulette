package game.domain.item;

import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;
import static game.domain.bullet.Type.BLUE;
import static game.domain.bullet.Type.RED;

import game.domain.LifeAndDeath;
import game.domain.bullet.Bullet;
import game.domain.healthpoint.HealthPoint;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import game.dto.PlayerDataDto;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CigarettePackTest {
    @Test
    void 시전자_체력_회복() {
        //given
        PlayerDataDto caster = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        PlayerDataDto target = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        GameStateDto gameStateDto = new GameStateDto(List.of(new Bullet(RED), new Bullet(BLUE)),
                List.of(CHALLENGER, DEALER));

        ItemUsageRequestDto itemUsageRequestDto = new ItemUsageRequestDto(caster, target, gameStateDto);

        //when
        Item cigarettePack = new CigarettePack();
        ItemUsageRequestDto newItemRequest = cigarettePack.useItem(itemUsageRequestDto);

        //then
        Assertions.assertThat(newItemRequest
                        .caster()
                        .healthPoint())
                .isEqualTo(new HealthPoint(9));
    }

}