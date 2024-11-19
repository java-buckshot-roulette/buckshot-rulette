package game.service.player;


import static game.domain.item.ItemType.MAGNIFYING_GLASS;

import game.config.StageDependency;
import game.domain.Player;
import game.domain.bullet.Bullet;
import game.domain.item.Item;
import game.domain.item.ItemType;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;
import game.util.Convertor;
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
        //Todo: 소지하지 않은 아이템 입력 시 예외 메시지 출력 후 다시 입력 받기
        return processItemsUntilShotgun(rival, gameStateDto);
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
        player = player
                .initializeHealthPoint(stageDependency.getPlayerInitialHealthPoint())
                .initializeItems();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void setPlayerName(String s) {
        player = player.name(s);
    }

    private ItemUsageResponseDto processItemsUntilShotgun(PlayerDataDto rival, GameStateDto gameStateDto) {
        ItemUsageRequestDto itemUsageRequestDto = makeTargetingRival(rival, gameStateDto);

        while (true) {
            Item item = readItem(itemUsageRequestDto);

            // 샷건 아이템이 입력되면 처리 후 반환
            if (item.equals(ItemType.SHOT_GUN.getInstance())) {
                applyPlayerDataDto(itemUsageRequestDto.caster());
                return useShotGun(item, itemUsageRequestDto.target(), itemUsageRequestDto.gameDataDto());
            }

            // 돋보기 사용
            if (item.equals(MAGNIFYING_GLASS.getInstance())) {
                printFirstBullet(gameStateDto.bullets().CheckFirstBullet());
            }

            // 아이템 사용 후 다음 요청 DTO 업데이트
            itemUsageRequestDto = item.useItem(itemUsageRequestDto);
        }
    }


    private ItemUsageResponseDto useShotGun(Item shotGun, PlayerDataDto rival, GameStateDto gameStateDto) {
        String shotPerson = inputView.askPersonToBeShot();
        int firstBulletDamage = gameStateDto.bullets().getFirstBulletDamage();

        // 나에게 쐇을 때
        if (shotPerson.equals("나")) {
            printResultOfShot(firstBulletDamage);
            ItemUsageRequestDto targetingMe = shotGun.useItem(
                    new ItemUsageRequestDto(player.makePlayerDataDto(), player.makePlayerDataDto(), gameStateDto));
            return makeTargetingMe(rival, targetingMe.target(), targetingMe.gameDataDto());
        }

        // 상대에게 쐇을 때
        printResultOfShot(firstBulletDamage);
        ItemUsageRequestDto targetingRival = shotGun.useItem(
                new ItemUsageRequestDto(player.makePlayerDataDto(), rival, gameStateDto));
        return makeTargetingRival(targetingRival);
    }

    private void printResultOfShot(int damage) {
        if (damage == 0) {
            outputView.println("...틱 공포탄 입니다.");
            return;
        }
        outputView.println("...빵! 실탄 입니다.");
    }

    /**
     * 아이템 사용 요청 dto 생성
     *
     * @param rival        효과를 적용할 상대
     * @param gameStateDto 현재 게임 상태
     * @return 아이템 사용 요청 dto
     */
    private ItemUsageRequestDto makeTargetingRival(PlayerDataDto rival, GameStateDto gameStateDto) {
        return new ItemUsageRequestDto(player.makePlayerDataDto(), rival, gameStateDto);
    }

    /**
     * 아이템을 상대방에게 적용한 아이템 사용 결과 반환. 아이템을 사용한 후 나의 정보도 갱신한다.
     *
     * @param targetingRival 아이템을 적용했던 요청
     * @return 아이템을 적용한 후 상태의 response
     */
    private ItemUsageResponseDto makeTargetingRival(ItemUsageRequestDto targetingRival) {
        applyPlayerDataDto(targetingRival.caster());    //나의 정보 갱신
        return new ItemUsageResponseDto(targetingRival.target(), targetingRival.gameDataDto());
    }

    /**
     * 아이템을 나에게 사용했을 때 나의 정보를 갱신하고, ItemResponse 반환 ex) 샷건을 나에게 쏘았을 때
     *
     * @param rival        상대
     * @param caster       아이템 사용 후 나
     * @param gameStateDto 아이템 사용 후 상태
     * @return 아이템 사용 후 ItemResponse
     */
    private ItemUsageResponseDto makeTargetingMe(PlayerDataDto rival, PlayerDataDto caster, GameStateDto gameStateDto) {
        applyPlayerDataDto(caster);
        return new ItemUsageResponseDto(rival, gameStateDto);
    }

    private Item readItem(ItemUsageRequestDto itemUsageRequestDto) {
        String dealerItems = itemUsageRequestDto
                .target()
                .items()
                .toString();

        String challengerItems = itemUsageRequestDto
                .caster()
                .items()
                .toString();

        outputView.printPlayerState(itemUsageRequestDto.target(), itemUsageRequestDto.caster());
        return Convertor.StringToItem(inputView.readItem(dealerItems, challengerItems));
    }

    private void printFirstBullet(Bullet bullet) {
        outputView.println("첫번째 탄환은..." + bullet.toString());
    }
}
