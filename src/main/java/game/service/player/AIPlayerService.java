package game.service.player;

import java.util.List;

import static game.domain.bullet.BulletConfig.BLUE;
import static game.domain.item.ItemType.MAGNIFYING_GLASS;
import static game.domain.item.ItemType.SHOT_GUN;

import game.domain.Player;
import game.domain.bullet.Bullet;
import game.domain.bullet.BulletConfig;
import game.config.StageDependency;
import game.domain.item.Item;
import game.domain.item.ItemType;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;
import game.util.Convertor;
import game.util.Timer;
import game.view.output.OutputView;

public class AIPlayerService implements PlayerService {
    private Player player;
    private OutputView outputView;

    private int red;
    private int blue;

    private Bullet nextBullet = null;

    public AIPlayerService(Player player, OutputView outputView) {
        this.player = player;
        this.outputView = outputView;
    }

    @Override
    public ItemUsageResponseDto useItem(PlayerDataDto rival, GameStateDto gameStateDto) {
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

    private ItemUsageResponseDto processItemsUntilShotgun(PlayerDataDto rival, GameStateDto gameStateDto) {
        Item item;
        ItemUsageRequestDto newitemUsageRequestDto = makeTargetingRival(rival, gameStateDto);
        while((item = readItem(newitemUsageRequestDto)).equals(SHOT_GUN.getInstance())) {
            printUsingItem(item);
            if (item.equals(MAGNIFYING_GLASS.getInstance())) {
                decideNextBullet(gameStateDto.bullets().CheckFirstBullet());
            }
            newitemUsageRequestDto = item.useItem(makeTargetingRival(rival, gameStateDto));
            return makeTargetingRival(newitemUsageRequestDto);
        }
        return useShotGun(item, rival, gameStateDto);
    }

    private Item readItem(ItemUsageRequestDto itemUsageRequestDto) {
        outputView.printPlayerState(itemUsageRequestDto.caster(), itemUsageRequestDto.target()); 
        Item item = SHOT_GUN.getInstance();

        /*
         * 1. 맥주: red, blue 조정 필요
         * 2. 담배
         * 3. 상한약
         * 4. 수갑
         * 5. 인버터
         * 6. 돋보기
         * 7. 샷건
         */

        return item;
    }

    private ItemUsageResponseDto useShotGun(Item shotGun, PlayerDataDto rival, GameStateDto gameStateDto) {
        int firstBulletDamage = gameStateDto.bullets().getFirstBulletDamage();
        if((nextBullet != null && nextBullet.equals(BLUE)) || red < blue) {
            return shootAtMe(shotGun, rival, gameStateDto, firstBulletDamage);   
        } 
        return shootAtRival(shotGun, rival, gameStateDto, firstBulletDamage);
    }

    private ItemUsageResponseDto shootAtMe(Item shotGun, PlayerDataDto rival, 
                                            GameStateDto gameStateDto, int firstBulletDamage) {
        outputView.println("딜러가 자신을 향해 총을 겨눕니다!");
        printResultOfShot(firstBulletDamage);
        ItemUsageRequestDto targetingMe = shotGun.useItem(
                new ItemUsageRequestDto(player.makePlayerDataDto(), 
                                        player.makePlayerDataDto(), 
                                        gameStateDto));
        return makeTargetingMe(rival, targetingMe.target(), targetingMe.gameDataDto());
    }

    private ItemUsageResponseDto shootAtRival(Item shotGun, PlayerDataDto rival, 
                                                GameStateDto gameStateDto, int firstBulletDamage) {
        outputView.println("딜러가 당신을 향해 총을 겨눕니다!");
        printResultOfShot(firstBulletDamage);
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

    /**
     * 딜러가 다음에 발사될 탄환이 무엇인지 확신합니다.
     * @param nextBullet 다음에 발사될 탄환
     */
    private void decideNextBullet(Bullet nextBullet) {
        if(nextBullet.equals(BulletConfig.RED)) {
            this.nextBullet = BulletConfig.RED;
        } else {
            this.nextBullet = BulletConfig.BLUE;
        }
    }

    /**
     * 딜러의 아이템 사용 메시지를 출력합니다.
     * @param item 사용된 아이템
     */
    private void printUsingItem(Item item) {
        outputView.println("딜러가 " + item.toString() + "을(를) 사용합니다.\n");
        Timer.delay(1000);
    }

    /**
     * 딜러의 총 발사 결과를 출력합니다.
     * @param damage 탄환 데미지
     */
    private void printResultOfShot(int damage) {
        outputView.println("\n철컥...\n");
        Timer.delay(1000);
        if (damage == 0) {
            outputView.println("...틱 공포탄 입니다.\n");
        } else {
            outputView.println("...빵! 실탄 입니다.\n");
        }
        Timer.delay(2000);
    }
}
