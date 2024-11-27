package game.domain.bullet;

import static game.domain.bullet.BulletConfig.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BulletsTest {
    @Test
    void 재장전시_이전_상태의_총알이_제거된다() {
        //given
        Bullets bullets = new Bullets(List.of(RED));
        List<Bullet> generagtedBullets = List.of(BLUE, RED);

        //when
        bullets = bullets.reload(generagtedBullets);

        //then
        Assertions.assertThat(bullets)
                .isEqualTo(new Bullets(List.of(BLUE, RED)));
    }

}