package game.domain.item;

import static game.domain.item.ItemType.MAGNIFYING_GLASS;

import game.dto.ItemUsageRequestDto;

public class MagnifyingGlass implements Item {
    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {
        return itemUsageRequestDto;
    }

    @Override
    public String toString() {
        return MAGNIFYING_GLASS.getName();
    }
}
