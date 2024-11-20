package game.service.turn;


import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;

import game.domain.Role;
import game.domain.turn.Turns;
import java.util.ArrayList;
import java.util.List;
import javax.print.attribute.standard.Finishings;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DefaultTurnServiceTest {
    @Test
    void 턴_초기화() {
        //given
        Turns turns = Turns.initialLialTurns();
        TurnService turnService = new DefaultTurnService(Turns.initialLialTurns());

        //when
        turnService.initializeTurn();

        //then
        Assertions.assertThat(turnService.getTurn()).isEqualTo(CHALLENGER);
    }

    @Test
    void 다음_턴() {
        //given
        Turns turns = Turns.initialLialTurns();

        //when
        turns = turns.passTurn();
        DefaultTurnService turnService = new DefaultTurnService(turns);

        Assertions.assertThat(turnService.getTurn()).isEqualTo(DEALER);
    }

    @Test
    void 맨앞에_턴_추가() {
        //given
        Turns turns = Turns.initialLialTurns();

        //when
        turns = turns.passTurn();
        DefaultTurnService turnService = new DefaultTurnService(turns);

        Assertions.assertThat(turnService.getTurn()).isEqualTo(DEALER);
    }


}