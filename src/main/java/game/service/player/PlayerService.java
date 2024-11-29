package game.service.player;

import game.config.StageDependency;
import game.domain.Role;
import game.domain.item.Item;
import game.dto.GameStateDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;
import java.util.List;

public interface PlayerService {

    ItemUsageResponseDto useItem(PlayerDataDto rival, GameStateDto gameStateDto);

    PlayerDataDto requestPlayerDataDto();

    void applyPlayerDataDto(PlayerDataDto playerDataDto);

    void addItem(List<Item> items);

    void initializePlayer(StageDependency stageDependency);

    String getName();

    void setPlayerName(String s);

    boolean isPlayerRole(Role role);
}
