package game.service.turn;


import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;

import game.domain.Role;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DefaultTurnServiceTest {
    @Test
    void 턴_초기화() {
        //given
        List<Role> turns = new ArrayList<>();
        TurnService turnService = new DefaultTurnService(turns);

        //when
        turnService.initializeTurn(1);

        //then
        Assertions.assertThat(turnService.getTurn()).isEqualTo(CHALLENGER);
    }

    @Test
    void 다음_턴() {
        //given
        List<Role> turns = new ArrayList<>(List.of(CHALLENGER, DEALER));

        //when
        turns.removeFirst();
        DefaultTurnService turnService = new DefaultTurnService(turns);

        Assertions.assertThat(turnService.getTurn()).isEqualTo(DEALER);
    }

    @Test
    void 맨앞에_턴_추가() {
        //given
        List<Role> turns = new ArrayList<>(List.of(CHALLENGER, DEALER));

        //when
        turns.addFirst(DEALER);
        DefaultTurnService turnService = new DefaultTurnService(turns);

        Assertions.assertThat(turnService.getTurn()).isEqualTo(DEALER);
    }


}