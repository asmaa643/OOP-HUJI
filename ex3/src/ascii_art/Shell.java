package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import exception.BoundsException;
import exception.CommandException;
import image.Image;
import image.SubImagesChars;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

/**
 * A class that runs the AsciiArt AAlgorithm according to the commands from the user.
 * @author Asmaa & Maryan
 */
public class Shell {
    /**
     * The first ascii char in the set.
     */
    public static final char FIRST_CHAR = 32;

    /**
     * The last ascii char in the set.
     */
    public static final char LAST_CHAR = 126;

    // Shell Constants
    private static final char MIN_START_CHAR = '0';
    private static final char MAX_START_CHAR = '9';
    private static final String GET_INPUT_COMMAND = ">>> ";
    private static final String CAT_PATH = "cat.jpeg";
    private static final String CHARS_COMMAND = "chars";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String RES_COMMAND = "res";
    private static final String IMAGE_COMMAND = "image";
    private static final String OUTPUT_COMMAND = "output";
    private static final String ASCIIART_COMMAND = "asciiArt";
    private static final String EXIT_COMMAND = "exit";
    private static final String UP_COMMAND = "up";
    private static final String DOWN_COMMAND = "down";
    private static final String ALL_COMMAND = "all";
    private static final String SPACE = "space";
    private static final String PRINTED_SPACE = " ";
    private static final String IMAGE_ERROR = "Did not execute due to problem with image file.";
    private static final String INCORRECT_COMMAND = "Did not execute due to incorrect command.";
    private static final String CHARSET_EMPTY_ERROR = "Did not execute. Charset is empty.";
    private static final String INCORRECT_OUTPUT = "Did not change output method due to incorrect format.";
    private static final String OUT_BOUNDARIES = "Did not change resolution due to exceeding boundaries.";
    private static final String INCORRECT_RES = "Did not change resolution due to incorrect format.";
    private static final String RES_SET = "Resolution set to %d.";
    private static final String INCORRECT_ADD = "Did not add due to incorrect format.";
    private static final String INCORRECT_REMOVE = "Did not remove due to incorrect format.";
    private static final char DASH = '-';
    private static final String SPLIT_REGEX = " ";
    private static final String HTML_OUTPUT = "html";
    private static final String CONSOLE_OUTPUT = "console";
    private static final String OUTPUT_FILE = "out.html";
    private static final String HTML_FONT = "Courier New";
    private static final int MIN_RES_SIZE = 1;
    private static final int UP_DOWN_RES_MULTIPLY = 2;
    private static final int INITIAL_RES = 128;

    // Shell Fields
    private Image image;
    private SubImagesChars subImagesChars;
    private int res = INITIAL_RES;
    private int minCharsInRows;
    private int maxCharsInRows;
    private String imagePath;
    private AsciiOutput asciiOutput;

    /**
     * Constructs a new shell.
     */
    public Shell() {
        asciiOutput = new ConsoleAsciiOutput();
        this.imagePath = CAT_PATH;
        try {
            this.image = new Image(this.imagePath);
        } catch (IOException e) {
            System.out.println(IMAGE_ERROR);
            return;
        }
        this.subImagesChars = new SubImagesChars(image);
        minCharsInRows = Math.max(MIN_RES_SIZE,
                this.subImagesChars.getPaddingWidth() / this.subImagesChars.getPaddingHeight());
        maxCharsInRows = this.subImagesChars.getPaddingWidth();
    }

    /**
     * Runs the whole algorithm, taking inputs from the user to execute commands.
     */
    public void run() {
        char[] startedChars = new char[10];
        for (int c = MIN_START_CHAR, i = 0; c <= MAX_START_CHAR; c++, i++) {
            startedChars[i] = (char) c;
        }
        SubImgCharMatcher subImgCharMatcher = new SubImgCharMatcher(startedChars);
        System.out.print(GET_INPUT_COMMAND);
        String input;
        while (!(input = KeyboardInput.readLine()).equals(EXIT_COMMAND)) {
            String[] inputWords = input.split(SPLIT_REGEX);
            String command = inputWords[0];
            try {
                commandsChecks(command, inputWords, subImgCharMatcher);
            } catch (IOException e) {
                System.out.println(IMAGE_ERROR);
            } catch (CommandException ce) {
                System.out.println(ce.getMessage());
            } catch (BoundsException b) {
                System.out.println(b.getMessage());
            }
            System.out.print(GET_INPUT_COMMAND);
        }
    }

    private void commandsChecks(String command, String[] inputWords,
                                SubImgCharMatcher subImgCharMatcher) throws CommandException,
            BoundsException, IOException {
        if (command.equals(CHARS_COMMAND)) {
            charsCommand(subImgCharMatcher);
        } else if (command.equals(ADD_COMMAND)) {
            addCommand(inputWords, subImgCharMatcher);
        } else if (command.equals(REMOVE_COMMAND)) {
            removeCommand(inputWords, subImgCharMatcher);
        } else if (command.equals(RES_COMMAND)) {
            resCommand(inputWords);
        } else if (command.equals(IMAGE_COMMAND)) {
            imageCommand(inputWords);
        } else if (command.equals(OUTPUT_COMMAND)) {
            outputCommand(inputWords);
        } else if (command.equals(ASCIIART_COMMAND)) {
            asciiArtCommand(subImgCharMatcher);
        } else {
            System.out.println(INCORRECT_COMMAND);
        }
    }

    private void asciiArtCommand(SubImgCharMatcher subImgCharMatcher) throws CommandException {
        if (!subImgCharMatcher.isCharsEmpty()) {
            AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(subImgCharMatcher,
                    subImagesChars, res);
            char[][] asciiImage = asciiArtAlgorithm.run();
            this.asciiOutput.out(asciiImage);

        } else {
            throw new CommandException(CHARSET_EMPTY_ERROR);
        }
    }

    private void outputCommand(String[] inputWords) throws CommandException {
        if (inputWords[1].equals(HTML_OUTPUT)) {
            asciiOutput = new HtmlAsciiOutput(OUTPUT_FILE, HTML_FONT);
        } else if (inputWords[1].equals(CONSOLE_OUTPUT)) {
            asciiOutput = new ConsoleAsciiOutput();
        } else {
            throw new CommandException(INCORRECT_OUTPUT);
        }
    }

    private void imageCommand(String[] inputWords) throws IOException {
        this.imagePath = inputWords[1];
        this.image = new Image(this.imagePath);
        this.subImagesChars = new SubImagesChars(image);
        minCharsInRows = Math.max(1,
                this.subImagesChars.getPaddingWidth() / this.subImagesChars.getPaddingHeight());
        maxCharsInRows = this.subImagesChars.getPaddingWidth();
    }

    private void resCommand(String[] inputWords) throws BoundsException, CommandException {
        if (inputWords[1].equals(UP_COMMAND)) {
            this.res *= UP_DOWN_RES_MULTIPLY;
            if (this.res > this.maxCharsInRows) {
                this.res /= UP_DOWN_RES_MULTIPLY;
                throw new BoundsException(OUT_BOUNDARIES);
            } else {
                System.out.println(String.format(RES_SET, this.res));
            }
        } else if (inputWords[1].equals(DOWN_COMMAND)) {
            this.res /= UP_DOWN_RES_MULTIPLY;
            if (this.res < this.minCharsInRows) {
                this.res *= UP_DOWN_RES_MULTIPLY;
                if (this.res <= 0) {
                    this.res = 1;
                }
                throw new BoundsException(OUT_BOUNDARIES);
            } else {
                System.out.println(String.format(RES_SET, this.res));
            }
        } else {
            throw new CommandException(INCORRECT_RES);
        }
    }

    private void removeCommand(String[] inputWords, SubImgCharMatcher charMatcher) throws CommandException {
        char[] input2;
        if (inputWords[1].equals(ALL_COMMAND)) {
            for (char c = FIRST_CHAR; c <= LAST_CHAR; c++) {
                charMatcher.removeChar(c);
            }
        } else if (inputWords[1].equals(SPACE)) {
            charMatcher.removeChar(' ');
        } else if ((input2 = inputWords[1].toCharArray()).length == 1) {
            charMatcher.removeChar(input2[0]);
        } else if (input2.length == 3 && input2[1] == DASH) {
            for (char d = (char) Math.min(input2[0], input2[2]); d <= (char) Math.max(input2[0],
                    input2[2]); d++) {
                charMatcher.removeChar(d);
            }
        } else {
            throw new CommandException(INCORRECT_REMOVE);
        }
    }

    private void addCommand(String[] inputWords, SubImgCharMatcher charMatcher) throws CommandException {
        char[] input2;
        if (inputWords[1].equals(ALL_COMMAND)) {
            for (char c = FIRST_CHAR; c <= LAST_CHAR; c++) {
                charMatcher.addChar(c);
            }
        } else if (inputWords[1].equals(SPACE)) {
            charMatcher.addChar(' ');
        } else if ((input2 = inputWords[1].toCharArray()).length == 1) {
            charMatcher.addChar(input2[0]);
        } else if (input2.length == 3 && input2[1] == DASH) {
            for (char d = (char) Math.min(input2[0], input2[2]); d <= (char) Math.max(input2[0],
                    input2[2]); d++) {
                charMatcher.addChar(d);
            }
        } else {
            throw new CommandException(INCORRECT_ADD);
        }
    }

    private void charsCommand(SubImgCharMatcher charMatcher) {
        for (char c : charMatcher.getCharset()) {
            System.out.print(c + PRINTED_SPACE);
        }
        System.out.println();
    }

    /**
     * The main method
     *
     * @param args of the program.
     */
    public static void main(String[] args) {
        new Shell().run();
    }
}
