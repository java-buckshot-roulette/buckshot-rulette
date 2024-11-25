package game;

import static game.util.Convertor.parseInt;

import game.config.StageConfig;
import game.config.StageDependency;
import game.controller.GameController;
import game.exception.IllegalStartingNumberException;
import game.service.Stage.GameResult;
import game.view.output.OutputView;
import game.view.input.InputView;

/*
 * 수정해야 할 사항
 *
 * 1. 수갑을 한번만 사용할 수 있도록 수정
 * 2. 쇠톱을 한번만 사용할 수 있도록 수정
 */

public class Application {
    private InputView inputView;
    private OutputView outputView;

    public Application() {
        outputView = new OutputView();
        inputView = new InputView(outputView);
    }

    public void run() {
        outputView.printMenu();

        GameResult result = null;

        while (true) {
            int state = parseInt(pickStartingNumber());

            switch (state) {
                case 1:
                    result = gameStart();
                    break;
                case 2:
                    System.exit(0);
                    break;
            }

            if (result != null) {
                outputView.printResult(result);
                outputView.printMenu();
                result = null;
            }
        }
    }

    private String pickStartingNumber() {
        try {
            return inputView.askPersonToSelectStartingNumber();
        } catch (IllegalStartingNumberException exception) {
            outputView.println(exception.getMessage());
            return pickStartingNumber();
        }
    }

    public GameResult gameStart() {
        StageConfig stageConfig = new StageConfig();
        GameController gameController = stageConfig.gameController(StageDependency.FIRST);
        return gameController.run();
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}