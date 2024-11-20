package game.service.turn;

import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;

import game.domain.Role;
import game.domain.turn.Turns;
import java.util.List;

public class DefaultTurnService implements TurnService {
    private static final List<Role> INITIAL_TURNS = List.of(CHALLENGER, DEALER);
    private Turns turns;

    public DefaultTurnService(Turns turns) {
        this.turns = turns;
    }

    @Override
    public Role getTurn() {
        return turns.getCurrentTurn();
    }

    public void initializeTurn() {
        turns = Turns.initialLialTurns();
    }

    @Override
    public Turns requestTurns() {
        return turns;
    }

    @Override
    public void applyTurns(Turns turns) {
        this.turns = turns;
    }

    @Override
    public void proceedTurn(TurnAction turnAction) {
        Role currentTurn = turns.getCurrentTurn(); // 현재 턴 가져오기
        turnAction.execute(currentTurn); // 현재 턴의 동작 실행
        passTurn(); // 턴 전환
    }

    private void passTurn() {
        turns = turns.passTurn();
    }
}