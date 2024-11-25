package game.exception;

public class NonExistItemException extends GameException{
    private static final String MESSAGE = "[ERROR] 존재하지 않는 아이템 입니다.\n";

    public NonExistItemException() {
        super(MESSAGE);
    }
}
