package game;

import java.io.IOException;

import game.config.StageConfig;
import game.config.StageDependency;
import game.controller.GameController;
import game.service.Stage.GameResult;
import game.view.output.OutputView;
import game.view.input.InputView;

/*
 * 수정해야 할 사항
 * 1. 미보유 아이템 사용 예외 처리
 * 2. 잘못된 아이템 입력 예외 처리
 * 3. "쇠톱" 아이템 사용 구현
 */

public class Application {
    private InputView inputView;
    private OutputView outputView;

    public Application() {
        outputView = new OutputView();
        inputView = new InputView(outputView);
    }

    public void run() throws IOException {
        outputView.printMenu();
        
        GameResult result = null;

        while(true) {
            int state = Integer.parseInt(inputView.askPersonToSelect());

            switch (state) {
                case 1:
                    result = gameStart();
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    outputView.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                    break;
            }

            if(result != null) {
                outputView.printResult(result);
                outputView.printMenu();
            }
        }
    }

    public GameResult gameStart() {
        StageConfig stageConfig = new StageConfig();
        GameController gameController = stageConfig.gameController(StageDependency.FIRST);
        return gameController.run();
    }

    public static void main(String[] args) throws IOException {
        Application app = new Application();
        app.run();
    }
}