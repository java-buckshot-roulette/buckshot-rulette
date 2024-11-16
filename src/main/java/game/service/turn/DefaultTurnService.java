package game.service.turn;

import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;

import game.domain.Role;
import java.util.List;

public class DefaultTurnService implements TurnService {
    private static final List<Role> INITIAL_TURNS = List.of(CHALLENGER, DEALER);
    private final List<Role> turns;

    public DefaultTurnService(List<Role> turns) {
        this.turns = turns;
    }

    @Override
    public Role getTurn() {
        return turns.getFirst();
    }

    public void initializeTurn(int bulletSize) {
        turns.clear();
        for (int i = 0; i < bulletSize; i++) {
            turns.addAll(INITIAL_TURNS);
        }
    }

}
