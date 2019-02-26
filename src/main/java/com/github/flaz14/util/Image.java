package com.github.flaz14.util;

import java.awt.image.BufferedImage;

/**
 * Handy wrapper that helps to keep original image format, file name and the buffer itself together.
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
     * Image buffer is not included into representation because tons of bytes from a buffer are unreadable. However, the
     * exact content of a buffer can be examined with aid of debugger.
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
