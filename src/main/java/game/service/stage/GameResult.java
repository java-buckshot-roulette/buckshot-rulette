package game.service.stage;

public enum GameResult {
    ONGOING("계속 진행 중"),
    GO_NEXT_STAGE("스테이지 클리어"),
    GAME_OVER("당신은 죽었습니다.."),
    GAME_CLEAR("축하합니다!");

    private final String stageMessage;

    GameResult(String stageMessage) {
        this.stageMessage = stageMessage;
    }

    public String toMessage() {
        return stageMessage;
    }
}

