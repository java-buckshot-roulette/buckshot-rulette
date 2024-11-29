package game.service.item;

import static game.domain.item.ItemType.BEAR;
import static game.domain.item.ItemType.CIGARETTE_PACK;
import static game.domain.item.ItemType.EXPIRED_MEDICINE;
import static game.domain.item.ItemType.HAND_CUFFS;
import static game.domain.item.ItemType.HAND_SAW;
import static game.domain.item.ItemType.INVERTER;
import static game.domain.item.ItemType.MAGNIFYING_GLASS;
import static game.util.Randoms.pickItemInList;

import game.domain.item.Item;
import game.domain.item.ItemType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DefaultItemGenerator implements ItemGenerator {
    private final static List<Item> ITEMS = Arrays.asList(BEAR.getInstance(), CIGARETTE_PACK.getInstance(),
            EXPIRED_MEDICINE.getInstance(), HAND_CUFFS.getInstance(), HAND_SAW.getInstance(),
            MAGNIFYING_GLASS.getInstance(),
            INVERTER.getInstance());

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
