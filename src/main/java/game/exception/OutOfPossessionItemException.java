package game.exception;

public class OutOfPossessionItemException extends GameException{
    private static final String MESSAGE = "[ERROR] 소지하지 않은 아이템 입니다.";

    public OutOfPossessionItemException() {
        super(MESSAGE);
    }
}
