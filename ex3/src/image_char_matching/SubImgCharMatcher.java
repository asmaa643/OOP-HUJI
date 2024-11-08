package image_char_matching;

import java.util.*;

import static ascii_art.Shell.FIRST_CHAR;
import static ascii_art.Shell.LAST_CHAR;

/**
 * Represents the algorithm char matcher for image's sub images.
 */
public class SubImgCharMatcher {

    private final TreeMap<Double, TreeSet<Character>> charTree;
    private final HashMap<Character, Double> charmap;
    private final TreeSet<Character> charset;
    private double minValue;
    private double maxValue;
    private char maxChar;
    private char minChar;
    private double normal;


    /**
     * Constructs a SubImgCharMatcher.
     * charmap to hold the brightnesses of all the chars.
     * charTree to hold the brightnesses of the charset.
     * charset to hold the charset.
     *
     * @param charset initial chars.
     */
    public SubImgCharMatcher(char[] charset) {
        this.charTree = new TreeMap<>();
        this.charmap = new HashMap<>();
        this.charset = new TreeSet<>();
        for (char c = FIRST_CHAR, i = 0; c <= LAST_CHAR; c++, i++) {
            this.charmap.put(c, calculateBrightness(c));
        }
        minValue = Double.MAX_VALUE;
        maxValue = Double.MIN_VALUE;
        for (char c : charset) {
            this.updateMinMax(c);
            this.charset.add(c);
        }
        this.normal = this.maxValue - this.minValue;
        for (var c : charset) {
            double updatedBrightness = (this.charmap.get(c) - this.minValue) / this.normal;
            if (!this.charTree.containsKey(updatedBrightness)) {
                this.charTree.put(updatedBrightness, new TreeSet<>());
            }
            this.charTree.get(updatedBrightness).add(c);
        }
    }

    /**
     * Given a brightness value of a sub-image, the method returns the character with the closest
     * brightness in absolute value to the given brightness.
     * The character with the lower ASCII value between them (if there are more than 1) will be returned
     *
     * @param brightness value.
     * @return the closest brightness to it.
     */
    public char getCharByImageBrightness(double brightness) {
        char closestKey = 0;
        double minDiff = Double.MAX_VALUE;
        for (var entry : this.charTree.entrySet()) {
            double diff = Math.abs(entry.getKey() - brightness);
            if (diff < minDiff) {
                minDiff = diff;
                closestKey = entry.getValue().first();
            }
        }
        return closestKey;
    }

    private double updateMinMax(char c) {
        double value = this.charmap.get(c);
        if (value < minValue) {
            minValue = value;
            minChar = c;
        }
        if (value > maxValue) {
            maxValue = value;
            maxChar = c;
        }
        return value;
    }

    /**
     * A method that adds the character c to the character set.
     *
     * @param c to add.
     */
    public void addChar(char c) {
        this.charset.add(c);
        double brightness = updateMinMax(c);
        if (this.maxChar == c || this.minChar == c) {
            recalculateBrightnesses();
        }
        double updatedBrightness = (brightness - this.minValue) / this.normal;
        if (!this.charTree.containsKey(updatedBrightness)) {
            this.charTree.put(updatedBrightness, new TreeSet<>());
        }
        this.charTree.get(updatedBrightness).add(c);

    }

    /**
     * A method that removes the character c to the character set.
     *
     * @param c to remove.
     */
    public void removeChar(char c) {
        this.charset.remove(c);
        double value = this.charmap.get(c);
        double updatedBrightness;
        if (this.normal == 0) {
            updatedBrightness = 0;
        } else {
            updatedBrightness = (value - this.minValue) / this.normal;
        }
        TreeSet<Character> chars = this.charTree.get(updatedBrightness);
        if (chars == null || chars.isEmpty() || !chars.contains(c)) return;
        chars.remove(c);
        updateBrightnessesMinMax(chars, updatedBrightness, value);
    }

    /**
     * @return ture if the current charset if empty, false otherwise.
     */
    public boolean isCharsEmpty() {
        return this.charTree.isEmpty();
    }

    /**
     * Charset getter.
     *
     * @return the current charset.
     */
    public TreeSet<Character> getCharset() {
        return charset;
    }

    private void updateBrightnessesMinMax(TreeSet<Character> chars, double updatedBrightness, double value) {
        if (chars.isEmpty()) {
            this.charTree.remove(updatedBrightness);
            if (value == minValue) {
                minChar = this.charTree.isEmpty() ? ' ' : this.charTree.get(this.charTree.firstKey()).first();
//                this.charTree.isEmpty() ? Double.MAX_VALUE : this.charmap.get(minChar);
                minValue = this.charTree.isEmpty() ? Double.MAX_VALUE : this.charmap.get(minChar);
                recalculateBrightnesses();
            }
            if (value == maxValue) {
                maxChar = this.charTree.isEmpty() ? ' ' : this.charTree.get(this.charTree.lastKey()).first();
                maxValue = this.charTree.isEmpty() ? Double.MIN_VALUE : this.charmap.get(maxChar);
                recalculateBrightnesses();
            }
        }
    }

    private void recalculateBrightnesses() {
        this.normal = this.maxValue - this.minValue;
        ArrayList<Double> toDelete = new ArrayList<>();
        TreeMap<Double, TreeSet<Character>> toAdd = new TreeMap<>();
        for (var entry : this.charTree.entrySet()) {
            double updated;
            if (this.normal == 0) {
                updated = 0;
            } else {
                updated = (this.charmap.get(entry.getValue().first()) - this.minValue) / this.normal;
            }
            toAdd.put(updated, entry.getValue());
            toDelete.add(entry.getKey());
        }
        for (var b : toDelete) {
            this.charTree.remove(b);
        }
        this.charTree.putAll(toAdd);
    }

    private double calculateBrightness(char c) {
        boolean[][] charImg = CharConverter.convertToBoolArray(c);
        int whiteCounter = 0;
        int cellsRowCounter = 1; // There are 16*16 by default.
        for (boolean[] row : charImg) {
            cellsRowCounter = row.length;
            for (boolean col : row) {
                if (col) {
                    whiteCounter++;
                }
            }
        }
        return (double) whiteCounter / (cellsRowCounter*charImg.length);
    }
}
