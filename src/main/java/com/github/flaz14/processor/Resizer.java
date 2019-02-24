package com.github.flaz14.processor;

import com.github.flaz14.Image;
import com.github.flaz14.util.Graphics;

import java.awt.image.BufferedImage;

import static java.util.Objects.requireNonNull;

/**
 * // TODO implement cache but not right now
 */
public class Resizer {
    private final Image image;

    public Resizer(Image image) {
        requireNonNull(image, "Image buffer should not be null.");
        this.image = image;
    }

    public Image resize(int width, int height) {
        BufferedImage oldBuffer = image.buffer();
        BufferedImage newBuffer = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB);
        try (Graphics graphics = new Graphics(newBuffer)) {
            graphics.draw(oldBuffer, width, height);
            return new Image(
                    newBuffer,
                    image.formatName(),
                    image.fileName());
        }
    }
}
