package game.domain.healthpoint;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HealthPointTest {
    @Test
    void 체력_1만큼_회복() {
        //given
        HealthPoint healthPoint = HealthPoint.of(1);

        //when
        healthPoint = healthPoint.heal(healthPoint);

        //then
        Assertions.assertThat(healthPoint).isEqualTo(new HealthPoint(2));
    }

    @Test
    void 체력_1만큼_감소() {
        //given
        HealthPoint healthPoint = HealthPoint.of(2);

        //when
        healthPoint = healthPoint.discount(HealthPoint.of(1));

        //then
        Assertions.assertThat(healthPoint).isEqualTo(HealthPoint.of(1));
    }

    @Test
    void 체력이_음수인_상태일_때_체력을_0으로_초기화() {
        //given
        HealthPoint healthPoint = HealthPoint.of(-1);

        //then
        Assertions.assertThat(healthPoint).isEqualTo(HealthPoint.of(HealthPoint.ZERO));
    }

    @Test
    void 체력이_6을_넘기면_6으로_초기화() {
        //given
        HealthPoint healthPoint = HealthPoint.of(HealthPoint.MAX + 1);

        //then
        Assertions.assertThat(healthPoint).isEqualTo(HealthPoint.of(HealthPoint.MAX));
    }

}