package com.github.flaz14.processor;

import com.github.flaz14.Image;
import com.github.flaz14.util.Graphics;

import java.awt.image.BufferedImage;

import static java.util.Objects.requireNonNull;

/**
 *
 */
public abstract class AbstractProcessor {
    AbstractProcessor(Image sourceImage) {
        this.sourceImage = requireNonNull(
                sourceImage,
                "Source image should not be null.");
    }

    public Image process() {
        BufferedImage sourceBuffer = sourceImage.buffer();
        BufferedImage targetBuffer = getTargetBuffer();
        try (Graphics graphics = new Graphics(targetBuffer)) {
            graphics.draw(
                    sourceBuffer,
                    targetBuffer.getWidth(),
                    targetBuffer.getHeight());
            return new Image(
                    targetBuffer,
                    sourceImage.formatName(),
                    sourceImage.fileName());
        }
    }

    protected abstract BufferedImage getTargetBuffer();

    final Image sourceImage;
}
