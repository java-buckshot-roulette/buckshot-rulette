package game.dto;

public record ItemUsageRequestDto(PlayerDataDto caster, PlayerDataDto target, GameStateDto gameDataDto) {
}
