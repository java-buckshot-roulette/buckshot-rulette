package game.domain.item;


import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;
import static game.domain.bullet.BulletConfig.*;
import static game.domain.bullet.BulletConfig.RED;

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

class BearTest {
    @Test
    void 탄환_제거() {
        //given
        PlayerDataDto caster = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        PlayerDataDto target = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        GameStateDto gameStateDto = new GameStateDto(new Bullets(List.of(RED, BLUE)),
                List.of(CHALLENGER, DEALER));

        ItemUsageRequestDto itemUsageRequestDto = new ItemUsageRequestDto(caster, target, gameStateDto);

        //when
        Item bear = new Bear();
        ItemUsageRequestDto newItemRequest = bear.useItem(itemUsageRequestDto);

        //then
        Assertions.assertThat(newItemRequest
                        .gameDataDto()
                        .bullets())
                .isEqualTo(new Bullets(List.of(BLUE)));
    }

}