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

    public BufferedImage buffer() {
        return buffer;
    }

    public String formatName() {
        return formatName;
    }

    public String fileName() {
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
