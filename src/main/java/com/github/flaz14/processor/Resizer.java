package com.github.flaz14.processor;

import com.github.flaz14.Image;

import java.awt.image.BufferedImage;

/**
 * // TODO implement cache but not right now
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
