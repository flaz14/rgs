package com.github.flaz14.limit.wrapper;

import com.github.flaz14.limit.Limits;

import static java.lang.String.format;

public class PermissibleDimensions {
    public PermissibleDimensions(int width, int height) {
        if (width < 1 || width > Limits.IMAGE_WIDTH_IN_PIXELS) {
            var message = format("Image width should be between [1..%d] pixels" +
                            "but %d has been specified.",
                    Limits.IMAGE_WIDTH_IN_PIXELS,
                    width);
            throw new IllegalArgumentException(message);
        }
        if (height < 1 || height > Limits.IMAGE_HEIGHT_IN_PIXELS) {
            var message = format("Image width should be between [1..%d] pixels" +
                            "but %d has been specified.",
                    Limits.IMAGE_HEIGHT_IN_PIXELS,
                    height);
            throw new IllegalArgumentException(message);
        }
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    private final int width;
    private final int height;
}
