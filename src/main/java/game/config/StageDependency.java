package game.config;

import game.domain.healthpoint.HealthPoint;
import game.dto.PlayerDataDto;

public enum StageDependency {
    FIRST(0, 1, false, 2, true, 0),
    SECOND(2, 2, false, 4, false, 0),
    THIRD(4, 2, true, 6, false, 2);

    private final int itemGenerationQuantity;
    private final boolean isFinalStage;
    private final int nextStageIndex;
    private final HealthPoint playerInitialHealthPoint;
    private final boolean isFirstStage;
    private final HealthPoint breakingDefibrillator;

    StageDependency(int itemGenerationQuantity, int nextStageIndex, boolean isFinalStage, int playerInitialHealthPoint,
                    boolean isFirstStage, int breakingDefibrillator) {
        this.itemGenerationQuantity = itemGenerationQuantity;
        this.isFinalStage = isFinalStage;
        this.nextStageIndex = nextStageIndex;
        this.playerInitialHealthPoint = new HealthPoint(playerInitialHealthPoint);
        this.isFirstStage = isFirstStage;
        this.breakingDefibrillator = new HealthPoint(breakingDefibrillator);
    }

    public int getItemGenerationQuantity() {
        return itemGenerationQuantity;
    }

    public boolean isFinalStage() {
        return isFinalStage;
    }

    public StageDependency nextStage() {
        if (isFinalStage) {
            throw new IllegalStateException("다음라운드가 없습니다.");    //Todo: 예외 처리
        }

        return values()[nextStageIndex];
    }

    public HealthPoint getPlayerInitialHealthPoint() {
        return playerInitialHealthPoint;
    }

    public boolean isFirstStage() {
        return isFirstStage;
    }

    public boolean isBreakingDefibrillatorsCondition(PlayerDataDto playerDataDto) {
        return playerDataDto.healthPoint()
                .equals(breakingDefibrillator);
    }

    public int getStageNumber() {
        return ordinal() + 1;
    }
}


