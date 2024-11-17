package game.util;

import game.domain.item.Item;
import game.domain.item.ItemType;

public class Convertor {

    public static Item StringToItem(String itemName) {
        return ItemType
                .getInstanceByName(itemName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item name: " + itemName));
        //todo: 예외 처리 클래스 따로 빼기
    }

}
