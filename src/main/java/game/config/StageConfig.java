package game.config;

import game.controller.GameController;
import game.domain.LifeAndDeath;
import game.domain.Player;
import game.domain.Role;
import game.domain.bullet.Bullets;
import game.domain.healthpoint.HealthPoint;
import game.domain.item.Items;
import game.domain.turn.Turns;
import game.service.Stage.DefaultStageReferee;
import game.service.Stage.StageReferee;
import game.service.bullet.BulletGenerator;
import game.service.bullet.DefaultBulletGenerator;
import game.service.item.DefaultItemGenerator;
import game.service.item.ItemGenerator;
import game.service.player.AIPlayerService;
import game.service.player.DefaultPlayerService;
import game.service.player.PlayerService;
import game.service.turn.DefaultTurnService;
import game.service.turn.TurnService;
import game.view.input.InputView;
import game.view.output.OutputView;
import java.util.ArrayList;

public class StageConfig {
    public GameController gameController(StageDependency startStage, OutputView outputView, InputView inputView) {

        PlayerService playerService = new DefaultPlayerService(
                new Player("player", Role.CHALLENGER, new Items(new ArrayList<>()), new HealthPoint(0),
                        LifeAndDeath.LIFE), inputView, outputView);

        PlayerService dealerService = new AIPlayerService(
                new Player("dealer", Role.DEALER, new Items(new ArrayList<>()), new HealthPoint(0),
                        LifeAndDeath.LIFE), outputView);

        BulletGenerator bulletGenerator = new DefaultBulletGenerator();

        ItemGenerator itemGenerator = new DefaultItemGenerator();

        StageReferee stageReferee = new DefaultStageReferee();

        TurnService turnService = new DefaultTurnService(outputView, Turns.initialLialTurns());

        Bullets bullets = new Bullets(new ArrayList<>());

        return new GameController(playerService, dealerService, bulletGenerator, itemGenerator,
                stageReferee, turnService, bullets, startStage, inputView, outputView);
    }
}
