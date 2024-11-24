package game;

import static game.util.Convertor.parseInt;

import game.config.StageConfig;
import game.config.StageDependency;
import game.controller.GameController;
import game.exception.IllegalStartingNumberException;
import game.view.output.OutputView;
import game.view.input.InputView;

/*
 * 수정해야 할 사항
 *
 * 1. 미보유 아이템 사용 예외 처리
 * 2. 잘못된 아이템 입력 예외 처리
 *
 * 3. 수갑을 한번만 사용할 수 있도록 수정
 * 4. 쇠톱을 한번만 사용할 수 있도록 수정
 *
 * 5. 체력 회복 상한선 추가
 */

public class Application {
    private final InputView inputView;
    private final OutputView outputView;

    public Application() {
        outputView = new OutputView();
        inputView = new InputView(outputView);
    }

    public void run() {
        while (true) {
            outputView.printMenu();
            int state = parseInt(pickStartingNumber());

            switch (state) {
                case 1:
                    gameStart();
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    private String pickStartingNumber() {
        try {
            return inputView.askStartingNumber();
        } catch (IllegalStartingNumberException exception) {
            outputView.println(exception.getMessage());
            return pickStartingNumber();
        }
    }

    public void gameStart() {
        StageConfig stageConfig = new StageConfig();
        GameController gameController = stageConfig.gameController(StageDependency.FIRST, outputView, inputView);
        gameController.run();
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}