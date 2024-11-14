package game.service.Stage;

import game.domain.GameResult;
import game.domain.LifeAndDeath;

public interface StageReferee {
    GameResult judgeGameResult(LifeAndDeath myStatus, LifeAndDeath revelStatus);
}
