package game.domain.item;

import static game.domain.LifeAndDeath.*;
import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;
import static game.domain.bullet.BulletConfig.BLUE;
import static game.domain.bullet.BulletConfig.RED;
import static org.junit.jupiter.api.Assertions.*;

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

class ShotGunTest {
    @Test
    void 빨간탄이_상대에게_피해를_준다() {
        //given
        PlayerDataDto caster = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LIFE);

        PlayerDataDto target = new PlayerDataDto(new HealthPoint(7), new Items(Collections.emptyList()),
                LIFE);

        GameStateDto gameStateDto = new GameStateDto(new Bullets(List.of(RED, BLUE)), List.of(CHALLENGER, DEALER));

        ItemUsageRequestDto itemUsageRequestDto = new ItemUsageRequestDto(caster, target, gameStateDto);

        //when
        Item shotGun = new ShotGun();
        ItemUsageRequestDto newItemRequest = shotGun.useItem(itemUsageRequestDto);

        //then
        Assertions.assertThat(newItemRequest
                        .target()
                        .healthPoint())
                .isEqualTo(new HealthPoint(6));
    }

    @Test
    void 샷건_사용후_탄환_감소() {
        //given
        PlayerDataDto caster = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LIFE);

        PlayerDataDto target = new PlayerDataDto(new HealthPoint(7), new Items(Collections.emptyList()),
                LIFE);

        GameStateDto gameStateDto = new GameStateDto(new Bullets(List.of(RED, BLUE)), List.of(CHALLENGER, DEALER));

        ItemUsageRequestDto itemUsageRequestDto = new ItemUsageRequestDto(caster, target, gameStateDto);

        //when
        Item shotGun = new ShotGun();
        ItemUsageRequestDto newItemRequest = shotGun.useItem(itemUsageRequestDto);

        //then
        Assertions.assertThat(newItemRequest
                        .gameDataDto()
                        .bullets())
                .isEqualTo(new Bullets(List.of(BLUE)));
    }

}