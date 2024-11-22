package game.domain.item;

import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;
import static game.domain.bullet.BulletConfig.BLUE;
import static game.domain.bullet.BulletConfig.RED;
import static org.junit.jupiter.api.Assertions.*;

import game.domain.LifeAndDeath;
import game.domain.bullet.Bullet;
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

class InverterTest {
    @Test
    void 빨간탄을_파란탄으로_교체() {
        //given
        PlayerDataDto caster = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        PlayerDataDto target = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        GameStateDto gameStateDto = new GameStateDto(new Bullets(List.of(RED)), Turns.initialLialTurns());

        ItemUsageRequestDto itemUsageRequestDto = new ItemUsageRequestDto(caster, target, gameStateDto);

        //when
        Item inverter = new Inverter();
        ItemUsageRequestDto newItemRequest = inverter.useItem(itemUsageRequestDto);

        //then
        Assertions.assertThat(newItemRequest
                        .gameDataDto()
                        .bullets())
                .isEqualTo(new Bullets(List.of(BLUE)));
    }

    @Test
    void 푸른탄을_빨간탄으로_교체() {
        //given
        PlayerDataDto caster = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        PlayerDataDto target = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        GameStateDto gameStateDto = new GameStateDto(new Bullets(List.of(RED, BLUE)), Turns.initialLialTurns());

        ItemUsageRequestDto itemUsageRequestDto = new ItemUsageRequestDto(caster, target, gameStateDto);

        //when
        Item inverter = new Inverter();
        ItemUsageRequestDto newItemRequest = inverter.useItem(itemUsageRequestDto);

        //then
        Assertions.assertThat(newItemRequest
                        .gameDataDto()
                        .bullets())
                .isEqualTo(new Bullets(List.of(BLUE, BLUE)));
    }

}