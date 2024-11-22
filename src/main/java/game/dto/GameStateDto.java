package game.dto;

import game.domain.bullet.Bullets;
import game.domain.turn.Turns;

public record GameStateDto(Bullets bullets, Turns turns) {

    public static GameStateDto of(Bullets bullets, Turns turns) {
        return new GameStateDto(bullets, turns);
    }

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
