package game.service.item;

import static game.domain.item.ItemConfig.BEAR;
import static game.domain.item.ItemConfig.CIGARETTE_PACK;
import static game.domain.item.ItemConfig.EXPIRED_MEDICINE;
import static game.domain.item.ItemConfig.HAND_CUFFS;
import static game.domain.item.ItemConfig.HAND_SAW;
import static game.domain.item.ItemConfig.INVERTER;
import static game.domain.item.ItemConfig.MAGNIFYING_GLASS;
import static game.util.Randoms.pickItemInList;

import game.domain.item.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DefaultItemGenerator implements ItemGenerator {
    private final static List<Item> ITEMS = Arrays.asList(BEAR, CIGARETTE_PACK,
            EXPIRED_MEDICINE, HAND_CUFFS, HAND_SAW, MAGNIFYING_GLASS, INVERTER);

    @Override
    public List<Item> generateItems(int size) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Optional<Item> item = pickItemInList(ITEMS);
            item.ifPresent(items::add);
        }
        return items;
    }
}
