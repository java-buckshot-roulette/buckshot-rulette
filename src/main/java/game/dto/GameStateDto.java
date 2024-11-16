package game.dto;

import game.domain.Role;
import game.domain.bullet.Bullet;
import java.util.List;

public record GameStateDto(List<Bullet> bullets, List<Role> turns) {
    public GameStateDto changeBullets(List<Bullet> newBullets) {
        return new GameStateDto(newBullets, this.turns);
    }

    public GameStateDto changeTurns(List<Role> newTurns) {
        return new GameStateDto(this.bullets, newTurns);
    }
}
