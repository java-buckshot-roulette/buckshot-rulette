package game.dto;

import game.domain.Role;
import game.domain.bullet.Bullet;
import game.domain.bullet.Bullets;
import game.domain.turn.Turns;
import java.util.ArrayList;
import java.util.List;

public record GameStateDto(Bullets bullets, Turns turns) {

    public GameStateDto changeBullets(Bullets newBullets) {
        return new GameStateDto(newBullets, this.turns);
    }

    public GameStateDto changeTurns(Turns newTurns) {
        return new GameStateDto(this.bullets, newTurns);
    }

    //Todo: 일급 컬렉션으로 리펙터링 하자
    public GameStateDto passTurn() {
        return changeTurns(turns.passTurn());
    }
}
