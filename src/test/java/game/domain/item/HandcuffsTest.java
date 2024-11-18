package game.domain.item;

import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;
import static game.domain.bullet.BulletConfig.BLUE;

import game.domain.LifeAndDeath;
import game.domain.bullet.Bullets;
import game.domain.healthpoint.HealthPoint;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import game.dto.PlayerDataDto;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HandcuffsTest {
    @Test
    void 수갑사용시_턴을_다시_진행() {
        //given
        PlayerDataDto caster = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        PlayerDataDto target = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        GameStateDto gameStateDto = new GameStateDto(new Bullets(List.of(BLUE)), List.of(CHALLENGER, DEALER));

        ItemUsageRequestDto itemUsageRequestDto = new ItemUsageRequestDto(caster, target, gameStateDto);

        //when
        Item Handcuffs = new Handcuffs();
        ItemUsageRequestDto newItemRequest = Handcuffs.useItem(itemUsageRequestDto);

        //then
        Assertions.assertThat(newItemRequest
                        .gameDataDto()
                        .turns())
                .isEqualTo(List.of(CHALLENGER, CHALLENGER, DEALER));
    }

}