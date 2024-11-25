package game.dto;

import game.domain.bullet.Bullets;

public record TurnProceedResponseDto(Bullets bullets) {

    public static TurnProceedResponseDto of(Bullets bullets) {
        return new TurnProceedResponseDto(bullets);
    }
}
