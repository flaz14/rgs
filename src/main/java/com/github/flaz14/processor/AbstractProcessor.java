package com.github.flaz14.processor;

import com.github.flaz14.util.Graphics;
import com.github.flaz14.util.Image;

import java.awt.image.BufferedImage;

import static java.util.Objects.requireNonNull;

/**
 * Common functionality for image processing.
 * <p>
 * Since we use {@link BufferedImage}, most of operations are very similar. So we concentrated such routines in this
 * class. But allowed customization of target image via extension.
 */
public abstract class AbstractProcessor {
    AbstractProcessor(Image sourceImage) {
        this.sourceImage = requireNonNull(
                sourceImage,
                "Source image should not be null.");
    }

    /**
     * Draws image from source buffer into target buffer.
     * <p>
     * Target buffer should be customized by implementing {@link AbstractProcessor#getTargetBuffer()} method.
     *
     * @return target buffer enclosed into utility wrapper.
     */
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

    /**
     * Override this method if you would like to alter dimensions, color space, etc of target image.
     *
     * @return buffer for image with desired characteristics.
     */
    protected abstract BufferedImage getTargetBuffer();

    final Image sourceImage;
}
