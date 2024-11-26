package game.service.player;

import game.domain.Role;
import java.util.List;

import static game.domain.bullet.BulletConfig.RED;
import static game.domain.bullet.BulletConfig.BLUE;

import static game.domain.item.ItemType.BEAR;
import static game.domain.item.ItemType.CIGARETTE_PACK;
import static game.domain.item.ItemType.EXPIRED_MEDICINE;
import static game.domain.item.ItemType.HAND_CUFFS;
import static game.domain.item.ItemType.HAND_SAW;
import static game.domain.item.ItemType.INVERTER;
import static game.domain.item.ItemType.MAGNIFYING_GLASS;
import static game.domain.item.ItemType.SHOT_GUN;

import game.domain.Player;
import game.domain.bullet.Bullet;
import game.domain.bullet.Bullets;
import game.domain.healthpoint.HealthPoint;
import game.config.StageDependency;
import game.domain.item.CigarettePack;
import game.domain.item.ExpiredMedicine;
import game.domain.item.HandSaw;
import game.domain.item.Item;
import game.domain.item.ItemType;
import game.domain.item.Items;
import game.dto.GameStateDto;
import game.dto.ItemUsageRequestDto;
import game.dto.ItemUsageResponseDto;
import game.dto.PlayerDataDto;
import game.util.Timer;
import game.view.output.OutputView;

public class AIPlayerService implements PlayerService {
    private Player player;
    private OutputView outputView;

    private Bullet nextBullet;

    private boolean isHandCuffsUsed;
    private boolean isHandSawUsed;

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
        isHandCuffsUsed = false;
        isHandSawUsed = false;
        nextBullet = null;

        while(true) {
            Bullets bullets = newitemUsageRequestDto.gameDataDto().bullets();
            if (bullets.isEmpty()) {
                return updatedData(newitemUsageRequestDto);
            }

            Item item = decideItem(newitemUsageRequestDto);
            if (item.equals(SHOT_GUN.getInstance())) {
                return useShotGun(item, newitemUsageRequestDto);
            }
            newitemUsageRequestDto = item.useItem(newitemUsageRequestDto);
        }
    }

    private Item decideItem(ItemUsageRequestDto itemUsageRequestDto) {
        outputView.printPlayerState(itemUsageRequestDto.caster(), itemUsageRequestDto.target());

        ItemType item = SHOT_GUN;

        if(tryToUseMagnifyingGlass(itemUsageRequestDto)) {        // 1. 돋보기
            item = MAGNIFYING_GLASS;
        } else if(tryToUseBeer(itemUsageRequestDto)) {            // 2. 맥주
            item = BEAR;
        } else if(tryToUseCigarettePack(itemUsageRequestDto)) {   // 3. 담배
            item = CIGARETTE_PACK;
        } else if(tryToUseExpiredMedicine(itemUsageRequestDto)) { // 4. 상한 약
            item = EXPIRED_MEDICINE;
        } else if(tryToUseHandCuffs(itemUsageRequestDto)) {       // 5. 수갑
            item = HAND_CUFFS;
        } else if(tryToUseHandSaw(itemUsageRequestDto)) {         // 6. 쇠톱
            item = HAND_SAW;
        } else if(tryToUseInverter(itemUsageRequestDto)) {        // 7. 인버터
            item = INVERTER;
        }

        Bullet firstBullet = itemUsageRequestDto.gameDataDto().bullets().CheckFirstBullet();
        printUsingItem(item.getInstance(), firstBullet);

        return item.getInstance();
    }

    /**
     * 다음 탄환이 무엇인지 모르면, 돋보기를 사용합니다.
     * @param request 아이템 사용 요청 정보
     * @return 돋보기 아이템 사용 여부
     */
    private boolean tryToUseMagnifyingGlass(ItemUsageRequestDto request) {
        Items inventory = request.caster().items();
        Item item = MAGNIFYING_GLASS.getInstance();

        Bullets bullets = request.gameDataDto().bullets();
        int red = bullets.getRedBulletCount();
        int blue = bullets.getBlueBulletCount();

        if (!inventory.contains(item) ||
            nextBullet != null || red == 0 || blue == 0) {
            return false;
        }

        nextBullet = bullets.CheckFirstBullet();

        return true;
    }

    /**
     * 다음 탄환이 무엇인지 모르면, 맥주를 사용합니다.
     * @param request 아이템 사용 요청 정보
     * @return 맥주 아이템 사용 여부
     */
    private boolean tryToUseBeer(ItemUsageRequestDto request) {
        Items inventory = request.caster().items();
        Item item = BEAR.getInstance();

        Bullets bullets = request.gameDataDto().bullets();
        int red = bullets.getRedBulletCount();
        int blue = bullets.getBlueBulletCount();

        if (!inventory.contains(item) ||
            nextBullet != null || red == 0 || blue == 0) {
            return false;
        }

        return true;
    }

    /**
     * 체력 회복 가능한 경우, 담배를 사용합니다.
     * @param request 아이템 사용 요청 정보
     * @return 담배 아이템 사용 여부
     */
    private boolean tryToUseCigarettePack(ItemUsageRequestDto request) {
        Items inventory = request.caster().items();
        Item item = CIGARETTE_PACK.getInstance();
        HealthPoint health = request.caster().healthPoint();
        
        if (!inventory.contains(item) ||
            !health.isPossibleToHeal(((CigarettePack)item).getHealingPoint())) {
            return false;
        }

        return true;
    }

    /**
     * 체력 회복 가능한 경우, 상한 약을 사용합니다.
     * @param request 아이템 사용 요청 정보
     * @return 상한 약 아이템 사용 여부
     */
    private boolean tryToUseExpiredMedicine(ItemUsageRequestDto request) {
        Items inventory = request.caster().items();
        Item item = EXPIRED_MEDICINE.getInstance();
        HealthPoint health = request.caster().healthPoint();

        if(!inventory.contains(item) ||
           !health.isPossibleToHeal(((ExpiredMedicine)item).getHealingPoint())) {
            return false;
        }

        return true;
    }

    /**
     * 이번 턴에 수갑을 사용하지 않은 경우, 수갑을 사용합니다.
     * @param request 아이템 사용 요청 정보
     * @return 수갑 아이템 사용 여부
     */
    private boolean tryToUseHandCuffs(ItemUsageRequestDto request) {
        Items inventory = request.caster().items();
        Item item = HAND_CUFFS.getInstance();

        Bullets bullets = request.gameDataDto().bullets();
        int bulletCount = bullets.getRedBulletCount() + bullets.getBlueBulletCount();

        // 탄 개수가 1개인 경우, 샷건 사용 시 무조건 턴이 플레이어에게 넘어가므로 사용하지 않음
        if(!inventory.contains(item) || isHandCuffsUsed || bulletCount == 1) {
            return false;
        }

        isHandCuffsUsed = true;

        return true;
    }

    /**
     * 다음 탄환이 실탄임을 알고 있으면, 쇠톱을 사용합니다.
     * @param request 아이템 사용 요청 정보
     * @return 쇠톱 아이템 사용 여부
     */
    private boolean tryToUseHandSaw(ItemUsageRequestDto request) {
        Items inventory = request.caster().items();
        HandSaw item = (HandSaw) HAND_SAW.getInstance();

        Bullets bullets = request.gameDataDto().bullets();

        int red = bullets.getRedBulletCount();
        int blue = bullets.getBlueBulletCount();

        if(!inventory.contains(item) || isHandSawUsed) {
            return false;
        }
        if(nextBullet == null && red <= blue) {
            return false;
        }
        if(nextBullet != null && nextBullet.equals(BLUE)) {
            return false;
        }

        isHandSawUsed = true;

        return true;
    }

    /**
     * 다음 탄환이 공포탄임을 알고 있으면, 인버터를 사용합니다.
     * @param request 아이템 사용 요청 정보
     * @return 인버터 아이템 사용 여부
     */
    private boolean tryToUseInverter(ItemUsageRequestDto request) {
        Items inventory = request.caster().items();
        Item item = INVERTER.getInstance();

        if(!inventory.contains(item) || nextBullet == null) {
            return false;
        }
        if(nextBullet.equals(RED)) {
            return false;
        }

        nextBullet = RED;

        return true;
    }

    private ItemUsageResponseDto useShotGun(Item shotGun, ItemUsageRequestDto itemUsageRequestDto) {
        GameStateDto state = itemUsageRequestDto.gameDataDto();

        int firstBulletDamage = state.bullets().getFirstBulletDamage();

        int redCount = state.bullets().getRedBulletCount();
        int blueCount = state.bullets().getBlueBulletCount();

        ItemUsageResponseDto result = null;
        if ((nextBullet != null && nextBullet.equals(RED))) {
            result = shootAtRival(shotGun, firstBulletDamage, itemUsageRequestDto); // 1. 실탄 확정: 상대방을 공격
        } else if ((nextBullet != null && nextBullet.equals(BLUE))) {
            result = shootAtMe(shotGun, firstBulletDamage, itemUsageRequestDto);    // 2. 공포탄 확정: 자신을 공격
        } else if (redCount >= blueCount) {
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

    private void printUsingItem(Item item, Bullet firstBullet) {
        Timer.delay(1000);
        outputView.println("딜러가 " + item.toString() + "을(를) 사용합니다.\n");
        Timer.delay(1000);

        if(item.equals(ItemType.BEAR.getInstance())) {
            outputView.println("..팅! " + firstBullet.toString() + " 탄환이 빠져나왔습니다.\n");
            Timer.delay(2000);
        }
    }

    @Override
    public boolean isPlayerRole(Role role) {
        return player.isPlayerRole(role);
    }
}
