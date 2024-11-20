package game.domain.turn;

import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;

import game.domain.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Turns {
    private static final List<Role> INITIAL_TURNS =
            //Todo: 리펙토링 필요할수도
            Stream.generate(() -> List.of(Role.CHALLENGER, Role.DEALER))
                    .limit(10)
                    .flatMap(List::stream)
                    .toList();

    private final List<Role> turns;


    public Turns(List<Role> turns) {
        this.turns = turns;
    }

    public Role getCurrentTurn() {
        return turns.getFirst();
    }

    public Turns KeepTurn() {
        ArrayList<Role> newTurns = new ArrayList<>(turns);
        Role first = newTurns.getFirst();
        newTurns.addFirst(first);
        return new Turns(turns);
    }


    public Turns passTurn() {
        ArrayList<Role> newTurns = new ArrayList<>(turns);
        newTurns.removeFirst();
        return new Turns(newTurns);
    }

    public static Turns initialLialTurns() {
        return new Turns(INITIAL_TURNS);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Turns turns1 = (Turns) o;
        return Objects.equals(turns, turns1.turns);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(turns);
    }
}
