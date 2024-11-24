package game.dto;

import game.domain.bullet.Bullets;
import game.service.player.PlayerService;
import java.util.List;

public record TurnRequestDto(List<PlayerService> playerServices, Bullets bullets) {

    public static TurnRequestDto of(List<PlayerService> playerServices, Bullets bullets) {
        return new TurnRequestDto(playerServices, bullets);
    }
}
