/*
 * Course: CSC-1110
 * Assignment: Text Processing
 * Name: Keagan Weinstock
 */
package weinstockk;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Driver that runs all the code
 */
public class Driver {
    private static final String DATA_FOLDER = "data";

    public static void main(String[] args) {
        // Instantiate your collections and other variables here
        List<BasicWord> words = new ArrayList<>();
        List<Word> bigrams = new ArrayList<>();
        List<Word> vocabulary = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        // Read file into Scanner
        Scanner read = null;
        boolean play = true;
        do {
            try {
                // Ask user for the file to read
                String fileName = getInput(scanner, "Enter the file to read: ");
                fileName = fileName.substring(fileName.indexOf(":") + 2);
                File inputFile = new File(DATA_FOLDER, fileName);
                read = new Scanner(inputFile);
                play = false;
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        } while (play);

        // Trim heading out of file
        System.out.println("Processing...");
        removeHeader(read);

        // Generate words and add to a list
        System.out.println("Getting the words...");
        addWords(words, read);

        // Generate bigrams from the list of words
        System.out.println("Making the bigrams...");
        addBigrams(bigrams, words);

        // Generate vocabulary from the list of words
        System.out.println("Generating vocabulary...");
        addVocabulary(vocabulary, words);

        // Sort the data
        System.out.println("Sorting lists...");
        bigrams.sort(Collections.reverseOrder());
        vocabulary.sort(Collections.reverseOrder());

        // Save vocabulary as a text file
        play = true;
        do {
            try {
                System.out.println("Saving vocabulary...");
                String vocabFileName = getInput(scanner, "Enter the vocabulary file to save: ");
                vocabFileName = vocabFileName.substring(vocabFileName.indexOf(":") + 2);
                saveFile(vocabulary, new File(DATA_FOLDER, vocabFileName));

                // Save bigrams as a text file
                System.out.println("Saving bigrams...");
                String bigramFileName = getInput(scanner, "Enter the bigram file to save: ");
                saveFile(bigrams, new File(DATA_FOLDER, bigramFileName));
                play = false;
            } catch (FileNotFoundException e) {
                System.out.println("File not found");

            }
        } while (play);

        // Generate reports
        System.out.println("Generating reports...");

        // Ask for how many top entries to show
        play = true;
        do {
            try {
                String topHits = getInput(scanner,
                        "Enter the number of top entries to show: ");
                topHits = topHits.substring(topHits.indexOf(":") + 2);
                int hits = Integer.parseInt(topHits);

                // Report the top entries for vocabulary
                report(vocabulary, "Vocabulary", hits);

                // Report the top entries for bigrams
                report(bigrams, "Bigrams", hits);
                play = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        } while (play);

    }

    private static String getInput(Scanner in, String message) {
        System.out.print(message);
        return message + in.nextLine();
    }
    private static void removeHeader(Scanner read) {
        String line;
        do {
            line = read.nextLine();
        } while(!line.contains("*** START OF THE PROJECT GUTENBERG EBOOK"));
    }
    private static void addWords(List<BasicWord> words, Scanner read) {
        int location = 0;
        String word = "";
        while (!word.startsWith("***")){
            word = read.next();
            String normalized = normalize(word);
            if (!normalized.isEmpty()) {
                words.add(new BasicWord(normalized, location++));
            }
        }
    }
    private static String normalize(String s) {
        s = s.toLowerCase();
        return s.replaceAll("\\p{P}", "");
    }

    private static void addBigrams(List<Word> bigrams, List<BasicWord> words) {
        for (int i = 0; i < words.size() - 1; i++) {
            Bigram bigram = new Bigram(words.get(i), words.get(i+1));
            if(!bigrams.contains(bigram)) {
                bigrams.add(bigram);
            } else {
                bigrams.get(bigrams.indexOf(bigram)).addLocation(words.get(i).getLocation());
            }
        }
    }
    private static void addVocabulary(List<Word> vocabulary, List<BasicWord> words) {
        for (BasicWord word : words) {
            VocabularyEntry vocab = new VocabularyEntry(word);
            if (!vocabulary.contains(vocab)) {
                vocabulary.add(vocab);
            } else {
                vocabulary.get(vocabulary.indexOf(vocab)).addLocation(word.getLocation());
            }
        }
    }

    private static void saveFile(List<Word> list, File output) throws FileNotFoundException {
        try(PrintWriter writer = new PrintWriter(output)) {
            for (Word word : list) {
                writer.println(word.toString());
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
    private static void report(List<Word> list, String type, int topHits) {
        System.out.print("Top " + topHits + " " + type + " are:\n");
        if (topHits > list.size()) {
            topHits = list.size();
        }

        for (int i = 0; i < topHits; i++) {
            System.out.printf("%-5d %20s\n", i + 1, list.get(i));
        }
    }
}
