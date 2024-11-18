package game.domain.healthpoint;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HealthPointTest {
    @Test
    void 체력_1만큼_회복() {
        //given
        HealthPoint healthPoint = new HealthPoint(1);

        //when
        healthPoint = healthPoint.heal(healthPoint);

        //then
        Assertions.assertThat(healthPoint).isEqualTo(new HealthPoint(2));
    }

    @Test
    void 체력_1만큼_감소() {
        //given
        HealthPoint healthPoint = new HealthPoint(2);

        //when
        healthPoint = healthPoint.discount(new HealthPoint(1));

        //then
        Assertions.assertThat(healthPoint).isEqualTo(new HealthPoint(1));
    }

}