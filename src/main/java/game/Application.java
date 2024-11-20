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
    private InputView inputView;
    private OutputView outputView;

    public Application() {
        outputView = new OutputView();
        inputView = new InputView(outputView);
    }

    public void run() {
        outputView.printMenu();
        
        GameResult result = null;

        while(true) {
            String state = inputView.askPersonToSelect();

            switch (state.trim()) {
                case "1":
                    result = gameStart();
                    break;
                case "2":
                    System.exit(0);
                    break;
                default:
                    outputView.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                    break;
            }

            if(result != null) {
                outputView.printResult(result);
                outputView.printMenu();
                result = null;
            }
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