package game;

import game.config.StageConfig;
import game.config.StageDependency;
import game.controller.GameController;

public class Application {
    public static void main(String[] args) {
        StageConfig stageConfig = new StageConfig();
        GameController gameController = stageConfig.gameController(StageDependency.FIRST);
        gameController.run();
    }
}