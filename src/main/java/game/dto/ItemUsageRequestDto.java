package game.dto;

import game.domain.item.Item;

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

    public ItemUsageRequestDto reduceCasterItem(Item item) {
        PlayerDataDto newCaster = this.caster;
        newCaster = newCaster.reduceItem(item);
        return new ItemUsageRequestDto(newCaster, this.target, this.gameDataDto);
    }
}
