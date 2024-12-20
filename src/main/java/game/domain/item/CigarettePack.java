package game.domain.item;

import static game.domain.item.ItemType.CIGARETTE_PACK;

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
                .changeCasterItems(itemUsageRequestDto.caster().items().reduceItem(this));
    }

    public HealthPoint getHealingPoint() {
        return HEALING_POINT;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }

    @Override
    public String toString() {
        return CIGARETTE_PACK.getName();
    }
}
