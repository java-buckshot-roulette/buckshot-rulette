package game.domain.item;

import game.domain.Role;
import game.dto.ItemUsageRequestDto;
import java.util.ArrayList;
import java.util.List;

public class Handcuffs implements Item {
    @Override
    public ItemUsageRequestDto useItem(ItemUsageRequestDto itemUsageRequestDto) {
        List<Role> newTurns = new ArrayList<>(itemUsageRequestDto.gameDataDto().turns());

        Role role = newTurns.getFirst();
        newTurns.addFirst(role);

        return itemUsageRequestDto.changeGameData(itemUsageRequestDto
                        .gameDataDto()
                        .changeTurns(newTurns))
                .reduceCasterItem(this);
    }
}
