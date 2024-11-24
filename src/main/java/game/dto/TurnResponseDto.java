package game.dto;

import game.domain.bullet.Bullets;

public record TurnResponseDto(Bullets bullets) {

    public static TurnResponseDto of(Bullets bullets) {
        return new TurnResponseDto(bullets);
    }
}
