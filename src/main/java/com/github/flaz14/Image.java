package com.github.flaz14;

import java.awt.image.BufferedImage;

/**
 *
 */
public class Image {
    public Image(BufferedImage buffer, String formatName, String fileName) {
        this.buffer = buffer;
        this.formatName = formatName;
        this.fileName = fileName;
    }

    BufferedImage buffer() {
        return buffer;
    }

    String formatName() {
        return formatName;
    }

    String fileName() {
        return fileName;
    }

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
