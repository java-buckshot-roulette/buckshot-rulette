package game.service.turn;

import game.domain.Role;
import game.domain.turn.Turns;
import java.util.List;

public interface TurnService {
    Role getTurn();

    void initializeTurn();

    Turns requestTurns();

    void applyTurns(Turns turns);

    void proceedTurn(TurnAction turnAction);

}
