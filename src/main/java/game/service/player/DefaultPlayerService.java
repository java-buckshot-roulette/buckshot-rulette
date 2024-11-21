package game.service.player;

import static game.util.Convertor.StringToItem;

import game.config.StageDependency;
import game.domain.Player;
import game.domain.bullet.Bullet;
import game.domain.item.Item;
import game.domain.item.ItemType;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;
import game.exception.OutOfPossessionItemException;
import game.util.Convertor;
import game.util.Timer;
import game.view.input.InputView;
import game.view.output.OutputView;
import java.util.List;

public class DefaultPlayerService implements PlayerService {
    private Player player;
    private final InputView inputView;
    private final OutputView outputView;

    public DefaultPlayerService(Player player, InputView inputView, OutputView outputView) {
        this.player = player;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    @Override
    public ItemUsageResponseDto useItem(PlayerDataDto rival, GameStateDto gameStateDto) {
        return processItemUsage(makeRequestDto(rival, gameStateDto));
    }

    @Override
    public PlayerDataDto requestPlayerDataDto() {
        return player.makePlayerDataDto();
    }

    @Override
    public void applyPlayerDataDto(PlayerDataDto playerDataDto) {
        player = player.applyEffect(playerDataDto);
    }

    @Override
    public void addItem(List<Item> items) {
        player = player.addItem(items);
    }

    @Override
    public void initializePlayer(StageDependency stageDependency) {
        player = player.initializeHealthPoint(stageDependency.getPlayerInitialHealthPoint())
                .initializeItems();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void setPlayerName(String name) {
        player = player.name(name);
    }

    // ======= 아이템 사용 처리 =======
    private ItemUsageResponseDto processItemUsage(ItemUsageRequestDto request) {
        while (true) {
            if (request.gameDataDto().bullets().isEmpty()) {
                outputView.println("탄환이 모두 소진되었습니다."); // 탄환 부족 메시지 출력
                return handleEmptyBullets(request);
            }

            Item item = getItemInput(request);
            if (item.equals(ItemType.SHOT_GUN.getInstance())) {
                outputView.println("\n샷건을 사용합니다."); // 샷건 사용 메시지 출력
                return handleShotgunUsage(request, item);
            }

            request = useItemAndUpdateState(item, request);
        }
    }

    private ItemUsageRequestDto useItemAndUpdateState(Item item, ItemUsageRequestDto request) {
        ItemUsageRequestDto updatedRequest = item.useItem(request);
        printItemUsage(item, updatedRequest);
        return updatedRequest;
    }

    private ItemUsageResponseDto handleShotgunUsage(ItemUsageRequestDto request, Item shotgun) {
        String shotTarget = inputView.askPersonToBeShot();
        int damage = request.gameDataDto().bullets().getFirstBulletDamage();
        outputView.printResultOfShot(damage); // 샷건 피해 결과 출력

        boolean selfShot = "나".equals(shotTarget);
        if(selfShot) {
            ItemUsageRequestDto shotResult = shotgun.useItem(new ItemUsageRequestDto(request.caster(), request.caster(), request.gameDataDto()));
            return createResponse(new ItemUsageRequestDto(shotResult.target(), request.target(), shotResult.gameDataDto()));
        }

        return createResponse(shotgun.useItem(request));
    }

    private ItemUsageResponseDto handleEmptyBullets(ItemUsageRequestDto request) {
        outputView.println("탄환이 모두 소진되었습니다. 아이템 사용 종료."); // 탄환 부족 출력
        return createResponse(request);
    }

    // ======= DTO 생성 및 상태 갱신 =======
    private ItemUsageRequestDto makeRequestDto(PlayerDataDto rival, GameStateDto gameStateDto) {
        return new ItemUsageRequestDto(player.makePlayerDataDto(), rival, gameStateDto);
    }

    private ItemUsageResponseDto createResponse(ItemUsageRequestDto request) {
        applyPlayerDataDto(request.caster());
        return new ItemUsageResponseDto(request.target(), request.gameDataDto());
    }

    // ======= 사용자 입력 및 출력 =======
    private Item getItemInput(ItemUsageRequestDto request) {
        outputView.printPlayerState(request.target(), request.caster()); // 현재 상태 출력
        return readITem();
    }

    private Item readITem() {
        try {
        String item = inputView.readItem();
        validatePossessionItem(item, player);
        return Convertor.StringToItem(item);
        } catch (Exception exception) {
            outputView.println(exception.getMessage());
            return readITem();

        }
    }

    private void validatePossessionItem(String item, Player player) {
        if(!player.hasItem(StringToItem(item))){
            throw new OutOfPossessionItemException();
        }
    }

    private void printItemUsage(Item item, ItemUsageRequestDto request) {
        outputView.println("\n" + item + "을(를) 사용합니다.\n"); // 아이템 사용 메시지 출력
        Timer.delay(1000);

        if (item.equals(ItemType.MAGNIFYING_GLASS.getInstance())) {
            Bullet firstBullet = request.gameDataDto().bullets().CheckFirstBullet();
            outputView.println("첫 번째 탄환은... " + firstBullet + "\n"); // 돋보기 사용 시 출력
            Timer.delay(1000);
        }
    }
}
