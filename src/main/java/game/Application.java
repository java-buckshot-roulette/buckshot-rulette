package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import game.config.StageConfig;
import game.config.StageDependency;
import game.controller.GameController;
import game.service.Stage.GameResult;
import game.view.output.OutputView;
import game.view.input.InputView;

/*
 * [발견된 버그] 
 * 1. 아이템 사용 시, 자신의 턴이 유지되지 않고 상대방의 턴으로 넘어가는 버그
 * 2. 잘못된 아이템 입력에 대한 예외 처리를 구현하지 않음
 * 
 * [기능 추가]
 * 1. 딜러 용 AI 추가
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