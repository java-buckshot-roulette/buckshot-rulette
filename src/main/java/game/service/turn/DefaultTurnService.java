package game.service.turn;

import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;

import game.domain.Role;
import java.util.List;

public class DefaultTurnService implements TurnService {
    private static final List<Role> INITIAL_TURNS = List.of(CHALLENGER, DEALER);
    private List<Role> turns;

    public DefaultTurnService(List<Role> turns) {
        this.turns = turns;
    }

    @Override
    public Role getTurn() {
        return turns.getFirst();
    }

    public void initializeTurn() {
        turns.clear();
        for (int i = 0; i < 10; i++) {  // Todo: 매직넘버 제거 방법 고민하기
            turns.addAll(INITIAL_TURNS);
        }
    }

    @Override
    public List<Role> requestTurns() {
        return turns;
    }

    @Override
    public void applyTurns(List<Role> turns) {
        this.turns = turns;
    }

    @Override
    public void proceedTurn(TurnAction turnAction) {
        Role currentTurn = turns.getFirst(); // 현재 턴 가져오기
        turnAction.execute(currentTurn); // 현재 턴의 동작 실행
        passTurn(); // 턴 전환
    }

    private void passTurn() {
        turns.removeFirst();
    }
}