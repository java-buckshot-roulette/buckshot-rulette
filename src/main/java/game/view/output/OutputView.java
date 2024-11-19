package game.view.output;

import game.dto.PlayerDataDto;

public class OutputView {

    public void printPersonToBeShot() {
        println("누구를 겨냥 할지 입력해 주세요. (예: 나/상대)");
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printItemReadMessage() {
        println("사용할 아이템을 입력해 주세요.");
    }

    public void printStage(int stageNumber) {
        println("-----------");
        println(stageNumber + "스테이지");
        println("-----------\n");
    }

    public void printPlayerState(PlayerDataDto dealer, PlayerDataDto challenger) {
        String dealerHealth = dealer.healthPoint().toString();
        String playerHealth = challenger.healthPoint().toString();
        String dealerItems = dealer.items().toString();
        String playerItems = challenger.items().toString();

        printPlayersHealthPoint(dealerHealth, playerHealth);
        printPlayersItems(dealerItems, playerItems);
    }

    public void printPlayersHealthPoint(String dealerHealth, String challengerHealth) {
        println("딜러의 체력");
        println(dealerHealth + "\n");
        println("플레이어의 체력");
        println(challengerHealth + "\n");
    }

    public void printPlayersItems(String dealerItems, String challengerItems) {
        println("아이템 목록\n\n" + "딜러");
        println(dealerItems + "\n");
        println("플레이어");
        println(challengerItems + "\n");
    }

    public void printBullet(String bullets) {
        println(bullets);
        println("무작위로 섞는 중...\n\n장전완료!\n");
    }
}
