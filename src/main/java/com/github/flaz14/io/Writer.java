package com.github.flaz14.io;

import com.github.flaz14.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static java.lang.String.format;

/**
 *
 */
public class Writer {
    private final Image image;

    public Writer(Image image) {
        this.image = image;
    }

    public void write() {
        try {
            ImageIO.write(
                    image.buffer(),
                    image.formatName(),
                    new File(image.fileName()));
        } catch (IOException onWrite) {
            var message = format("Failed to write image [%s] into current directory.", image);
            throw new IllegalStateException(message, onWrite);
        }
    }
}
