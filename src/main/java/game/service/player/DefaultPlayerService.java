package game.service.player;

import game.domain.Player;
import game.domain.item.Item;
import game.domain.item.ItemType;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;
import game.util.Convertor;
import game.view.input.InputView;

public class DefaultPlayerService implements PlayerService {
    private Player player;
    private final InputView inputView;

    public DefaultPlayerService(Player player, InputView inputView) {
        this.player = player;
        this.inputView = inputView;
    }

    @Override
    public ItemUsageResponseDto useItem(PlayerDataDto rival, GameStateDto gameStateDto) {
        //Todo: 소지하지 않은 아이템 입력 시 예외 메시지 출력 후 다시 입력 받기
        return processItemsUntilShotgun(rival, gameStateDto);
    }

    private ItemUsageResponseDto processItemsUntilShotgun(PlayerDataDto rival, GameStateDto gameStateDto) {
        Item item;
        while (!(item = readItem()).equals(ItemType.SHOT_GUN.getInstance())) {
            ItemUsageRequestDto newitemUsageRequestDto = item.useItem(
                    makeTargetingRival(rival, gameStateDto));
            return makeTargetingRival(newitemUsageRequestDto);
        }
        return useShotGun(item, rival, gameStateDto);
    }

    private ItemUsageResponseDto useShotGun(Item shotGun, PlayerDataDto rival, GameStateDto gameStateDto) {
        String shotPerson = inputView.askPersonToBeShot();

        // 나에게 쐇을 때
        if (shotPerson.equals("나")) {
            ItemUsageRequestDto targetingMe = shotGun.useItem(
                    new ItemUsageRequestDto(player.makePlayerDataDto(), player.makePlayerDataDto(), gameStateDto));
            return makeTargetingMe(rival, targetingMe.target(), targetingMe.gameDataDto());
        }

        // 상대에게 쐇을 때
        ItemUsageRequestDto targetingRival = shotGun.useItem(
                new ItemUsageRequestDto(player.makePlayerDataDto(), rival, gameStateDto));
        return makeTargetingRival(targetingRival);
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
        player = player.applyEffect(targetingRival.caster());   //나의 정보 갱신
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
        player = player.applyEffect(caster);
        return new ItemUsageResponseDto(rival, gameStateDto);
    }

    private Item readItem() {
        return Convertor.StringToItem(inputView.readItem());
    }

    private boolean hasItem(Item item) {
        return player.hasItem(item);
    }
}
