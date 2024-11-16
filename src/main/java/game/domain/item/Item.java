package game.domain.item;

import game.dto.ItemUsageRequestDto;

public interface Item {
    ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto);
}
