package game.view.input;

import game.view.output.OutputView;
import java.util.Scanner;

public class InputView {
    private final OutputView outputView;
    private static Scanner scanner;

    private static Scanner getInstance() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }

        return scanner;
    }

    private static String readLine() {
        return getInstance().nextLine();
    }

    public InputView(OutputView outputView) {
        this.outputView = outputView;
    }

    public String askPersonToBeShot() {
        outputView.printPersonToBeShot();
        return readLine();
    }


    public String readItem() {
        outputView.printItemReadMessage();
        return readLine();
    }

    public String askStartingNumber() {
        outputView.print("선택 : ");
        String number = readLine();
        InputValidator.validateStartingNumber(number);
        return number;
    }

    public String readName() {
        outputView.println("이름을 입력해 주세요.");
        return readLine();
    }
}
