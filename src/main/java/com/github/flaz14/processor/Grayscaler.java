package com.github.flaz14.processor;

import com.github.flaz14.util.Image;

import java.awt.image.BufferedImage;

/**
 * Makes an image grayscaled.
 * <p>
 * The idea of implementation has been taken from
 * <a href="https://stackoverflow.com/questions/9131678/convert-a-rgb-image-to-grayscale-image-reducing-the-memory-in-java">convert
 * a RGB image to grayscale Image reducing the memory in java</a> answer on StackOverflow.
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
