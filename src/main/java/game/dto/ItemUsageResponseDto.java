package game.dto;

public record ItemUsageResponseDto(PlayerDataDto target, GameStateDto gameStateDto) {
    public static ItemUsageResponseDto of(PlayerDataDto target, GameStateDto gameStateDto) {
        return new ItemUsageResponseDto(target, gameStateDto);
    }
}
