import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    static ArrayList<String> validWords = new ArrayList<>();
    // Define the dimensions of the 2D array
    private static char[][] grid = {
            {'R', 'E', 'A', 'G', 'F'},
            {'C', 'I', 'U', 'F', 'A'},
            {'V', 'G', 'K', 'A', 'E'},
            {'A', 'B', 'C', 'D', 'T'},
            {'A', 'B', 'C', 'D', 'T'}
    };

    public static void main(String[] args) {
        Trie dictionaryTrie = new Trie();
        loadDictionary(dictionaryTrie, "/Users/alexthepark/Downloads/dictionary.txt");

        // Now, 'dictionaryTrie' contains all the English words from the file
        System.out.println("Total English words: " + dictionaryTrie.size());

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                traverse(grid, i, j, new StringBuilder(), dictionaryTrie.getRoot());
            }
        }

        printWords(validWords);
    }

    private static void traverse(char[][] grid, int row, int col, StringBuilder path, TrieNode node) {
        char letter = grid[row][col];

        // Check if the character is an alphabet letter
        if (!Character.isLetter(letter)) {
            return;
        }

        letter = Character.toUpperCase(letter);

        if (node.children[letter - 'A'] == null) {
            return;  // No words in the dictionary start with this path
        }

        path.append(letter);
        node = node.children[letter - 'A'];

        if (node.isEndOfWord) {
            validWords.add(path.toString());
            node.isEndOfWord = false;  // Avoid duplicate words
        }

        // Define the possible moves (up, down, left, right, and diagonals)
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        // Mark the cell as visited
        char original = grid[row][col];
        grid[row][col] = ' ';

        // Try each possible move
        for (int i = 0; i < 8; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];

            // Check if the new position is valid
            if (isValid(newRow, newCol)) {
                traverse(grid, newRow, newCol, path, node);
            }
        }

        // Restore the cell
        grid[row][col] = original;

        // Backtrack
        path.deleteCharAt(path.length() - 1);
    }



    private static boolean isValid(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public static void loadDictionary(Trie trie, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Trim leading/trailing whitespace and add the word to the Trie
                trie.insert(line.trim().toUpperCase()); // Convert to uppercase
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printWords(List<String> allValidWords) {
        for (int i = 16; i >= 3; i--) {
            ArrayList<String> iLengthWords = new ArrayList<>();
            for (int j = 0; j < allValidWords.size(); j++) {
                if (allValidWords.get(j).length() == i) {
                    iLengthWords.add(allValidWords.remove(j));
                    j--;
                }
            }
            if (!iLengthWords.isEmpty()) {
                System.out.println(i + " letter words:");
                for (int k = 0; k < iLengthWords.size(); k++) {
                    System.out.println(iLengthWords.get(k));
                }
                System.out.println();
            }
        }
    }
}

class Trie {
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

class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;

    public TrieNode() {
        children = new TrieNode[26]; // Assuming English alphabet
        isEndOfWord = false;
    }
}
