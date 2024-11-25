package game.exception;

public class IllegalStartingNumberException extends GameException {
    private static final String MESSAGE = "[ERROR] 잘못된 입력입니다. 다시 입력해주세요.\n";

    public IllegalStartingNumberException() {
        super(MESSAGE);
    }
}
