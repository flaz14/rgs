package com.github.flaz14.processor;

import com.github.flaz14.util.Graphics;
import com.github.flaz14.Image;

import java.awt.image.BufferedImage;

/**
 * https://stackoverflow.com/questions/9131678/convert-a-rgb-image-to-grayscale-image-reducing-the-memory-in-java
 */
public class Grayscaler {
    public Grayscaler(Image image) {
        this.image = image;
    }

    public Image grayscale() {
        BufferedImage oldBuffer = image.buffer();
        int width = oldBuffer.getWidth();
        int height = oldBuffer.getHeight();
        BufferedImage newBuffer = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_BYTE_GRAY);
        try (Graphics graphics = new Graphics(newBuffer)) {
            graphics.draw(oldBuffer, width, height);
            return new Image(
                    newBuffer,
                    image.formatName(),
                    image.fileName());
        }
    }

    private final Image image;
}
