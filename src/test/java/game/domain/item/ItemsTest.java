package game.domain.item;

import static game.domain.item.ItemType.BEAR;
import static game.domain.item.ItemType.EXPIRED_MEDICINE;
import static game.domain.item.ItemType.HAND_CUFFS;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class ItemsTest {
    @Test
    void 아이템_추가() {
        //given
        Item bear = BEAR.getInstance();
        Item handCuffs = HAND_CUFFS.getInstance();
        Item expiredMedicine = EXPIRED_MEDICINE.getInstance();

        List<Item> oldItem = List.of(bear);
        List<Item> additionalItemList = List.of(bear, bear, bear, bear, bear, bear, handCuffs, expiredMedicine);
        Items items = new Items(oldItem);
        Items additionalItems = new Items(additionalItemList);

        //when
        Items newItems = items.add(additionalItems);

        //then
        assertThat(newItems.contains(handCuffs)).isTrue();
        assertThat(newItems.contains(expiredMedicine)).isFalse();
    }

}