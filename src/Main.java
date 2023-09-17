import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ArrayList<String> validWords = new ArrayList<>();
    // Define the dimensions of the 2D array
    private static char[][] grid = new char[4][4];

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the Word Hunt Solver!");
        System.out.println("Please input your 4x4 Word Hunt board as a 16 character string with no spaces, going from left to right and starting with the first row going down.");

        String str = scan.nextLine();

        if (str.length()!=16){
            System.out.println("Sorry, your string was not 16 characters long. Please try again.");
        } else {
            for (int i = 0; i<4; i++){
                for (int j = 0; j<4; j++){
                    grid[i][j] = str.charAt(j*4 + i);
                }
            }
            Trie dictionaryTrie = new Trie();
            loadDictionary(dictionaryTrie, "/Users/alexthepark/Downloads/dictionary.txt");

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    traverse(grid, i, j, new StringBuilder(), dictionaryTrie.getRoot());
                }
            }

            printWords(validWords);

        }


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




