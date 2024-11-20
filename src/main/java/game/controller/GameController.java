package game.controller;

import static game.service.Stage.GameResult.GO_NEXT_STAGE;
import static game.service.Stage.GameResult.ONGOING;

import game.config.StageDependency;
import game.domain.Role;
import game.domain.bullet.Bullets;
import game.dto.GameStateDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;
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

    public GameResult run() {
        GameResult gameState = ONGOING;
        dealerService.setPlayerName("dealer");
        inputChallengerName();
        initializeStage();

        while (gameState == ONGOING || gameState == GO_NEXT_STAGE) {
            if (gameState == GO_NEXT_STAGE) {
                stageDependency = stageDependency.nextStage();
                initializeStage();
            }

            if (bullets.isEmpty()) {
                prepareForRound();
            }

            turnService.proceedTurn(this::processTurn);

            handleDefibrillatorActions();

            gameState = stageReferee.judgeGameResult(
                    challengerService.requestPlayerDataDto(),
                    dealerService.requestPlayerDataDto(),
                    stageDependency
            );
        }

        return gameState;
    }

    private void inputChallengerName() {
        challengerService.setPlayerName(inputView.readName());
    }

    private void initializeStage() {
        printStage();
        defibrillator.initializeDefibrillator();
        challengerService.initializePlayer(stageDependency);
        dealerService.initializePlayer(stageDependency);
        prepareForRound();
    }

    private void prepareForRound() {
        bullets.reload(bulletGenerator.generateBullet(Randoms.pickNumberInRange(3, 8)));
        outputView.printBullet(bullets.toString());
        turnService.initializeTurn();
        distributeItems();
    }

    private void distributeItems() {
        int itemQuantity = stageDependency.getItemGenerationQuantity();
        challengerService.addItem(itemGenerator.generateItems(itemQuantity));
        dealerService.addItem(itemGenerator.generateItems(itemQuantity));
    }

    private void processTurn(Role currentTurn) {
        PlayerService currentPlayer = (currentTurn == Role.CHALLENGER) ? challengerService : dealerService;
        PlayerService opponent = (currentTurn == Role.CHALLENGER) ? dealerService : challengerService;

        outputView.println("*** " + currentPlayer.getName() + " 턴 ***");
        ItemUsageResponseDto response = currentPlayer.useItem(opponent.requestPlayerDataDto(), makeGameStateDto());
        applyPlayerDataDto(opponent, response.target());
        applyGameStateDataDto(response.gameStateDto());
    }

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

    private void printStage() {
        outputView.printStage(stageDependency.getStageNumber());
    }

    private void applyPlayerDataDto(PlayerService target, PlayerDataDto targetData) {
        target.applyPlayerDataDto(targetData);
    }

    private void applyGameStateDataDto(GameStateDto gameStateDto) {
        bullets = gameStateDto.bullets();
        turnService.applyTurns(gameStateDto.turns());
    }

    private GameStateDto makeGameStateDto() {
        return new GameStateDto(bullets, turnService.requestTurns());
    }
}
