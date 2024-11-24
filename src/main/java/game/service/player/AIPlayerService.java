package game.service.player;

import java.util.List;

import static game.domain.bullet.BulletConfig.RED;
import static game.domain.bullet.BulletConfig.BLUE;
import static game.domain.item.ItemType.MAGNIFYING_GLASS;
import static game.domain.item.ItemType.SHOT_GUN;

import game.domain.Player;
import game.domain.bullet.Bullet;
import game.config.StageDependency;
import game.domain.item.Item;
import game.domain.item.ItemType;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;
import game.util.Timer;
import game.view.output.OutputView;

public class AIPlayerService implements PlayerService {
    private Player player;
    private OutputView outputView;

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

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void setPlayerName(String s) {
        player = player.name(s);
    }

    private ItemUsageResponseDto processItemsUntilShotgun(PlayerDataDto rival, GameStateDto gameStateDto) {
        ItemUsageRequestDto newitemUsageRequestDto = new ItemUsageRequestDto(
                                                    player.makePlayerDataDto(), // 1. caster 
                                                    rival,                      // 2. target
                                                    gameStateDto);              // 3. game data (탄환 & 턴 정보)
        while(true) {
            GameStateDto state = newitemUsageRequestDto.gameDataDto();
            if(state.bullets().isEmpty()) {
                return updatedData(newitemUsageRequestDto);
            }
            
            Item item = decideItem(newitemUsageRequestDto);
            if (item.equals(ItemType.SHOT_GUN.getInstance())) {
                printUsingItem(item, gameStateDto);
                return useShotGun(item, newitemUsageRequestDto);
            }

            newitemUsageRequestDto = item.useItem(newitemUsageRequestDto);
            printUsingItem(item, gameStateDto);
            if (item.equals(MAGNIFYING_GLASS.getInstance())) {
                nextBullet = newitemUsageRequestDto.gameDataDto().bullets().CheckFirstBullet();
            }       
        }
    }

    private Item decideItem(ItemUsageRequestDto itemUsageRequestDto) {
        outputView.printPlayerState(itemUsageRequestDto.caster(), itemUsageRequestDto.target()); 
        Item item = SHOT_GUN.getInstance(); // 테스트용 코드 (항상 샷건 사용)
        
        // A.I. 작동 로직
        /*
         * 1. 맥주
         * 2. 담배: 체력이 감소된 상태라면, 무조건 사용
         * 3. 상한약
         * 4. 수갑
         * 5. 쇠톱
         * 6. 인버터
         * 7. 돋보기
         * 7. 샷건
         */

        return item;
    }

    private ItemUsageResponseDto useShotGun(Item shotGun, ItemUsageRequestDto itemUsageRequestDto) {
        GameStateDto state = itemUsageRequestDto.gameDataDto();

        int firstBulletDamage = state.bullets().getFirstBulletDamage();

        int redCount = state.bullets().getRedBulletCount();
        int blueCount = state.bullets().getBlueBulletCount();

        ItemUsageResponseDto result = null;
        if((nextBullet != null && nextBullet.equals(RED))) {                        
            result = shootAtRival(shotGun, firstBulletDamage, itemUsageRequestDto); // 1. 실탄 확정: 상대방을 공격
        } else if((nextBullet != null && nextBullet.equals(BLUE))) {                
            result = shootAtMe(shotGun, firstBulletDamage, itemUsageRequestDto);    // 2. 공포탄 확정: 자신을 공격
        } else if(redCount >= blueCount) {                                                    
            result = shootAtRival(shotGun, firstBulletDamage, itemUsageRequestDto); // 3. 실탄이 더 많은 경우: 상대방을 공격
        } else {                                                                    
            result = shootAtMe(shotGun, firstBulletDamage, itemUsageRequestDto);    // 4. 공포탄이 더 많은 경우: 자신을 공격
        }

        return result;
    }

    private ItemUsageResponseDto shootAtRival(Item shotGun, int firstBulletDamage,
                                                ItemUsageRequestDto itemUsageRequestDto) {
        outputView.println("딜러가 당신을 향해 총을 겨눕니다!");
        Timer.delay(1000);
        outputView.printResultOfShot(firstBulletDamage);
        return updatedData(shotGun.useItem(itemUsageRequestDto));
    }

    private ItemUsageResponseDto shootAtMe(Item shotGun, int firstBulletDamage, 
                                            ItemUsageRequestDto itemUsageRequestDto) {
        outputView.println("딜러가 자신을 향해 총을 겨눕니다!");
        Timer.delay(1000);          
        // 1. 타겟을 자신으로 변경
        ItemUsageRequestDto targetMe = itemUsageRequestDto.changeTargetData(itemUsageRequestDto.caster());
        // 2. 격발
        ItemUsageRequestDto shootMe = shotGun.useItem(targetMe);
        // 3. 자신의 상태 갱신 / 타겟을 원래대로 변경
        shootMe = shootMe.changeCasterData(shootMe.target())
                         .changeTargetData(itemUsageRequestDto.target());

        outputView.printResultOfShot(firstBulletDamage);
        return updatedData(shootMe);
    }

    /**
     * 시전자의 상태를 갱신하고, 변경된 타겟 및 게임 상태 정보를 반환합니다.
     * @param itemUsageRequestDto 시전자, 타겟, 게임 상태 정보
     * @return 변경된 플레이어 및 게임 상태 정보
     */
    private ItemUsageResponseDto updatedData(ItemUsageRequestDto itemUsageRequestDto) {
        applyPlayerDataDto(itemUsageRequestDto.caster());
        return new ItemUsageResponseDto(itemUsageRequestDto.target(), itemUsageRequestDto.gameDataDto());
    }

    private void printUsingItem(Item item, GameStateDto gameStateDto) {
        Timer.delay(1000);
        outputView.println("딜러가 " + item.toString() + "을(를) 사용합니다.\n");
        Timer.delay(1000);

        if(item.equals(ItemType.BEAR.getInstance())) {
            Bullet firstBullet = gameStateDto.bullets().CheckFirstBullet();
            outputView.println("..팅! " + firstBullet.toString() + " 탄환이 빠져나왔습니다.\n");
            Timer.delay(2000);
        }
    }
}
