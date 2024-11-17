package game.service.Stage;

import static game.domain.LifeAndDeath.DEATH;
import static game.service.Stage.GameResult.*;
import static game.service.Stage.GameResult.GAME_OVER;

import game.config.StageDependency;
import game.dto.PlayerDataDto;

public class DefaultStageReferee implements StageReferee {

    @Override
    public GameResult judgeGameResult(PlayerDataDto challenger, PlayerDataDto dealer, StageDependency stageDependency) {
        if (challenger.lifeAndDeath().equals(DEATH)) {
            return GAME_OVER;
        }
        if (dealer.lifeAndDeath().equals(DEATH) && stageDependency.isFinalStage()) {
            return GAME_CLEAR;
        }
        if (dealer.lifeAndDeath().equals(DEATH)) {
            return GO_NEXT_STAGE;
        }
        return ONGOING;
    }
}
