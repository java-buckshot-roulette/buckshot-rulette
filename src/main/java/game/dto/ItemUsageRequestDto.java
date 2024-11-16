package game.dto;

public record ItemUsageRequestDto(PlayerDataDto caster, PlayerDataDto target, GameStateDto gameDataDto) {
    public ItemUsageRequestDto changeCasterData(PlayerDataDto newCaster) {
        return new ItemUsageRequestDto(newCaster, this.target, this.gameDataDto);
    }

    public ItemUsageRequestDto changeTargetData(PlayerDataDto newTarget) {
        return new ItemUsageRequestDto(this.caster, newTarget, this.gameDataDto);
    }

    public ItemUsageRequestDto changeGameData(GameStateDto newGameData) {
        return new ItemUsageRequestDto(this.caster, this.target, newGameData);
    }
}
