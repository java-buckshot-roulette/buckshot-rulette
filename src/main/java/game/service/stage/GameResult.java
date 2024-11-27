package game.service.stage;

public enum GameResult {
    ONGOING("계속 진행 중"),
    GO_NEXT_STAGE("스테이지 클리어"),
    GAME_OVER("당신은 사망하였습니다.."),
    GAME_CLEAR("딜러가 사망하였습니다.\n\n축하합니다!");

    private final String stageMessage;

    GameResult(String stageMessage) {
        this.stageMessage = stageMessage;
    }

    public String toMessage() {
        return stageMessage;
    }
}

