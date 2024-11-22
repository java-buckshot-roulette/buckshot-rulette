package game.exception;

public class NonExistItemException extends GameException{
    private static final String MESSAGE = "[ERROR] 존재하지 않은 아이템 입니다.";

    public NonExistItemException() {
        super(MESSAGE);
    }
}
