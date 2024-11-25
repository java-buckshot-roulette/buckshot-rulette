package game.service.turn;

import static game.domain.Role.CHALLENGER;
import static game.domain.Role.DEALER;

import game.domain.Role;
import game.domain.bullet.Bullets;
import game.domain.turn.Turns;
import game.dto.GameStateDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;
import game.dto.TurnProceedRequestDto;
import game.dto.TurnProceedResponseDto;
import game.service.player.PlayerService;
import game.view.output.OutputView;
import java.util.List;

public class DefaultTurnService implements TurnService {
    private static final List<Role> INITIAL_TURNS = List.of(CHALLENGER, DEALER);
    private final OutputView outputView;
    private Turns turns;

    public DefaultTurnService(OutputView outputview, Turns turns) {
        this.outputView = outputview;
        this.turns = turns;
    }

    @Override
    public Role getTurn() {
        return turns.getCurrentTurn();
    }

    public void initializeTurn() {
        turns = Turns.initialLialTurns();
    }

    @Override
    public TurnProceedResponseDto proceedTurn(TurnProceedRequestDto turnProceedRequestDto) {
        Role currentTurn = turns.getCurrentTurn(); // 현재 턴 가져오기
        List<PlayerService> playerServices = turnProceedRequestDto.playerServices();

        PlayerService currentPlayer = playerServices.stream()
                .filter(h -> h.isPlayerRole(currentTurn))
                .toList()
                .getFirst();

        PlayerService opponent = playerServices.stream()
                .filter(h -> !h.isPlayerRole(currentTurn))
                .toList()
                .getFirst();

        return executeTurn(currentPlayer, opponent, turnProceedRequestDto.bullets());
    }

    private TurnProceedResponseDto executeTurn(PlayerService currentPlayer, PlayerService opponent, Bullets bullets) {
        outputView.println("*** " + currentPlayer.getName() + " 턴 ***");
        GameStateDto gameStateDto = createGameState(bullets);

        ItemUsageResponseDto response = currentPlayer.useItem(opponent.requestPlayerDataDto(), gameStateDto);
        response = ItemUsageResponseDto.of(response.target(), response.gameStateDto().changeTurns(response.gameStateDto()
                .turns().passTurn()));

        updatePlayerState(opponent, response.target());
        updateGameTurn(response);
        return TurnProceedResponseDto.of(response.gameStateDto().bullets());
    }

    private GameStateDto createGameState(Bullets bullets) {
        return GameStateDto.of(bullets, turns);
    }

    private void updateGameTurn(ItemUsageResponseDto itemUsageResponseDto) {
        this.turns = itemUsageResponseDto.gameStateDto().turns();
    }

    private void updatePlayerState(PlayerService target, PlayerDataDto newData) {
        target.applyPlayerDataDto(newData);
    }
}