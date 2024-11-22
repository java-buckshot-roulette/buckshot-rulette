package game.service.turn;

import game.domain.Role;
import game.domain.turn.Turns;
import game.dto.TurnRequestDto;
import game.dto.TurnResponseDto;

public interface TurnService {
    Role getTurn();

    void initializeTurn();

    TurnResponseDto proceedTurn(TurnRequestDto turnRequestDto);

}
