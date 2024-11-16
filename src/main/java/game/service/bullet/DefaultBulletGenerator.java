package game.service.bullet;

import static game.util.Randoms.pickBulletInList;

import game.domain.bullet.Bullet;
import game.domain.bullet.Type;
import game.domain.item.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DefaultBulletGenerator implements BulletGenerator{
    @Override
    public List<Bullet> generateBullet(int size) {
        List<Bullet> bullets = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Optional<Bullet> item = pickBulletInList(
                    Arrays.stream(Type.values())
                    .map(Bullet::new)
                    .toList());
            item.ifPresent(bullets::add);
        }
        return bullets;
    }
}
