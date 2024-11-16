package game.dto;

import game.domain.Role;
import game.domain.bullet.Bullet;
import game.domain.bullet.Bullets;
import java.util.List;

public record GameStateDto(Bullets bullets, List<Role> turns) {
    public GameStateDto changeBullets(Bullets newBullets) {
        return new GameStateDto(newBullets, this.turns);
    }

    public GameStateDto changeTurns(List<Role> newTurns) {
        return new GameStateDto(this.bullets, newTurns);
    }
}
