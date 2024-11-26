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
 * 1. 수갑을 한번만 사용할 수 있도록 수정 (플레이어)
 * 2. 쇠톱을 한번만 사용할 수 있도록 수정 (플레이어)
 * 
 * 3. 상한 약 사용 후 체력이 0 이하로 떨어졌을 때, 턴이 종료되기 전까지 사망하지 않는 현상
 *    --> 턴이 종료된 후 사망을 체크하기 때문에?
 * 
 * 4. 3스테이지에서 체력이 2 이하로 떨어진 후, 체력을 다시 회복하더라도 한 번의 공격으로 죽는 현상
 *    --> 제세동기 작동 때문인거 같은데, 시각적으로 보이는 요소가 없다보니 버그처럼 느껴짐
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