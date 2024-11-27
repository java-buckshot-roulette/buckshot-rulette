package game.service.turn;

import game.domain.Role;
import game.dto.TurnProceedRequestDto;
import game.dto.TurnProceedResponseDto;

public interface TurnService {
    Role getTurn();

    void initializeTurn();

    TurnProceedResponseDto proceedTurn(TurnProceedRequestDto turnProceedRequestDto);

}
