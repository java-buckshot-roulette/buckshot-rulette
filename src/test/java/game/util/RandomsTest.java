package game.util;

import static game.domain.item.ItemConfig.BEAR;
import static org.junit.jupiter.api.Assertions.*;

import game.domain.item.Item;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomsTest {
    @Test
    void 아이템_생성() {
        //given
        List<Item> item = List.of(BEAR);

        //when
        Optional<Item> generatedItem = Randoms.pickItemInList(item);

        //then
        Assertions.assertThat(generatedItem)
                .isPresent()
                .contains(BEAR);
    }

}