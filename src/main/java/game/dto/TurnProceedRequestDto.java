package game.dto;

import game.domain.bullet.Bullets;
import game.service.player.PlayerService;
import java.util.List;

public record TurnProceedRequestDto(List<PlayerService> playerServices, Bullets bullets) {

    public static TurnProceedRequestDto of(List<PlayerService> playerServices, Bullets bullets) {
        return new TurnProceedRequestDto(playerServices, bullets);
    }
}
