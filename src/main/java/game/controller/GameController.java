package game.controller;

import game.config.StageDependency;
import game.domain.LifeAndDeath;
import game.domain.Player;
import game.domain.Role;
import game.domain.bullet.BulletConfig;
import game.domain.bullet.Bullets;
import game.domain.healthpoint.HealthPoint;
import game.domain.item.Items;
import game.dto.GameStateDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;
import game.service.Stage.DefaultStageReferee;
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
    private final StageDependency stageDependency;
    private final InputView inputView;
    private final OutputView outputView;

    public GameController(PlayerService challengerService, PlayerService dealerService, BulletGenerator bulletGenerator,
                          ItemGenerator itemGenerator, StageReferee stageReferee, TurnService turnService,
                          Bullets bullets, StageDependency stageDependency, InputView inputView, OutputView outputView) {
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
    }

    public void run() {
        if(bullets.isEmpty())
            bullets.reload(bulletGenerator.generateBullet(Randoms.pickNumberInRange(3, 8)));    //Todo: dependency 로 관리할지 고민하기
        if(turnService.getTurn().equals(Role.CHALLENGER)) {
            proceedPlayerTurn();
        }
        proceedDealerTurn();

    }

    private void proceedPlayerTurn() {
        ItemUsageResponseDto itemUsageResponseDto = challengerService.useItem(dealerService.requestPlayerDataDto(),
                makeGameStateDto());
        applyPlayerDataDto(dealerService, itemUsageResponseDto.target());
    }

    private void proceedDealerTurn() {
        ItemUsageResponseDto itemUsageResponseDto = dealerService.useItem(challengerService.requestPlayerDataDto(),
                makeGameStateDto());
        applyPlayerDataDto(challengerService, itemUsageResponseDto.target());
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

    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView(outputView);
        PlayerService playerService = new DefaultPlayerService(new Player(Role.CHALLENGER, new Items(new ArrayList<>()), new HealthPoint(3),
                LifeAndDeath.LIFE),inputView);
        PlayerService dealerService = new DefaultPlayerService(new Player(Role.DEALER, new Items(new ArrayList<>()), new HealthPoint(3),
                LifeAndDeath.LIFE),inputView);
        BulletGenerator bulletGenerator = new DefaultBulletGenerator();
        ItemGenerator itemGenerator = new DefaultItemGenerator();
        StageReferee stageReferee = new DefaultStageReferee();
        TurnService turnService = new DefaultTurnService(new ArrayList<>());
        Bullets bullets = new Bullets(List.of(BulletConfig.RED));
        StageDependency stageDependency = StageDependency.FIRST;
        GameController gameController = new GameController(playerService, dealerService, bulletGenerator, itemGenerator,
                stageReferee, turnService, bullets, stageDependency, inputView, outputView);

        gameController.run();
    }


}
