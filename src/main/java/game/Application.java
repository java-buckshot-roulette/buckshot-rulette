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
 * 1. 제세동기 사용 불가능에 대한 연출을 추가할 필요가 있음.
 *    - 더이상 소생할 수 없다는 메세지 출력
 *    - 체력바를 0으로 표시
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