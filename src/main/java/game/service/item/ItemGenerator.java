package game.service.item;

import game.domain.item.Item;
import game.domain.item.Items;
import java.util.List;

public interface ItemGenerator {
    List<Item> generateItems(int size);
}
