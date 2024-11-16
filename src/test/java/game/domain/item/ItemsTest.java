package game.domain.item;

import static game.domain.item.ItemConfig.BEAR;
import static game.domain.item.ItemConfig.EXPIRED_MEDICINE;
import static game.domain.item.ItemConfig.HAND_CUFFS;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class ItemsTest {
    @Test
    void 아이템_추가() {
        //given
        List<Item> oldItem = List.of(BEAR);
        List<Item> additionalItems = List.of(BEAR, BEAR, BEAR, BEAR, BEAR, BEAR, HAND_CUFFS, EXPIRED_MEDICINE);
        Items items = new Items(oldItem);

        //when
        Items newItems = items.addItems(additionalItems);

        //then
        assertThat(newItems.contains(HAND_CUFFS)).isTrue();
        assertThat(newItems.contains(EXPIRED_MEDICINE)).isFalse();
    }

}