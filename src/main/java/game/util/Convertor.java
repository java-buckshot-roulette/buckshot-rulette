package game.util;

import game.domain.item.Item;
import game.domain.item.ItemType;
import game.exception.NonExistItemException;

public class Convertor {

    public static Item StringToItem(String itemName) {
        return ItemType
                .getInstanceByName(itemName)
                .orElseThrow(NonExistItemException::new);

    }

    public static Integer parseInt(String number) {
        return Integer.parseInt(number);
    }
}
