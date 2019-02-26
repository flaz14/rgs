package com.github.flaz14.processor;

import com.github.flaz14.limit.wrapper.PermissibleDimensions;
import com.github.flaz14.util.Image;

import java.awt.image.BufferedImage;

import static java.util.Objects.requireNonNull;

/**
 * Resizes an image according to specified dimensions.
 * <p>
 * Please note that in order to keep aspect ratio the user need to calculate dimensions in advance and specify them
 * explicitly.
 */
public class Resizer extends AbstractProcessor {
    public Resizer(Image sourceImage, PermissibleDimensions dimensions) {
        super(sourceImage);
        this.dimensions = requireNonNull(
                dimensions,
                "Dimensions wrapper should not be null.");
    }

    @Override
    protected BufferedImage getTargetBuffer() {
        return new BufferedImage(
                dimensions.width(),
                dimensions.height(),
                BufferedImage.TYPE_INT_RGB);
    }

    private final PermissibleDimensions dimensions;
}
