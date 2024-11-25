package game.controller;

import static game.service.Stage.GameResult.GO_NEXT_STAGE;
import static game.service.Stage.GameResult.ONGOING;

import game.config.StageDependency;
import game.domain.bullet.Bullets;
import game.dto.TurnProceedRequestDto;
import game.dto.TurnProceedResponseDto;
import game.service.Defibrillator;
import game.service.Stage.GameResult;
import game.service.Stage.StageReferee;
import game.service.bullet.BulletGenerator;
import game.service.item.ItemGenerator;
import game.service.player.PlayerService;
import game.service.turn.TurnService;
import game.util.Randoms;
import game.view.input.InputView;
import game.view.output.OutputView;
import java.util.List;

public class GameController {
    private final PlayerService challengerService;
    private final PlayerService dealerService;
    private final BulletGenerator bulletGenerator;
    private final ItemGenerator itemGenerator;
    private final StageReferee stageReferee;
    private final TurnService turnService;
    private Bullets bullets;
    private StageDependency stageDependency;
    private final InputView inputView;
    private final OutputView outputView;
    private final Defibrillator defibrillator;

    public GameController(PlayerService challengerService, PlayerService dealerService, BulletGenerator bulletGenerator,
                          ItemGenerator itemGenerator, StageReferee stageReferee, TurnService turnService,
                          Bullets bullets, StageDependency stageDependency, InputView inputView,
                          OutputView outputView) {
        this.challengerService = challengerService;
        this.dealerService = dealerService;
        this.bulletGenerator = bulletGenerator;
        this.itemGenerator = itemGenerator;
        this.stageReferee = stageReferee;
        this.turnService = turnService;
        this.bullets = bullets;
        this.stageDependency = stageDependency;
        this.inputView = inputView;
        this.outputView = outputView;
        this.defibrillator = new Defibrillator(true, true);
    }

    public void run() {
        GameResult gameState = ONGOING;

        setupGame();
        while (gameState == ONGOING || gameState == GO_NEXT_STAGE) {
            if (gameState == GO_NEXT_STAGE) {
                advanceToNextStage();
            }

            if (bullets.isEmpty()) {
                prepareForRound();
            }

            proceedTurn();
            handleDefibrillatorActions();

            gameState = evaluateGameResult();
        }
        outputView.println(gameState.toMessage());
    }

    private void proceedTurn() {
        TurnProceedRequestDto turnProceedRequestDto = TurnProceedRequestDto.of(List.of(challengerService, dealerService), bullets);
        TurnProceedResponseDto turnProceedResponseDto = turnService.proceedTurn(turnProceedRequestDto);
        updateGameState(turnProceedResponseDto);
    }

    // ======= Initialization and Setup =======
    private void setupGame() {
        dealerService.setPlayerName("dealer");
        challengerService.setPlayerName(inputView.readName());
        initializeStage();
    }

    private void initializeStage() {
        outputView.printStage(stageDependency.getStageNumber());
        defibrillator.initializeDefibrillator();
        challengerService.initializePlayer(stageDependency);
        dealerService.initializePlayer(stageDependency);
        prepareForRound();
    }

    private void advanceToNextStage() {
        stageDependency = stageDependency.nextStage();
        initializeStage();
    }

    // ======= Game Rounds =======
    private void prepareForRound() {
        bullets = bullets.reload(bulletGenerator.generateBullet(Randoms.pickNumberInRange(3, 8)));
        outputView.printBullet(bullets.toString());
        turnService.initializeTurn();
        distributeItems();
    }

    private void distributeItems() {
        int itemQuantity = stageDependency.getItemGenerationQuantity();
        challengerService.addItem(itemGenerator.generateItems(itemQuantity));
        dealerService.addItem(itemGenerator.generateItems(itemQuantity));
    }

    // ======= Game State Updates =======
    private void handleDefibrillatorActions() {
        challengerService.applyPlayerDataDto(
                defibrillator.tryUsingChallengerDefibrillator(challengerService.requestPlayerDataDto()));
        dealerService.applyPlayerDataDto(
                defibrillator.tryUsingDealersDefibrillator(dealerService.requestPlayerDataDto()));
        defibrillator.tryToBreakDefibrillators(
                challengerService.requestPlayerDataDto(),
                dealerService.requestPlayerDataDto(),
                stageDependency
        );
    }

    private GameResult evaluateGameResult() {
        return stageReferee.judgeGameResult(
                challengerService.requestPlayerDataDto(),
                dealerService.requestPlayerDataDto(),
                stageDependency
        );
    }

    private void updateGameState(TurnProceedResponseDto turnProceedResponseDto) {
        bullets = turnProceedResponseDto.bullets();
    }
}

