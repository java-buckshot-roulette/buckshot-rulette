package game.domain.item;

import static game.domain.item.ItemType.EXPIRED_MEDICINE;

import game.domain.healthpoint.HealthPoint;
import game.dto.ItemUsageRequestDto;
import game.util.Randoms;

public class ExpiredMedicine implements Item {
    public static final HealthPoint HEALING_POINT = new HealthPoint(2);
    public static final HealthPoint DISCOUNT_POINT = new HealthPoint(1);

    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {
        HealOrDiscount randomPick = Randoms.pickHealOrDiscount();
        itemUsageRequestDto = healOrDiscount(itemUsageRequestDto, randomPick);

        return itemUsageRequestDto.reduceCasterItem(this);

    }

    private ItemUsageRequestDto healOrDiscount(ItemUsageRequestDto itemUsageRequestDto, HealOrDiscount randomPick) {
        if (randomPick.equals(HealOrDiscount.HEAL)) {
            return itemUsageRequestDto.changeCasterData(itemUsageRequestDto.caster().healPlayer(HEALING_POINT));
        }

        return itemUsageRequestDto.changeCasterData(itemUsageRequestDto
                .caster()
                .discountHealthPoint(DISCOUNT_POINT));

    }

    @Override
    public String toString() {
        return EXPIRED_MEDICINE.getName();
    }
}
