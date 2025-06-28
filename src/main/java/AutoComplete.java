import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class AutoComplete {
    public enum Result {
        NONE, MORE, SINGLE;
    }

    private static final Comparator<String> SHORTEST_FIRST = Comparator
            .comparingInt(String::length)
            .thenComparing(String::compareTo);

    private static class Trie {
        public static class TrieNode {
            Map<Character, TrieNode> children = new HashMap<>();
            boolean endOfWord;
        }

        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode cur = root;

            for (char c : word.toCharArray()) {
                cur.children.putIfAbsent(c, new TrieNode());
                cur = cur.children.get(c);
            }

            cur.endOfWord = true;
        }

        public List<String> getWordsFrom(String prefix) {
            List<String> results = new ArrayList<>();
            TrieNode cur = root;

            for (char c : prefix.toCharArray()) {
                if (cur.children.get(c) == null) {
                    return results;
                }
                cur = cur.children.get(c);
            }

            collectRestOfWords(prefix, cur, results);
            return results;
        }

        private void collectRestOfWords(String prefix, TrieNode cur, List<String> results) {
            if (cur.endOfWord) results.add(prefix);
            for (Map.Entry<Character, TrieNode> entry : cur.children.entrySet()) {
                collectRestOfWords(prefix + entry.getKey(), entry.getValue(), results);
            }
        }
    }

    private final Trie builtins = new Trie();
    private final Trie executablesFromPath = new Trie();

    public AutoComplete() {
        addBuiltInCommands();
        addExecutablesFromPath();
    }

    private void addBuiltInCommands() {
        for (String command : BuiltinCommands.getCommands()) {
            builtins.insert(command);
        }
    }

    private void addExecutablesFromPath() {
        for (String command : Executable.getExecutablesFromPath()) {
            builtins.insert(command);
        }
    }

    public Result autoComplete(StringBuilder buffer, boolean bell) {
        String prefix = buffer.toString();
        if (prefix.isBlank()) { return Result.NONE; }

        SequencedSet<String> completions = getCompletions(prefix);

        if (completions.isEmpty()) { return Result.NONE; }

        if (completions.size() == 1) {
            writeToOutput(buffer, completions.getFirst(), false);
            return Result.SINGLE;
        }

        String longestPrefix = getLongestSharedPrefix(completions);

        if (!longestPrefix.isEmpty()) {
            writeToOutput(buffer, longestPrefix, true);
            return Result.MORE;
        }

        if (bell) {
            System.out.print("\n");
            System.out.print(completions.stream().map(prefix::concat).collect(Collectors.joining("  ")));
            System.out.print("\n");
            System.out.print("$ ");
            System.out.print(prefix);
            System.out.flush();
        }

        return Result.MORE;
    }

    private void writeToOutput(StringBuilder buffer, String prefix, boolean hasMore) {
        buffer.append(prefix);
        System.out.print(prefix);
        if (!hasMore) {
            buffer.append(" ");
            System.out.print(" ");
        }
    }

    private SequencedSet<String> getCompletions(String prefix) {
        List<String> builtInSuggestions = builtins.getWordsFrom(prefix);
        String[] completions;

        if (builtInSuggestions.isEmpty()) {
            List<String> executableSuggestions = executablesFromPath.getWordsFrom(prefix);

            completions = executableSuggestions.toArray(String[]::new);
        } else {
            completions = builtInSuggestions.toArray(String[]::new);
        }

        return Arrays.stream(completions)
                .map((candidate) -> candidate.substring(prefix.length())).
                collect(Collectors.toCollection(() -> new TreeSet<>(SHORTEST_FIRST)));
    }

    private String getLongestSharedPrefix(SequencedSet<String> candidates) {
        String first = candidates.getFirst();
        if (first.isEmpty()) return "";

        int i = 0;

        for (; i < first.length(); i++) {
            boolean oneIsNotMatching = false;

            for (String candidate : candidates) {
                if (!first.subSequence(0, i).equals(candidate.subSequence(0, i))) {
                    oneIsNotMatching = true;
                    break;
                }
            }

            if (oneIsNotMatching) {
                i--;
                break;
            }
        }

        return first.substring(0, i);
    }
}
