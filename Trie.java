public class Trie {
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public TrieNode getRoot() {
        return root;
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'A';
            if (index >= 0 && index < 26) {
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            } else {
                // Handle invalid characters here if needed
            }
        }
        node.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'A';
            if (index >= 0 && index < 26) {
                if (node.children[index] == null) {
                    return false;
                }
                node = node.children[index];
            } else {
                // Handle invalid characters here if needed
                return false;
            }
        }
        return node.isEndOfWord;
    }

    public int size() {
        return size(root);
    }

    private int size(TrieNode node) {
        if (node == null) {
            return 0;
        }
        int count = 0;
        if (node.isEndOfWord) {
            count++;
        }
        for (TrieNode child : node.children) {
            count += size(child);
        }
        return count;
    }
}