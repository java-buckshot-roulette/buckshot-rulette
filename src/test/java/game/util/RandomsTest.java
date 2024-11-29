package game.util;

import static game.domain.item.ItemType.BEAR;

import game.domain.bullet.Bullet;
import game.domain.bullet.BulletConfig;
import game.domain.bullet.Type;
import game.domain.item.Item;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomsTest {
    @Test
    void 아이템_생성() {
        //given
        Item bear = BEAR.getInstance();
        List<Item> item = List.of(bear);

        //when
        Optional<Item> generatedItem = Randoms.pickItemInList(item);

        //then
        Assertions.assertThat(generatedItem)
                .isPresent()
                .contains(bear);
    }

    @Test
    void 탄환_생성() {
        //given
        Bullet redBullet = new Bullet(Type.RED);
        List<Bullet> bullet = List.of(redBullet);

        //when
        Optional<Bullet> generatedBullet = Randoms.pickBulletInList(bullet);

        //then
        Assertions.assertThat(generatedBullet)
                .isPresent()
                .contains(redBullet);
    }

    @Test
    void 무작위_셔플() {
        //given
        List<Bullet> bullet = List.of(BulletConfig.RED, BulletConfig.BLUE, BulletConfig.RED, BulletConfig.BLUE);

        //when
        List<Bullet> newBullet = Randoms.shuffle(bullet);

        //then
        Assertions.assertThat(bullet)
                .isNotEqualTo(newBullet);
    }
}