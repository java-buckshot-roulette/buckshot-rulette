package game.service.bullet;

import game.domain.bullet.Bullet;
import java.util.List;

public interface BulletGenerator {
    List<Bullet> generateBullet(int size);
}
