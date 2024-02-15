/*
 * Course: CSC-1110
 * Assignment: Text Processing
 * Name: Keagan Weinstock
 */
package weinstockk;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a bigram
 */
public class Bigram extends Word {
    private final String word2;
    private int occurrences;
    private final List<Long> locations;

    /**
     * Constructor for a Bigram
     * @param word1 the first Word in the Bigram
     * @param word2 the second Word in the Bigram
     */
    public Bigram(BasicWord word1, BasicWord word2) {
        super(word1.word);
        this.word2 = word2.word;
        locations = new ArrayList<>();
        occurrences = 1;
        locations.add(word1.getLocation());
    }

    /**
     * Adds a new locations to an existing Bigram and increments the number of occurrences.
     * If the location already has been added, or the location is not a
     * valid location (i.e. negative), an exception is thrown.
     * @param location a location of the word
     * @throws IllegalArgumentException thrown if the location already exists or is invalid
     */
    @Override
    public void addLocation(long location) throws IllegalArgumentException {
        if (location < 0) {
            throw new IllegalArgumentException();
        } else if (locations.contains(location)) {
            throw new IllegalArgumentException();
        }
        occurrences++;
        locations.add(location);
    }

    /**
     * Generates a String representation of the Bigram that
     * contains both words of the Bigram and the number
     * of occurrences of the Bigram
     * @return a String representation of the Bigram
     */
    @Override
    public String toString() {
        return String.format("%-15s %-15s %4d", word, word2, occurrences);
    }

    /**
     * Compares another Object to check if that Object
     * is equal to this Bigram. Equality is measured by whether
     * the other Object is also a Bigram and both words contained
     * in this Bigram matches exactly the words contained in
     * the other Bigram object in the same order.
     * @param o the Object to compare to this
     * @return true if o is both a Bigram and contains the same words
     * in the same order as this Bigram, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bigram that)) {
            return false;
        }
        return this.word.equals(that.word) && this.word2.equals(that.word2);

    }

    /**
     * Compares this object with the specified object for order.
     * Returns a negative integer, zero, or a positive integer as
     * this object is less than, equal to, or greater than
     * the specified object.
     * For this class, we are comparing the number of occurrences
     * of the two Bigrams.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive
     * integer as this object is less than, equal to,
     * or greater than the specified object.
     */
    @Override
    public int compareTo(Word o) {
        if (!(o instanceof Bigram that)) {
            return 0;
        }
        return occurrences - that.getOccurrences();
    }

    private int getOccurrences() {
        return occurrences;
    }
}
