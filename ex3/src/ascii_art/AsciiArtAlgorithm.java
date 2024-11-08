package ascii_art;

import image.Image;
import image.SubImagesChars;
import image_char_matching.SubImgCharMatcher;


/**
 * Represents a run of an AsciiArtAlgorithm.
 */
public class AsciiArtAlgorithm {
    private final SubImgCharMatcher subImgCharMatcher;
    private final SubImagesChars subImagesChars;
    private final int res;

    /**
     * Constructs an AsciiArtAlgorithm.
     * @param subImgCharMatcher of the algorithm.
     * @param subImagesChars of the image to show.
     * @param res to split the image to.
     */
    public AsciiArtAlgorithm(SubImgCharMatcher subImgCharMatcher, SubImagesChars subImagesChars, int res) {
        this.subImgCharMatcher = subImgCharMatcher;
        this.subImagesChars = subImagesChars;
        this.res = res;
    }

    /**
     * Runs the AsciiArtAlgorithm for a single image.
     * @return a 2D array of the pixels image.
     */
    public char[][] run(){
        char[][] asciiImage;
        subImagesChars.resolution(this.res);
        Image[] subImages = this.subImagesChars.getSubImages();
        int resCount = 0, colCount = 0;
        asciiImage = new char[subImages.length/this.res][this.res];
        for (Image subImage : subImages) {
            double b = this.subImagesChars.calculateImageBrightness(subImage);
            char c = subImgCharMatcher.getCharByImageBrightness(b);
            asciiImage[colCount][resCount] = c;
            resCount++;
            if (resCount == this.res) {
                resCount = 0;
                colCount++;
            }
        }
        return asciiImage;
    }
}
