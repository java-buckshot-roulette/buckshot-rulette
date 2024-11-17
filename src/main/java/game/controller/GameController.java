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

            // 탄을 재장전할 때
            // 1. 턴을 초기화
            // 2. 아이템을 플레이어들에게 나눠줌
            if (bullets.isEmpty()) {
                prepareForRound();
            }

            proceedGameTurn();

            tryToBreakDefibrillator();
            tryUsingDefibrillation();

            gameState = stageReferee.judgeGameResult(challengerService.requestPlayerDataDto(),
                    dealerService.requestPlayerDataDto(), stageDependency);

        } while (gameState.equals(ONGOING) || gameState.equals(GO_NEXT_STAGE));

    }

    private void tryToBreakDefibrillator() {
        defibrillator.tryToBreakDefibrillators(challengerService.requestPlayerDataDto(),
                dealerService.requestPlayerDataDto(), stageDependency);
    }

    private void tryUsingDefibrillation() {
        challengerService.applyPlayerDataDto(defibrillator.tryUsingChallengerDefibrillator(challengerService.requestPlayerDataDto()));

        dealerService.applyPlayerDataDto(defibrillator.tryUsingDealersDefibrillator(dealerService.requestPlayerDataDto()));
    }

    private void initializeStage() {
        prepareForRound();
        defibrillator.initializeDefibrillator();
        challengerService.initializePlayer(stageDependency);
        dealerService.initializePlayer(stageDependency);
    }

    private void prepareForRound() {
        bullets.reload(bulletGenerator.generateBullet(Randoms.pickNumberInRange(3, 8)));
        printBullets();
        turnService.initializeTurn();
        handOutItems();
    }

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
        ItemUsageResponseDto itemUsageResponseDto = challengerService.useItem(dealerService.requestPlayerDataDto(),
                makeGameStateDto());
        applyPlayerDataDto(dealerService, itemUsageResponseDto.target());
        applyGameStateDataDto(itemUsageResponseDto.gameStateDto());
    }

    private void proceedDealerTurn() {
        ItemUsageResponseDto itemUsageResponseDto = dealerService.useItem(challengerService.requestPlayerDataDto(),
                makeGameStateDto());
        applyPlayerDataDto(challengerService, itemUsageResponseDto.target());
        applyGameStateDataDto(itemUsageResponseDto.gameStateDto());

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
        outputView.println(bullets.toString());
    }

    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView(outputView);
        PlayerService playerService = new DefaultPlayerService(
                new Player(Role.CHALLENGER, new Items(new ArrayList<>()), new HealthPoint(0),
                        LifeAndDeath.LIFE), inputView);
        PlayerService dealerService = new DefaultPlayerService(
                new Player(Role.DEALER, new Items(new ArrayList<>()), new HealthPoint(0),
                        LifeAndDeath.LIFE), inputView);
        BulletGenerator bulletGenerator = new DefaultBulletGenerator();
        ItemGenerator itemGenerator = new DefaultItemGenerator();
        StageReferee stageReferee = new DefaultStageReferee();
        TurnService turnService = new DefaultTurnService(new ArrayList<>());
        Bullets bullets = new Bullets(new ArrayList<>());
        StageDependency stageDependency = StageDependency.THIRD;
        GameController gameController = new GameController(playerService, dealerService, bulletGenerator, itemGenerator,
                stageReferee, turnService, bullets, stageDependency, inputView, outputView);

        gameController.run();
    }


}
