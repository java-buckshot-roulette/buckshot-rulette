package game.service.player;

import game.dto.GameStateDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;

public interface PlayerService {

    ItemUsageResponseDto useItem(PlayerDataDto rival, GameStateDto gameStateDto);
}
