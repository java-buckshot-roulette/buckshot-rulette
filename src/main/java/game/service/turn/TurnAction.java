package game.service.turn;

import game.domain.Role;

public interface TurnAction {
    void execute(Role currentTurn);
}
