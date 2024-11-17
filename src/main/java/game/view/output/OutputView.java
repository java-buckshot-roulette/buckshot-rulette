package game.view.output;

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
}
