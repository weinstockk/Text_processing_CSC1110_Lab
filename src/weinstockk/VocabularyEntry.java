/*
 * Course: CSC-1110
 * Assignment: Text Processing
 * Name: Keagan Weinstock
 */
package weinstockk;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that contains information about a single word,
 * it's location(s), and its occurrences.
 */
public class VocabularyEntry extends Word {
    private int occurrences;
    private final List<Long> locations;

    /**
     * Constructor for a Word object
     * @param basicWord Word to add to the list
     */
    public VocabularyEntry(BasicWord basicWord) {
        super(basicWord.word);
        locations = new ArrayList<>();
        occurrences = 1;
        locations.add(basicWord.getLocation());

    }

    public int getOccurrences() {
        return occurrences;
    }
    public List<Long> getLocations() {
        return locations;
    }

    /**
     * Adds a new locations to an existing Word. If the location
     * already has been added, return false, otherwise return true.
     * @param location a location of the word
     * @throws IllegalArgumentException thrown if the location already exists or is invalid
     */
    @Override
    public void addLocation(long location) throws IllegalArgumentException{
        if (locations.contains(location) || location < 0) {
            throw new IllegalArgumentException();
        }
        occurrences++;
        locations.add(location);
    }

    /**
     * Generates a String representation of the VocabularyEntry that
     * contains both the word of the entry and the number of
     * occurrences of the entry.
     * @return a String representation of the VocabularyEntry
     */
    @Override
    public String toString() {
        return String.format("%-15s %4d", word, occurrences);
    }

    /**
     * Compares another Object to check if that Object is equal
     * to this VocabularyEntry. Equality is measured by whether
     * the other Object is also a VocabularyEntry and the word
     * contained in this VocabularyEntry matches exactly the word
     * contained in the other VocabularyEntry object.
     * @param o the Object to compare to this
     * @return true if o is both a VocabularyEntry and contains
     * the same word as this VocabularyEntry, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VocabularyEntry that)) {
            return false;
        }
        return this.word.equals(that.word);
    }

    /**
     * Compares this object with the specified object for order.
     * Returns a negative integer, zero, or a positive integer as
     * this object is less than, equal to, or greater than the
     * specified object.
     * For this class, we are comparing the number of occurrences of
     * the two VocabularyEntry objects.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as
     * this object is less than, equal to, or greater than the
     * specified object.
     */
    @Override
    public int compareTo(Word o) {
        if (!(o instanceof VocabularyEntry that)) {
            return 0;
        }
        return occurrences - that.getOccurrences();
    }
}
