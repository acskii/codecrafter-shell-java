import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInputParser {
    public static String getCommand(String input) {
        return tokeniseInput(input)[0];
    }

    public static String[] getArgs(String input) {
        String[] tokens = tokeniseInput(input);
        return Arrays.copyOfRange(tokens, 1, tokens.length);
    }

    public static String[] tokeniseInput(String input) {
        List<String> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        boolean isInsideSingleQuote = false;
        boolean isInsideDoubleQuote = false;
        int i = 0, n = input.length();

        while (i < n) {
            char c = input.charAt(i);

            if (isInsideSingleQuote) {
                if (c == '\'') {
                    isInsideSingleQuote = false;
                } else {
                    builder.append(c);
                }
                i++;
                continue;
            }

            if (isInsideDoubleQuote) {
                if (c == '"') {
                    isInsideDoubleQuote = false;
                    i++;
                    continue;
                }
                if (c == '\\' && i+1 < n && "\"\\$`".indexOf(input.charAt(i+1)) >= 0) {
                    builder.append(input.charAt(i+1));
                    i += 2;
                    continue;
                }
                builder.append(c);
                i++;
                continue;
            }

            if (c == '\'') {
                isInsideSingleQuote = true;
                i++;
            } else if (c == '"') {
                isInsideDoubleQuote = true;
                i++;
            } else if (c == '\\' && i+1 < n) {
                builder.append(input.charAt(i+1));
                i += 2;
            } else if (Character.isWhitespace(c)) {
                if (!builder.isEmpty()) {
                    result.add(builder.toString());
                    builder.setLength(0);
                }
                while (i < n && Character.isWhitespace(input.charAt(i))) i++;
            } else {
                builder.append(c);
                i++;
            }
        }
        if (!builder.isEmpty()) result.add(builder.toString());

        return result.toArray(String[]::new);
    }
}
