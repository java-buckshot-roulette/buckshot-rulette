package game;

import java.io.IOException;

import game.config.StageConfig;
import game.config.StageDependency;
import game.controller.GameController;
import game.service.Stage.GameState;
import game.view.output.OutputView;
import game.view.input.InputView;
import java.time.LocalDateTime;

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
    public InputView inputView;
    public OutputView outputView;

    public Application() {
        outputView = new OutputView();
        inputView = new InputView(outputView);
    }

    public GameState selectMenu() {
        int state = 0;
        try {
            state = Integer.parseInt(inputView.askPersonToSelect());
        }catch(Exception e) {
            outputView.println("숫자를 입력해주세요");
            return null;
        }
        switch (state) {
            case 1:
                outputView.println("게임을 시작합니다.\n");
                return gameStart();
            case 2:
                System.exit(0);
                break;
            default:
                outputView.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                break;
        }

        return null;
    }

    public void run() throws IOException {

        outputView.printMenu();

        while(true) {
            GameState result = selectMenu();

            if(result != null) {
                outputView.printResult(result);
                outputView.printMenu();
            }
        }
    }

    public GameState gameStart() {

        StageConfig stageConfig = new StageConfig();
        GameController gameController = stageConfig.gameController(StageDependency.FIRST);
        return gameController.run();
    }

    public static void main(String[] args) throws IOException {
        Application app = new Application();
        app.run();
    }
}