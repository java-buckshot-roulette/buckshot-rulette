package game.exception;

public class GameException extends IllegalArgumentException {
    protected GameException(String message) {
        super(message);
    }
}
