package game.config;

import game.domain.healthpoint.HealthPoint;

public enum StageDependency {
    FIRST(0, 1, false, 2, true),
    SECOND(2, 2, false, 4, false),
    THIRD(4, 2, true, 6, false);

    private final int itemGenerationQuantity;
    private final boolean isFinalStage;
    private final int nextStageIndex;
    private final HealthPoint playerInitialHealthPoint;
    private final boolean isFirstStage;

    StageDependency(int itemGenerationQuantity, int nextStageIndex, boolean isFinalStage, int playerInitialHealthPoint,
                    boolean isFirstStage) {
        this.itemGenerationQuantity = itemGenerationQuantity;
        this.isFinalStage = isFinalStage;
        this.nextStageIndex = nextStageIndex;
        this.playerInitialHealthPoint = new HealthPoint(playerInitialHealthPoint);
        this.isFirstStage = isFirstStage;
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
}


