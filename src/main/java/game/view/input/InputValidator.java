package game.view.input;

import static game.util.Convertor.parseInt;

import game.exception.IllegalStartingNumberException;

public class InputValidator {

    private InputValidator() {
    }

    public static void validateStartingNumber(String number) {
        try {
            validateNumberFormat(number);
            validateStartingNumberRange(number);
        } catch (Exception exception) {
            throw new IllegalStartingNumberException();
        }
    }

    private static void validateStartingNumberRange(String number) {
        int i = parseInt(number);
        if (!(i >= 1 && i <= 2)) {
            throw new IllegalStartingNumberException();
        }
    }

    private static void validateNumberFormat(String number) {
        try {
            parseInt(number);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException();
        }
    }
}

