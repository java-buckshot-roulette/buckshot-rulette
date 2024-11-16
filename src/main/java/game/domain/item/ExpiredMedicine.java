package game.domain.item;

import game.dto.ItemUsageRequestDto;

public class ExpiredMedicine implements Item {
    @Override
    public ItemUsageRequestDto useItem() {
        return null;
    }
}
