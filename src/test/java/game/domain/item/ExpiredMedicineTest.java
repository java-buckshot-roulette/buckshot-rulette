package game.domain.item;

import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;
import static game.domain.bullet.BulletConfig.BLUE;
import static game.domain.bullet.BulletConfig.RED;
import static org.junit.jupiter.api.Assertions.*;

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

class ExpiredMedicineTest {
    @Test
    void 상한약을_사용한이후_목숨이_이전과_달라짐() {
        //given
        PlayerDataDto caster = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        PlayerDataDto target = new PlayerDataDto(new HealthPoint(8), new Items(Collections.emptyList()),
                LifeAndDeath.LIFE);

        GameStateDto gameStateDto = new GameStateDto(new Bullets(List.of(RED, BLUE)), Turns.initialLialTurns());

        ItemUsageRequestDto itemUsageRequestDto = new ItemUsageRequestDto(caster, target, gameStateDto);

        //when
        Item expiredMedicine = new ExpiredMedicine();
        ItemUsageRequestDto newItemRequest = expiredMedicine.useItem(itemUsageRequestDto);

        //then
        Assertions.assertThat(newItemRequest
                        .caster()
                        .healthPoint())
                .isNotEqualTo(new HealthPoint(8));
    }

}