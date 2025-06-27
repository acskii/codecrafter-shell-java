import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoComplete {
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

    public AutoComplete() {
        addBuiltInCommands();
    }

    private void addBuiltInCommands() {
        for (String command : BuiltinCommands.getCommands()) {
            builtins.insert(command);
        }
    }

    public String[] getBuiltInCompletions(String prefix) {
        return builtins.getWordsFrom(prefix).toArray(String[]::new);
    }
}
