import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInputParser {
    public static String getCommand(String input) {
        return tokeniseInput(getPromptToken(input))[0];
    }

    public static String[] getArgs(String input) {
        String[] tokens = tokeniseInput(getPromptToken(input));
        return Arrays.copyOfRange(tokens, 1, tokens.length);
    }

    private static boolean hasRedirector(String input) {
        return input.contains(" 1> ") || input.contains(" > ");
    }

    private static String getPromptToken(String input) {
        if (hasRedirector(input)) {
            String[] parts = input.split("( 1> )|( > )");
            return parts[0];
        } else {
            return input;
        }
    }

    private static String getStreamToken(String input) {
        if (hasRedirector(input)) {
            String[] parts = input.split("( 1> )|( > )");
            return parts[1];
        } else {
            return null;
        }
    }

    public static String getOutputStream(String input) {
        String streamToken = getStreamToken(input);
        if (streamToken != null) {
            Path path = Paths.get(streamToken);
            return path.normalize().toString();
        } else {
            return null;
        }
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
