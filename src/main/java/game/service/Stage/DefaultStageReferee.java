package game.service.Stage;

import static game.domain.LifeAndDeath.DEATH;
import static game.service.Stage.GameResult.*;
import static game.service.Stage.GameResult.GAME_OVER;

import game.config.StageDependency;
import game.domain.healthpoint.HealthPoint;
import game.dto.PlayerDataDto;

public class DefaultStageReferee implements StageReferee {
    private static final HealthPoint ZERO_HEALTH = new HealthPoint(0);

    @Override
    public GameResult judgeGameResult(PlayerDataDto challenger, PlayerDataDto dealer, StageDependency stageDependency) {
        if (challenger.lifeAndDeath().equals(DEATH) || challenger.healthPoint().equals(ZERO_HEALTH)) {
            return GAME_OVER;
        }
        if ((dealer.healthPoint().equals(ZERO_HEALTH) || dealer.lifeAndDeath().equals(DEATH))
                && stageDependency.isFinalStage()) {
            return GAME_CLEAR;
        }
        if (dealer.lifeAndDeath().equals(DEATH) || dealer.healthPoint().equals(ZERO_HEALTH)) {
            return GO_NEXT_STAGE;
        }
        return ONGOING;
    }
}
