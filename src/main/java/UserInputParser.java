import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInputParser {
    private static String[] tokeniseInput(String input) {
        return input.split(" ");
    }

    public static String getCommand(String input) {
        return tokeniseInput(input)[0];
    }

    public static String[] getArgs(String input) {
        List<String> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        boolean isInsideSingleQuote = false;
        int i = 0;

        while (i < input.length() && !Character.isWhitespace(input.charAt(i))) i++;
        while (i < input.length() && Character.isWhitespace(input.charAt(i))) i++;

        for (int j = i; j < input.length(); j++) {
            char c = input.charAt(j);

            if (c == '\'') {
                isInsideSingleQuote = !isInsideSingleQuote;
            } else if (isInsideSingleQuote | c != ' ') {
                builder.append(c);
            } else if (!builder.isEmpty()) {
                result.add(builder.toString());
                builder.delete(0, builder.length());
            }
        }

        if (!builder.isEmpty()) result.add(builder.toString());

        return result.toArray(String[]::new);
    }
}
