package game.view.output;

import game.dto.PlayerDataDto;
import game.service.Stage.GameState;
import game.util.Timer;

public class OutputView {

    public void printPersonToBeShot() {
        println("\n누구를 겨냥 할지 입력해 주세요. (예: 나/상대)");
    }

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printMenu() {
        println("\n" +
                "   ___  __  _________ ________ ______  ______  ___  ____  __  ____   ____________________\n" +
                "  / _ )/ / / / ___/ //_/ __/ // / __ \\/_  __/ / _ \\/ __ \\/ / / / /  / __/_  __/_  __/ __/\n" + 
                " / _  / /_/ / /__/ ,< _\\ \\/ _  / /_/ / / /   / , _/ /_/ / /_/ / /__/ _/  / /   / / / _/  \n" +
                "/____/\\____/\\___/_/|_/___/_//_/\\____/ /_/   /_/|_|\\____/\\____/____/___/ /_/   /_/ /___/  \n" +
                "                                                                                         \n" +
                "\n");
        println("1. 게임 시작\n");
        println("2. 게임 종료\n");
    }

    public void printItemReadMessage() {
        println("사용할 아이템을 입력해 주세요. (ex1: 돋보기 / ex2: 총)");
    }

    public void printStage(int stageNumber) {
        println("\n################################");
        println("###        " + stageNumber + " 스테이지" + "        ###");
        println("################################\n");
        Timer.delay(1000);
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
        println("------  아이템 목록  ------\n");
        println("딜러");
        println(dealerItems + "\n");
        println("플레이어");
        println(challengerItems + "\n");
        println("---------------------------\n");
    }

    public void printBullet(String bullets) {
        println(bullets);
        println("무작위로 섞는 중...\n");
        Timer.delay(1000);
        println("장전완료!\n");
        Timer.delay(2000);
    }

    public void printResult(GameState result) {
        if (result.equals(GameState.GAME_CLEAR)) {
            println("축하합니다!");
        } else {
            println("당신은 죽었습니다..");
        }
        Timer.delay(1000);
    }
}
