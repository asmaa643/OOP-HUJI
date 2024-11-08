package image;

import java.awt.*;

/**
 * Represents the sub images for an image, add the paddings and resolute it.
 */
public class SubImagesChars {
    private static final double RED_GREY = 0.2126;
    private static final double GREEN_GREY = 0.7152;
    private static final double BLUE_GREY = 0.0722;
    private static final int MAX_RGB = 255;
    private Image image;
    private Image[] subImages;
    private int paddingWidth;
    private int paddingHeight;


    /**
     * Constructs a SubImagesChars.
     * @param image to work with.
     */
    public SubImagesChars(Image image) {
        this.image = image;
        this.padding();
    }

    private void padding() {
        // Calculate the nest power of 2 of the height and the width, and make the padding symmetric.
        this.paddingHeight = closestPower2(this.image.getHeight());
        int midAddedHeight = (paddingHeight - this.image.getHeight()) / 2;

        this.paddingWidth = closestPower2(this.image.getWidth());
        int midAddedWidth = (paddingWidth - this.image.getWidth()) / 2;

        if (paddingHeight != this.image.getHeight() || paddingWidth != this.image.getWidth()){
            Color[][] pixelArray = getTheReorderedColors(midAddedHeight, midAddedWidth);
            this.image = new Image(pixelArray, paddingWidth, paddingHeight);
        }
    }

    private Color[][] getTheReorderedColors(int midAddedHeight, int midAddedWidth) {
        Color[][] pixelArray = new Color[paddingHeight][paddingWidth];
        for (int i = 0; i < paddingHeight; i++) {
            for (int j = 0; j < paddingWidth; j++) {
                if (i < midAddedHeight || j < midAddedWidth
                        || i >= (paddingHeight - midAddedHeight)
                        || j >= (paddingWidth - midAddedWidth)) {
                    pixelArray[i][j] = new Color(MAX_RGB, MAX_RGB, MAX_RGB);
                } else {
                    pixelArray[i][j] = this.image.getPixel(i - midAddedHeight, j - midAddedWidth);
                }
            }
        }
        return pixelArray;
    }

    private int closestPower2(int x) {
        int highestOneBit = Integer.highestOneBit(x);
        if (x == highestOneBit) {
            return x;
        }
        return highestOneBit << 1;
    }

    /**
     * Padding image width getter.
     * @return the width after padding.
     */
    public int getPaddingWidth() {
        return paddingWidth;
    }

    /**
     * Padding image height getter.
     * @return the height after padding.
     */
    public int getPaddingHeight() {
        return paddingHeight;
    }


    /**
     * Resolutes the image according to the given resolution value.
     * @param res resolution value.
     */
    public void resolution(int res) {
        int width = this.image.getWidth() / res;
        int rowsImages = this.image.getHeight() / (width);
        this.subImages = new Image[res * rowsImages];
        for (int i = 0; i < rowsImages; i++) {
            for (int j = 0; j < res; j++) {
                reorderThePixels(res, width, i, j);
            }
        }
    }

    private void reorderThePixels(int res, int width, int i, int j) {
        Color[][] pixelArray = new Color[width][width];
        for (int l = 0; l < width; l++) {
            for (int m = 0; m < width; m++) {
                pixelArray[l][m] = this.image.getPixel(i * width + l, width * j + m);
            }
        }
        this.subImages[res * i + j] = new Image(pixelArray, width, width);
    }

    /**
     * Sub images getter.
     * @return the array of the sub images.
     */
    public Image[] getSubImages() {
        return subImages;
    }


    /**
     * Calculates the brightness of an image (Sub image) according to the equation:
     * greyPixel = color.getRed() * 0.2126 + color.getGreen() * 0.7152 + color.getBlue() * 0.0722, for each 
     * cell.
     * @param image to calculate the brightness of it.
     * @return the calculated brightness.
     */
    public double calculateImageBrightness(Image image) {
        double totalGrey = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color pixelColor = image.getPixel(i, j);
                double red = pixelColor.getRed() * RED_GREY;
                double green = pixelColor.getGreen() * GREEN_GREY;
                double blue = pixelColor.getBlue() * BLUE_GREY;
                totalGrey += red + green + blue;
            }
        }
        totalGrey /= (image.getHeight() * image.getWidth());
        return totalGrey / MAX_RGB;
    }
}
