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
        boolean isInsideDoubleQuote = false;
        boolean passedBackslash = false;
        int i = 0, n = input.length();

        while (i < input.length() && !Character.isWhitespace(input.charAt(i))) i++;
        while (i < input.length() && Character.isWhitespace(input.charAt(i))) i++;

//        // TODO: Backslash inside single quote + outside quotes
//        for (int j = i; j < input.length(); j++) {
//            char c = input.charAt(j);
//
//            System.out.print(c + " :  ");
//            System.out.print(isInsideSingleQuote);
//            System.out.print(isInsideDoubleQuote);
//            System.out.print(passedBackslash);
//            System.out.println();
//
//            if (c == '\\') {
//                if (isInsideSingleQuote) {
//                    builder.append(c);
//                    continue;
//                }
//
//                if (passedBackslash) {
//                    builder.append(c);
//                    passedBackslash = false;
//                    continue;
//                }
//
//                passedBackslash = true;
//            } else if (passedBackslash) {
//                builder.append(c);
//                passedBackslash = false;
//            } else if (c == '\'') {
//                isInsideSingleQuote = !isInsideSingleQuote;
//                if (isInsideDoubleQuote) builder.append(c);
//            } else if (c == '"') {
//                isInsideDoubleQuote = !isInsideDoubleQuote;
//            } else if (isInsideDoubleQuote || isInsideSingleQuote || c != ' ') {
//                builder.append(c);
//            } else if (!builder.isEmpty()) {
//                result.add(builder.toString());
//                builder.delete(0, builder.length());
//            }
//        }
//
//        if (!builder.isEmpty()) result.add(builder.toString());
//
//        return result.toArray(String[]::new);

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
