package com.github.flaz14.processor;

import com.github.flaz14.Image;

import java.awt.image.BufferedImage;

/**
 * https://stackoverflow.com/questions/9131678/convert-a-rgb-image-to-grayscale-image-reducing-the-memory-in-java
 */
public class Grayscaler extends AbstractProcessor {
    public Grayscaler(Image sourceImage) {
        super(sourceImage);
    }

    @Override
    protected BufferedImage getTargetBuffer() {
        int targetWidth = sourceImage.buffer().getWidth();
        int targetHeight = sourceImage.buffer().getHeight();
        return new BufferedImage(
                targetWidth,
                targetHeight,
                BufferedImage.TYPE_BYTE_GRAY);
    }
}
