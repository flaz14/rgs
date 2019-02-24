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

    private final BufferedImage buffer;
    private final String formatName;
    private final String fileName;
}
