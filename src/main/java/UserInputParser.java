import java.util.Arrays;

public class UserInputParser {
    private static String[] tokeniseInput(String input) {
        return input.split(" ");
    }

    public static String getCommand(String input) {
        return tokeniseInput(input)[0];
    }

    public static String[] getArgs(String input) {
        return Arrays.stream(tokeniseInput(input)).skip(1).toArray(String[]::new);
    }
}
