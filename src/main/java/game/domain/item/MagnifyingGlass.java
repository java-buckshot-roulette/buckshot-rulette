package game.domain.item;

import game.dto.ItemUsageRequestDto;

public class MagnifyingGlass implements Item {
    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {
        return itemUsageRequestDto;
    }
}
