package game.service.Stage;

import game.config.StageDependency;
import game.dto.PlayerDataDto;

public interface StageReferee {
    GameState judgeGameResult(PlayerDataDto challenger, PlayerDataDto dealer, StageDependency stageDependency);
}
