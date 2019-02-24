package com.github.flaz14;

import java.awt.image.BufferedImage;

/**
 * Handy wrapper that helps to keep original image format and file name.
 */
public class Image {
    public Image(BufferedImage buffer, String formatName, String fileName) {
        this.buffer = buffer;
        this.formatName = formatName;
        this.fileName = fileName;
    }

    public BufferedImage buffer() {
        return buffer;
    }

    public String formatName() {
        return formatName;
    }

    public String fileName() {
        return fileName;
    }

    /**
     * Image buffer is not included into representation.
     * <p>
     * Tons of bytes from a buffer are unreadable (but they still can be examined with aid of debugger).
     */
    @Override
    public String toString() {
        return "Image{" +
                "formatName='" + formatName + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    private final BufferedImage buffer;
    private final String formatName;
    private final String fileName;
}
