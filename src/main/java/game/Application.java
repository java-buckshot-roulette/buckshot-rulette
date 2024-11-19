package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import game.config.StageConfig;
import game.config.StageDependency;
import game.controller.GameController;
import game.service.Stage.GameResult;
import game.view.input.InputView;
import game.view.output.OutputView;

public class Application {

    private OutputView outputView = new OutputView();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void run() throws IOException {
        outputView.printMenu();
        while(true) {
            outputView.print("선택 : ");
            int state = Integer.parseInt(this.reader.readLine());

            GameResult result = null;
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
                if (result.equals(GameResult.GAME_CLEAR)) {
                    outputView.println("축하합니다!");
                } else {
                    outputView.println("당신은 죽었습니다..");
                }
                break;
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