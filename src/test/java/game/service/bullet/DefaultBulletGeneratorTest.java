package game.service.bullet;

import game.domain.bullet.Bullets;
import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DefaultBulletGeneratorTest {
    @Test
    void 탄알_재장전() {
        //given
        DefaultBulletGenerator defaultBulletGenerator = new DefaultBulletGenerator();
        Bullets bullets = new Bullets(new ArrayList<>());

        //when
        bullets.reload(defaultBulletGenerator.generateBullet(3));

        //then
        Assertions.assertThat(bullets.isEmpty()).isFalse();
    }

}