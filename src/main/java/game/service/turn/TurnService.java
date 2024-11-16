package game.service.turn;

import game.domain.Role;
import java.util.List;

public interface TurnService {
    Role getTurn();
    void initializeTurn(int bulletSize);
}
