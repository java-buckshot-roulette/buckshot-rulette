package game.controller;

import static game.service.Stage.GameResult.GO_NEXT_STAGE;
import static game.service.Stage.GameResult.ONGOING;

import game.config.StageDependency;
import game.domain.LifeAndDeath;
import game.domain.Player;
import game.domain.Role;
import game.domain.bullet.Bullets;
import game.domain.healthpoint.HealthPoint;
import game.domain.item.Item;
import game.domain.item.Items;
import game.dto.GameStateDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;
import game.service.Defibrillator;
import game.service.Stage.DefaultStageReferee;
import game.service.Stage.GameResult;
import game.service.Stage.StageReferee;
import game.service.bullet.BulletGenerator;
import game.service.bullet.DefaultBulletGenerator;
import game.service.item.DefaultItemGenerator;
import game.service.item.ItemGenerator;
import game.service.player.DefaultPlayerService;
import game.service.player.PlayerService;
import game.service.turn.DefaultTurnService;
import game.service.turn.TurnService;
import game.util.Randoms;
import game.view.input.InputView;
import game.view.output.OutputView;
import java.util.ArrayList;
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
        defibrillator = new Defibrillator(true, true);
    }

    public void run() {
        GameResult gameState = ONGOING;
        initializeStage();

        do {
            if (gameState.equals(GO_NEXT_STAGE)) {
                stageDependency = stageDependency.nextStage();
                initializeStage();
            }

            if (bullets.isEmpty()) {
                prepareForRound();
            }

            proceedGameTurn();

            tryUsingDefibrillation();
            tryToBreakDefibrillator();

            gameState = stageReferee.judgeGameResult(challengerService.requestPlayerDataDto(),
                    dealerService.requestPlayerDataDto(), stageDependency);

        } while (gameState.equals(ONGOING) || gameState.equals(GO_NEXT_STAGE));
    }

    private void printStage() {
        outputView.printStage(stageDependency.getStageNumber());
    }

    private void tryToBreakDefibrillator() {
        defibrillator.tryToBreakDefibrillators(challengerService.requestPlayerDataDto(),
                dealerService.requestPlayerDataDto(), stageDependency);
    }

    private void tryUsingDefibrillation() {
        challengerService.applyPlayerDataDto(
                defibrillator.tryUsingChallengerDefibrillator(challengerService.requestPlayerDataDto()));

        dealerService.applyPlayerDataDto(
                defibrillator.tryUsingDealersDefibrillator(dealerService.requestPlayerDataDto()));
    }

    private void initializeStage() {
        printStage();

        defibrillator.initializeDefibrillator();
        challengerService.initializePlayer(stageDependency);
        dealerService.initializePlayer(stageDependency);
        prepareForRound();
    }

    /**
     * 라운드 -> 탄을 재장전하는 단위 1. 탄을 재장전 2. 아이템을 플레이어들에게 나눠줌 3. 게임 턴을 초기화 (라운드 시작 시 항상 도전자가 먼저 아이템을 사용)
     */
    private void prepareForRound() {
        bullets.reload(bulletGenerator.generateBullet(Randoms.pickNumberInRange(3, 8)));
        printBullets();
        turnService.initializeTurn();
        handOutItems();
    }

    /**
     * 게임 턴 진행 턴 -> 한 사람이 아이템을 사용해서부터 총 쏘기 까지
     */
    private void proceedGameTurn() {
        if (turnService.getTurn().equals(Role.CHALLENGER)) {
            proceedPlayerTurn();
            return;
        }
        proceedDealerTurn();
    }

    private void handOutItems() {
        int itemQuantity = stageDependency.getItemGenerationQuantity();

        List<Item> challengerItems = itemGenerator.generateItems(itemQuantity);
        List<Item> dealerItems = itemGenerator.generateItems(itemQuantity);

        challengerService.addItem(challengerItems);
        dealerService.addItem(dealerItems);
    }

    private void proceedPlayerTurn() {
        outputView.println("***플레이어 턴***");
        ItemUsageResponseDto itemUsageResponseDto = challengerService.useItem(dealerService.requestPlayerDataDto(),
                makeGameStateDto());
        applyPlayerDataDto(dealerService, itemUsageResponseDto.target());
        applyGameStateDataDto(itemUsageResponseDto.gameStateDto().passTurn());
    }

    private void proceedDealerTurn() {
        outputView.println("***딜러 턴***");
        ItemUsageResponseDto itemUsageResponseDto = dealerService.useItem(challengerService.requestPlayerDataDto(),
                makeGameStateDto());
        applyPlayerDataDto(challengerService, itemUsageResponseDto.target());
        applyGameStateDataDto(itemUsageResponseDto.gameStateDto().passTurn());

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

    private void printBullets() {
        outputView.printBullet(bullets.toString());
    }
}
