package com.github.flaz14.processor;

import com.github.flaz14.Image;

import java.awt.image.BufferedImage;

/**
 * Resizes an image according to specified dimensions.
 * <p>
 * Please note that in order to keep aspect ratio you need to calculate dimensions in advance and specify them
 * explicitly.
 */
public class Resizer extends AbstractProcessor {
    public Resizer(Image sourceImage, int targetWidth, int targetHeight) {
        super(sourceImage);
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
    }

    @Override
    protected BufferedImage getTargetBuffer() {
        return new BufferedImage(
                targetWidth,
                targetHeight,
                BufferedImage.TYPE_INT_RGB);
    }

    private final int targetWidth;
    private final int targetHeight;
}
