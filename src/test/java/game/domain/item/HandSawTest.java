package game.domain.item;

import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;
import static game.domain.bullet.BulletConfig.*;


import game.domain.LifeAndDeath;
import game.domain.bullet.Bullets;
import game.domain.healthpoint.HealthPoint;
import game.domain.turn.Turns;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import game.dto.PlayerDataDto;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HandSawTest {
    @Test
    void 빨간탄의_데미지를_2로_강화() {
        //given
        PlayerDataDto caster = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        PlayerDataDto target = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        GameStateDto gameStateDto = new GameStateDto(new Bullets(List.of(RED, BLUE)), Turns.initialLialTurns());

        ItemUsageRequestDto itemUsageRequestDto = new ItemUsageRequestDto(caster, target, gameStateDto);

        //when
        Item HandSaw = new HandSaw();
        ItemUsageRequestDto newItemRequest = HandSaw.useItem(itemUsageRequestDto);

        //then
        Assertions.assertThat(newItemRequest
                        .gameDataDto()
                        .bullets()
                        .getFirstBulletDamage())
                .isEqualTo(2);
    }

    @Test
    void 푸른탄은_강화해도_데미지가_0이다() {
        //given
        PlayerDataDto caster = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        PlayerDataDto target = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        GameStateDto gameStateDto = new GameStateDto(new Bullets(List.of(BLUE)), Turns.initialLialTurns());

        ItemUsageRequestDto itemUsageRequestDto = new ItemUsageRequestDto(caster, target, gameStateDto);

        //when
        Item HandSaw = new HandSaw();
        ItemUsageRequestDto newItemRequest = HandSaw.useItem(itemUsageRequestDto);

        //then
        Assertions.assertThat(newItemRequest
                        .gameDataDto()
                        .bullets()
                        .getFirstBulletDamage())
                .isEqualTo(0);
    }

}