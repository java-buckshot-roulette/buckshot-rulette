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

    public GameStateDto passTurn() {
        return changeTurns(turns.passTurn());
    }

    public GameStateDto keepTurn() {
        return changeTurns(turns.KeepTurn());
    }
}
