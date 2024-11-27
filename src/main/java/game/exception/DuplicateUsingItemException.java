package game.exception;

public class DuplicateUsingItemException extends GameException {
    private static final String MESSAGE = "[ERROR] 중복 사용이 불가능한 아이템입니다.\n";

    public DuplicateUsingItemException() {
        super(MESSAGE);
    }
}
