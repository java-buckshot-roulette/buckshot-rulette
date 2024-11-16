package game.domain.item;

import game.domain.healthpoint.HealthPoint;
import game.dto.ItemUsageRequestDto;
import game.dto.PlayerDataDto;

public class CigarettePack implements Item {
    private static final HealthPoint HEALING_POINT = new HealthPoint(1);
    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {
        PlayerDataDto newCaster = itemUsageRequestDto.caster();
        newCaster = newCaster.healPlayer(HEALING_POINT);
        return new ItemUsageRequestDto(newCaster, itemUsageRequestDto.target(), itemUsageRequestDto.gameDataDto())
                .reduceCasterItem(this);
    }
}
