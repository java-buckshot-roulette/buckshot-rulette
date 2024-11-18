package game.service.bullet;

import static game.domain.bullet.BulletConfig.BLUE;
import static game.domain.bullet.BulletConfig.RED;
import static game.util.Randoms.pickBulletInList;
import static game.util.Randoms.shuffle;

import game.domain.bullet.Bullet;
import game.domain.bullet.BulletConfig;
import game.domain.bullet.Bullets;
import game.domain.bullet.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DefaultBulletGenerator implements BulletGenerator{
    private static final List<Bullet> BULLETS_SET = List.of(RED, RED, RED, BLUE);    //뽑을 탄환의 꾸러미 Todo: dependency 에서 관리할지 고민
    @Override
    public List<Bullet> generateBullet(int size) {
        List<Bullet> bullets = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Optional<Bullet> item = pickBulletInList(BULLETS_SET);
            item.ifPresent(bullets::add);
        }
        return shuffle(bullets);
    }
}
